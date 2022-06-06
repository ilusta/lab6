package lab6.Commands;

import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.EOFInputException;
import lab6.Exceptions.InputException;
import lab6.Server.VehicleCollectionServer.VehicleCollection;
import lab6.Vehicle.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class RemoveLower extends CollectionCommand
{

    public RemoveLower(){
    }

    private RemoveLower(RemoveLower cmd){
        this.vehicle = cmd.vehicle;
    }

    @Override
    public String getName() {
        return "remove_lower";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "{vehicle} | Removes element from collection that are lower than given.";
    }

    public static void attach(VehicleCollection collection){
        RemoveLower.collection = collection;
    }

    private Vehicle vehicle;

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        Set<Long> IDList = new HashSet<>();
        vehicle = new Vehicle((Set) IDList);

        return new RemoveLower(this);
    }

    @Override
    public String execute() throws CommandExecutionException {
        return collection.removeLower(vehicle) + "\n";
    }
}