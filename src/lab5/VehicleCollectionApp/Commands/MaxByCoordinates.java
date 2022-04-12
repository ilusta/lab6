package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.EOFInputException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.VehicleCollection;

public class MaxByCoordinates extends CollectionCommand
{
    public MaxByCoordinates(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params) throws InputException, EOFInputException, CommandExecutionException {
        System.out.println(collection.maxByCoordinates());
    }

    @Override
    public String getHelp() {
        return "Prints vehicle with maximum coordinates.";
    }
}
