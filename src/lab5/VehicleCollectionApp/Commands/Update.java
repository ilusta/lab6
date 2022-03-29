package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.VehicleCollection;

public class Update extends CollectionCommand
{
    public Update(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params) throws InputException, CommandExecutionException {
        if (params.length < 2) throw new InputException("Argument is missing");
        Long ID = Long.parseLong(params[1]);
        collection.update(ID);
    }

    @Override
    public String getHelp() {
        return "[ID] {vehicle} | Updates element in collection by it`s ID.";
    }
}