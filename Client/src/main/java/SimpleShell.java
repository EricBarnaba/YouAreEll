import com.fasterxml.jackson.core.type.TypeReference;

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

        if (type.equals(ObjectType.MESSAGE)) {
            try {
                ArrayList<Message> messages = Mapper.mapper.readValue(output, new TypeReference<ArrayList<Message>>() {
                });
                System.out.println();
                messages.forEach(System.out::println);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } else if (type.equals(ObjectType.ID)) {
            try {
                ArrayList<Id> ids = Mapper.mapper.readValue(output, new TypeReference<ArrayList<Id>>() {
                });
                System.out.println();
                ids.forEach(System.out::println);
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } else System.out.println("Something has gone wrong");

    }

    public static void main(String[] args) throws java.io.IOException {

        //YouAreEll webber = new YouAreEll();
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
                    if (list.size() == 3) {
                        new Thread(new IdController(IdCommand.POST_ID,commandLine)).start();
                    } else if (list.size() == 1) {
                        new Thread(new IdController(IdCommand.GET_ALL)).start();
                    } else System.out.println("Invalid Command");
                    continue;
                }

                // messages
                if (list.get(0).equals("messages")) {
                    if (list.size() == 2) {
                        new Thread(new MessageController(MessageCommand.GET_TO_SPECIFIC, commandLine)).start();
                    } else if (list.size() == 1) {
                        new Thread(new MessageController(MessageCommand.GET_ALL, commandLine)).start();
                    } else System.out.println("Invalid Command");
                    continue;
                }

                if (list.get(0).equals("send")) {
                    new Thread(new MessageController(MessageCommand.SEND, commandLine)).start();
                    continue;
                }

                if (list.get(0).equals("wait")) {
                    new Thread(new MessageController(MessageCommand.WAIT, commandLine)).start();
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