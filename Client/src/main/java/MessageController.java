import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageController implements Runnable {

    private MessageCommand command;
    private String commandLine;
    private String[] commands;

    public MessageController(MessageCommand command, String commandLine){
        this.command = command;
        this.commandLine = commandLine;
        this.commands = commandLine.split(" ");
    }

    @Override
    public void run() {
        if(command.equals(MessageCommand.GET_ALL)){
            getAll();
        }
        else if (command.equals(MessageCommand.GET_TO_SPECIFIC)){
            getToSpecific();
        }
        else if (command.equals(MessageCommand.SEND)){
            sendMessage();
        }
        else if (command.equals(MessageCommand.WAIT)){
            waitTest();
        }

    }

    private void getAll(){
        SimpleShell.prettyPrint(YouAreEll.get_messages(),ObjectType.MESSAGE);
    }

    private void getToSpecific(){
        String id = commands[1];
        SimpleShell.prettyPrint(YouAreEll.MakeURLCall("/ids/" + id + "/messages", "GET", ""), ObjectType.MESSAGE);
    }

    private void sendMessage(){
        Pattern messageNoTo = Pattern.compile("'(.*)'");
        Pattern messageTo = Pattern.compile("'(.*)' (to) (.*)");
        Matcher withTo = messageTo.matcher(commandLine);
        Matcher noTo = messageNoTo.matcher(commandLine);
        String from = commands[1];
        try {
            if (withTo.find()) {
                String to = withTo.group(3);
                String payload = Mapper.mapper.writeValueAsString(new Message(from, to, withTo.group(1)));
                YouAreEll.MakeURLCall("/ids/" + to + "/messages", "POST", payload);
            } else if (noTo.find()) {
                String payload = Mapper.mapper.writeValueAsString(new Message(from, noTo.group(1)));
                YouAreEll.MakeURLCall("/ids/" + from + "/messages", "POST", payload);
            } else System.out.println("Invalid message format");
        }
        catch(JsonProcessingException jpe){
            System.out.println(jpe.getMessage());
        }
    }

    private void waitTest(){
        try {
            Thread.sleep(20000);
            System.out.println("Waking Up");
        }
        catch(InterruptedException ie){
            System.out.println(ie.getMessage());
        }
    }


}
