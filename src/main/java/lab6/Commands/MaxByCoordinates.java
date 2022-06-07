package lab6.Commands;

import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.EOFInputException;
import lab6.Exceptions.InputException;
import lab6.Server.VehicleCollectionServer.VehicleCollection;

public class MaxByCoordinates extends CollectionCommand
{

    @Override
    public String getName() {
        return "max_by_coordinates";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "Prints vehicle with biggest coordinates.";
    }

    public static void attach(VehicleCollection collection){
        MaxByCoordinates.collection = collection;
    }

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        return this;
    }

    @Override
    public String execute() throws CommandExecutionException {
        return collection.maxByCoordinates() + "\n";
    }
}