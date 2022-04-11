package lab5.VehicleCollectionApp.Exceptions;

public class EOFInputException extends Exception
{
    public EOFInputException(String errorMessage) {
        super(errorMessage);
    }
}