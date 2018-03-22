import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleShell {


    public static void prettyPrint(String output, ObjectType type) {
        // yep, make an effort to format things nicely, eh?

        if(type.equals(ObjectType.MESSAGE)){
            try {
                ArrayList<Message> messages = Mapper.mapper.readValue(output, new TypeReference<ArrayList<Message>>() {});
                System.out.println();
                messages.forEach(System.out::println);
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }
        else if (type.equals(ObjectType.ID)){
            try {
                ArrayList<Id> ids = Mapper.mapper.readValue(output, new TypeReference<ArrayList<Id>>() {});
                System.out.println();
                ids.forEach(System.out::println);
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }
        else System.out.println("Something has gone wrong");

//        try {
//            ArrayList<JSONObject> messages = Mapper.mapper.readValue(output, new TypeReference<ArrayList<JSONObject>>() {});
//            for(JSONObject j : messages){
//                System.out.println(j);
//            }
//        }
//        catch(IOException ioe){
//            System.out.println(ioe.getMessage());
//        }

    }
    public static void main(String[] args) throws java.io.IOException {

        YouAreEll webber = new YouAreEll();
        String commandLine;
        BufferedReader console = new BufferedReader
                (new InputStreamReader(System.in));

        ProcessBuilder pb = new ProcessBuilder();
        List<String> history = new ArrayList<String>();
        int index = 0;
        //we break out with <ctrl c>
        while (true) {
            //read what the user enters
            System.out.println("cmd? ");
            commandLine = console.readLine();

            //input parsed into array of strings(command and arguments)
            String[] commands = commandLine.split(" ");
            List<String> list = new ArrayList<String>();

            //if the user entered a return, just loop again
            if (commandLine.equals(""))
                continue;
            if (commandLine.equals("exit")) {
                System.out.println("bye!");
                break;
            }

            //loop through to see if parsing worked
            Collections.addAll(list, commands);
            System.out.print(list); //***check to see if list was added correctly***
            history.addAll(list);
            try {
                //display history of shell with index
                if (list.get(list.size() - 1).equals("history")) {
                    for (String s : history)
                        System.out.println((index++) + " " + s);
                    continue;
                }

                // Specific Commands.

                // ids
                if (list.get(0).equals("ids")) {
                    if (list.size()==3){
                        String userName = list.get(1);
                        String gitHub = list.get(2);
                        String payload = Mapper.mapper.writeValueAsString(new Id(userName,gitHub));
                        webber.MakeURLCall("/ids","POST", payload);
                    }
                    else if (list.size()==1) {
                        String results = webber.get_ids();
                        SimpleShell.prettyPrint(results, ObjectType.ID);
                    }
                    else System.out.println("Invalid Command");
                    continue;
                }

                // messages
                if (list.get(0).equals("messages")) {
                    if(list.size()==2){
                        String id = list.get(1);
                        SimpleShell.prettyPrint(webber.MakeURLCall("/ids/" + id + "/messages", "GET", ""),ObjectType.MESSAGE);
                    }
                    else if(list.size()==1) {
                        String results = webber.get_messages();
                        SimpleShell.prettyPrint(results, ObjectType.MESSAGE);
                    }
                    else System.out.println("Invalid Command");
                    continue;
                }

                if(list.get(0).equals("send")){
                    Pattern messageNoTo = Pattern.compile("'(.*)'");
                    Pattern messageTo = Pattern.compile("'(.*)' (to) (.*)");
                    Matcher withTo = messageTo.matcher(commandLine);
                    Matcher noTo = messageNoTo.matcher(commandLine);
                    String from = list.get(1);
                    if(withTo.find()){
                        String to = withTo.group(3);
                        String payload = Mapper.mapper.writeValueAsString(new Message(from, to, withTo.group(1)));
                        webber.MakeURLCall("/ids/" + to + "/messages", "POST", payload);
                    }
                    else if(noTo.find()) {
                        String payload = Mapper.mapper.writeValueAsString(new Message(from, noTo.group(1)));
                        webber.MakeURLCall("/ids/" + from + "/messages", "POST", payload);
                    }
                    else System.out.println("Invalid message format");
                    continue;
                }
                // you need to add a bunch more.

                //!! command returns the last command in history
                if (list.get(list.size() - 1).equals("!!")) {
                    pb.command(history.get(history.size() - 2));

                }//!<integer value i> command
                else if (list.get(list.size() - 1).charAt(0) == '!') {
                    int b = Character.getNumericValue(list.get(list.size() - 1).charAt(1));
                    if (b <= history.size())//check if integer entered isn't bigger than history size
                        pb.command(history.get(b));
                } else {
                    pb.command(list);
                }

                // wait, wait, what curiousness is this?
                Process process = pb.start();

                //obtain the input stream
                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                //read output of the process
                String line;
                while ((line = br.readLine()) != null)
                    System.out.println(line);
                br.close();


            }

            //catch ioexception, output appropriate message, resume waiting for input
            catch (IOException e) {
                System.out.println("Input Error, Please try again!");
            }
            // So what, do you suppose, is the meaning of this comment?
            /** The steps are:
             * 1. parse the input to obtain the command and any parameters
             * 2. create a ProcessBuilder object
             * 3. start the process
             * 4. obtain the output stream
             * 5. output the contents returned by the command
             */

        }


    }

}