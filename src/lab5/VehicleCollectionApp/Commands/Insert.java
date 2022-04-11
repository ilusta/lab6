package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.EOFInputException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.VehicleCollection;

public class Insert extends CollectionCommand
{
    public Insert(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params) throws InputException, CommandExecutionException, EOFInputException {
        if (params.length < 2) throw new InputException("Argument is missing");
        String key = params[1];
        collection.insert(key);
    }

    @Override
    public String getHelp() {
        return "[key] {vehicle} | Inserts new element to collection with given key.";
    }
}