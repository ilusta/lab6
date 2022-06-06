package lab6.Commands;

import lab6.Client.VehicleCollectionClient.VehicleCollectionClient;
import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.EOFInputException;
import lab6.Exceptions.InputException;

import java.util.ArrayList;


public class Disconnect extends Command
{
    private static ArrayList<Command> commandList;

    @Override
    public String getName() {
        return "disconnect";
    }
    @Override
    public CommandType getType() {
        return CommandType.CLIENT;
    }
    @Override
    public String getHelp() {
        return "Disconnects from server";
    }

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        return this;
    }

    @Override
    public String execute() throws CommandExecutionException {
        VehicleCollectionClient.disconnect();
        return "";
    }
}