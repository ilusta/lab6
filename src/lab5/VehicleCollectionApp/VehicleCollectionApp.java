package lab5.VehicleCollectionApp;

import lab5.VehicleCollectionApp.Exceptions.EOFInputException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.Commands.*;
import java.util.HashMap;
import java.util.ArrayList;
import lab5.VehicleCollectionApp.UserInput.UserInput;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class VehicleCollectionApp
{
    public void run() {
        System.out.println("Welcome to the Vehicle Collection App!");

        final VehicleCollection collection = new VehicleCollection();
        try {
            final String file = EnvVarReader.getValue("VEHICLE_COLLECTION_PATH");
            collection.setFileName(file);
            collection.open();
        }
        catch (Exception e) {
            System.out.println("Error occurred while reading file:");
            System.out.println(e.getMessage());
        }

        final boolean runFlag = true;

        UserInput.setReader(new BufferedReader(new InputStreamReader(System.in)));
        final ArrayList<String> commandHistory = new ArrayList<String>();

        final HashMap<String, Command> commandList = new HashMap<String, Command>();
        commandList.put("help", new Help(commandList));
        commandList.put("exit", new Exit());
        commandList.put("history", new History(commandHistory));
        commandList.put("info", new Info(collection));
        commandList.put("show", new Show(collection));
        commandList.put("insert", new Insert(collection));
        commandList.put("update", new Update(collection));
        commandList.put("remove_key", new RemoveKey(collection));
        commandList.put("clear", new Clear(collection));
        commandList.put("save", new Save(collection));


        while (Exit.getRunFlag()) {
            try {
                String[] words = UserInput.readLine().split(" +");
                Command command = commandList.get(words[0]);
                if (command == null) throw new InputException("Unknown command. Use 'help' command to get list of supported commands.");
                command.execute(words);
                commandHistory.add(words[0]);
            }
            catch (Exception e) {
                if(e.getClass() == EOFInputException.class) break;
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Goodbye!");

        UserInput.closeReader();
    }
}