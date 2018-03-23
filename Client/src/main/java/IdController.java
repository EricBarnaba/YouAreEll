import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

public class IdController implements Runnable {

    private IdCommand command;
    private String[] commands;


    public IdController(IdCommand command, String commandLine){
        this.command = command;
        this.commands = commandLine.split(" ");
    }

    public IdController(IdCommand command){
        this.command = command;
    }


    @Override
    public void run() {
        if(this.command.equals(IdCommand.GETALL)){
            getAll();
        }
        else if (this.command.equals(IdCommand.POST_ID)){
            postId();
        }
    }

    private void getAll(){
        SimpleShell.prettyPrint(YouAreEll.get_ids(), ObjectType.ID);
    }

    private void postId(){
        String userName = commands[1];
        String gitHub = commands[2];
        try {
            String payload = Mapper.mapper.writeValueAsString(new Id(userName, gitHub));
            YouAreEll.MakeURLCall("/ids", "POST", payload);
        }
        catch(JsonProcessingException jpe){
            System.out.println(jpe.getMessage());
        }
    }



}
