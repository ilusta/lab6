package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.CommandExecutor;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.UserInput.UserInput;
import lab5.VehicleCollectionApp.VehicleCollection;
import lab5.VehicleCollectionApp.VehicleCollectionApp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Collection;
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
            BufferedReader previousReader = UserInput.getActiveReader();
            UserInput.setActiveReader(reader);

            System.out.println("Executing commands from file " + fileName);
            CommandExecutor executor = new CommandExecutor(commandList);
            executor.run();
            System.out.println("Reached end of file.");

            UserInput.closeReader();
            UserInput.setActiveReader(previousReader);
        }
        catch (Exception e){
            System.out.print("Can not read " + fileName + ". " + e.getMessage());
        }

    }

    @Override
    public String getHelp() {
        return "[file name] | Executes commands from given file.";
    }
}
