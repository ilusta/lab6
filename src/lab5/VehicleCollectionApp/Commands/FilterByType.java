package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.EOFInputException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.VehicleCollection;

public class FilterByType extends CollectionCommand
{
    public FilterByType(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params) throws InputException, EOFInputException, CommandExecutionException {
        String output = collection.filterByType();
        if(output.equals("") || output == null) System.out.println("No vehicles with such type.");
        else System.out.println(output);
    }

    @Override
    public String getHelp() {
        return "{type} | Prints all vehicles with given type.";
    }
}
