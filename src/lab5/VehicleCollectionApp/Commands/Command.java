package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.EOFInputException;
import lab5.VehicleCollectionApp.Exceptions.InputException;

public abstract class Command
{
    public abstract void execute(String[] p0) throws InputException, CommandExecutionException, EOFInputException;

    public abstract String getHelp();
}