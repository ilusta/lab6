package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.CommandExecutor;
import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.UserInput.UserInput;
import lab5.VehicleCollectionApp.VehicleCollection;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class ExecuteScript extends Command
{
    public VehicleCollection collection;
    HashMap<String, Command> commandList;

    public ExecuteScript(VehicleCollection collection, HashMap<String, Command> commandList) {
        this.collection = collection;
        this.commandList = commandList;
    }

    @Override
    public void execute(String[] params) throws InputException{
        if (params.length < 2) throw new InputException("File name is missing");

        String fileName = params[1];

        try {
            for(String s : UserInput.callList){
                if(s.equalsIgnoreCase(fileName)){
                    UserInput.callList.remove(fileName);
                    throw new CommandExecutionException("Recursive script call detected");
                }
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            BufferedReader previousReader = UserInput.getActiveReader();
            UserInput.setActiveReader(reader);

            UserInput.callList.add(fileName);

            System.out.println("Executing commands from file " + fileName);
            CommandExecutor executor = new CommandExecutor(commandList);
            executor.run();
            System.out.println("Reached end of file.");

            UserInput.closeReader();
            UserInput.callList.remove(fileName);
            UserInput.setActiveReader(previousReader);
        }
        catch (Exception e){
            System.out.println("Error occurred while executing commands from file " + fileName + ". " + e.getMessage());
        }

    }

    @Override
    public String getHelp() {
        return "[file name] | Executes commands from given file.";
    }
}
