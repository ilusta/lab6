package lab6.Exceptions;

public class EOFInputException extends Exception
{
    public EOFInputException(String errorMessage) {
        super(errorMessage);
    }
}