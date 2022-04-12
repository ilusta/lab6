package lab5.VehicleCollectionApp;

import lab5.VehicleCollectionApp.Commands.Command;
import lab5.VehicleCollectionApp.Commands.Exit;
import lab5.VehicleCollectionApp.Commands.History;
import lab5.VehicleCollectionApp.Exceptions.EOFInputException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.UserInput.UserInput;

import java.util.HashMap;

public class CommandExecutor {

    HashMap<String, Command> commandList;

    public CommandExecutor(HashMap<String, Command> commandList){
        //StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        //for(StackTraceElement x : stack);
        this.commandList = commandList;
    }

    public void run(){
        while (Exit.getRunFlag()) {
            try {
                String[] words = UserInput.readLine().split(" +");
                if (words.length == 0)
                    throw new InputException("Unknown command. Use 'help' command to get list of supported commands.");
                Command command = commandList.get(words[0]);
                if (command == null)
                    throw new InputException("Unknown command. Use 'help' command to get list of supported commands.");
                command.execute(words);
                History.commandHistory.add(words[0]);
            } catch (Exception e) {
                if (e.getClass() == EOFInputException.class) break;
                System.out.println(e.getMessage());
            }
        }
    }
}
