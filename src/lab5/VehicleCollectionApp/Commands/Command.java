package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import lab5.VehicleCollectionApp.Exceptions.InputException;

public abstract class Command
{
    public abstract void execute(String[] p0) throws InputException, CommandExecutionException;

    public abstract String getHelp();
}