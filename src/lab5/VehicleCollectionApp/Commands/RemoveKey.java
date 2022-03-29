package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.VehicleCollection;

public class RemoveKey extends CollectionCommand
{
    public RemoveKey(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params) throws InputException, CommandExecutionException {
        if (params.length < 2) throw new InputException("Argument is missing");
        String removeKey = params[1];
        collection.removeKey(removeKey);
    }

    @Override
    public String getHelp() {
        return "[key] | Removes element from collection by given key.";
    }
}