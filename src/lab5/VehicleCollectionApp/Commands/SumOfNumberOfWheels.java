package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.VehicleCollection;

public class SumOfNumberOfWheels extends CollectionCommand
{
    public SumOfNumberOfWheels(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params) throws InputException {
        Long sum = collection.getSumOfWheels();
        if(sum == null) System.out.println("Vehicles in this collection do not have any wheels.");
        else System.out.println("Sum of number of wheels is " + collection.getSumOfWheels() + ".");
    }

    @Override
    public String getHelp() {
        return "Prints sum of number of wheels of all vehicles in collection.";
    }
}
