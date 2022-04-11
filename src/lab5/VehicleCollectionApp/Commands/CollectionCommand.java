package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.EOFInputException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.VehicleCollection;

public abstract class CollectionCommand extends Command
{
    public VehicleCollection collection;

    public CollectionCommand(VehicleCollection collection) {
        this.collection = collection;
    }

    @Override
    public abstract void execute(String[] p0) throws InputException, CommandExecutionException, EOFInputException;

    @Override
    public abstract String getHelp();
}