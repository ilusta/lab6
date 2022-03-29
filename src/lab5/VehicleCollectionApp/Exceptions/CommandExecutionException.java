package lab5.VehicleCollectionApp.Exceptions;

public class CommandExecutionException extends Exception
{
    public CommandExecutionException(String errorMessage) {
        super(errorMessage);
    }
}