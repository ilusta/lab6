package lab6.Commands;

import lab6.Client.VehicleCollectionClient.VehicleCollectionClient;
import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.EOFInputException;
import lab6.Exceptions.InputException;

import java.util.ArrayList;


public class Connect extends Command
{
    private static ArrayList<Command> commandList;

    @Override
    public String getName() {
        return "connect";
    }
    @Override
    public CommandType getType() {
        return CommandType.CLIENT;
    }
    @Override
    public String getHelp() {
        return "[ip.ip.ip.ip.port] | Connects to the server with given IP and port";
    }

    private String[] args;

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        try {
            args = params[1].split("[.:]");
        }
        catch (ArrayIndexOutOfBoundsException e){
            throw new InputException("Not enough arguments. See 'help' and try again.");
        }
        return this;
    }

    @Override
    public String execute() throws CommandExecutionException {
        VehicleCollectionClient.connect(args);
        return "";
    }
}