package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.EOFInputException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.VehicleCollection;

public class RemoveGreaterKey extends CollectionCommand
{
    public RemoveGreaterKey(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params) throws InputException, EOFInputException, CommandExecutionException {
        if (params.length < 2) throw new InputException("Key is missing");
        collection.removeGreaterKey(params[1]);
    }

    @Override
    public String getHelp() {
        return "[key] | Removes elements from collection with keys bigger than given.";
    }
}
