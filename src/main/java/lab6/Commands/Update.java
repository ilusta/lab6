package lab6.Commands;

import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.EOFInputException;
import lab6.Exceptions.InputException;
import lab6.Server.VehicleCollectionServer.VehicleCollection;
import lab6.Vehicle.Vehicle;

import java.util.HashSet;
import java.util.Set;

public class Update extends CollectionCommand
{

    @Override
    public String getName() {
        return "update";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "[ID] {vehicle} | Updates element in collection by it`s ID.";
    }

    private Long ID;
    private Vehicle vehicle;

    public static void attach(VehicleCollection collection){
        Update.collection = collection;
    }

    @Override
    public Command build(String[] params) throws InputException, EOFInputException{
        if (params.length < 2) throw new InputException("Argument is missing");
        try {
            ID = Long.parseLong(params[1]);
        }
        catch(NumberFormatException e)
        {
            throw new InputException("Impossible vehicle ID");
        }

        Set<Long> IDList = new HashSet<>();
        vehicle = new Vehicle((Set) IDList);
        return this;
    }

    @Override
    public String execute() throws CommandExecutionException{
        return collection.update(ID, vehicle) + "\n";
    }
}