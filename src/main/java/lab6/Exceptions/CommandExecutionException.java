package lab6.Exceptions;

public class CommandExecutionException extends Exception
{
    public CommandExecutionException(String errorMessage) {
        super(errorMessage);
    }
}