package lab6.Commands;

import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.EOFInputException;
import lab6.Exceptions.InputException;
import lab6.Server.VehicleCollectionServer.VehicleCollection;

public class SumOfNumberOfWheels extends CollectionCommand
{

    @Override
    public String getName() {
        return "sum_of_number_of_wheels";
    }
    @Override
    public CommandType getType() {
        return CommandType.SERVER;
    }
    @Override
    public String getHelp() {
        return "Prints sum of number of wheels of vehicles in collection.";
    }

    public static void attach(VehicleCollection collection){
        SumOfNumberOfWheels.collection = collection;
    }

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        return this;
    }

    @Override
    public String execute() throws CommandExecutionException {
        return collection.getSumOfWheels() + "\n";
    }
}