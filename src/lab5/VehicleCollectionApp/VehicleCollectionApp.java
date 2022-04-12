package lab5.VehicleCollectionApp;

import lab5.VehicleCollectionApp.Commands.*;
import java.util.HashMap;
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

        final HashMap<String, Command> commandList = new HashMap<>();
        commandList.put("help", new Help(commandList));
        commandList.put("exit", new Exit());
        commandList.put("history", new History());
        commandList.put("info", new Info(collection));
        commandList.put("show", new Show(collection));
        commandList.put("insert", new Insert(collection));
        commandList.put("update", new Update(collection));
        commandList.put("remove_key", new RemoveKey(collection));
        commandList.put("sum_of_number_of_wheels", new SumOfNumberOfWheels(collection));
        commandList.put("execute_script", new ExecuteScript(collection, commandList));
        commandList.put("remove_lower", new RemoveLower(collection));
        commandList.put("remove_greater_key", new RemoveGreaterKey(collection));
        commandList.put("max_by_coordinates", new MaxByCoordinates(collection));
        commandList.put("filter_by_type", new FilterByType(collection));
        commandList.put("clear", new Clear(collection));
        commandList.put("save", new Save(collection));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        UserInput.setActiveReader(reader);
        CommandExecutor executor = new CommandExecutor(commandList);

        executor.run();

        UserInput.closeReader();
        System.out.println("Goodbye!");
    }
}