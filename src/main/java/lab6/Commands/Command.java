package lab6.Commands;

import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.EOFInputException;
import lab6.Exceptions.InputException;

import java.io.Serializable;

public abstract class Command implements Serializable
{
    public abstract String getName();
    public abstract CommandType getType();
    public abstract String getHelp();

    public abstract String execute() throws CommandExecutionException;
    public abstract Command build(String[] param) throws InputException, EOFInputException;
}