package lab6.Commands;

import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.EOFInputException;
import lab6.Exceptions.InputException;
import lab6.Server.VehicleCollectionServer.VehicleCollection;
import lab6.Vehicle.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class RemoveKey extends CollectionCommand
{

    @Override
    public String getName() {
        return "remove_key";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "[key] | Removes element from collection by given key.";
    }

    public static void attach(VehicleCollection collection){
        RemoveKey.collection = collection;
    }

    String removeKey;

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        if (params.length < 2) throw new InputException("Argument is missing");
        removeKey = params[1];

        return this;
    }

    @Override
    public String execute() throws CommandExecutionException {
        return collection.removeKey(removeKey) + "\n";
    }
}