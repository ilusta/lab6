package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.EOFInputException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.VehicleCollection;

public class RemoveLower extends CollectionCommand
{
    public RemoveLower(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params) throws InputException, EOFInputException, CommandExecutionException {
        collection.removeLower();
    }

    @Override
    public String getHelp() {
        return "{vehicle} | Removes elements from collection that are less than given.";
    }
}
