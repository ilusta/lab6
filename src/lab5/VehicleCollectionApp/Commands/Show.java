package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.VehicleCollection;

public class Show extends CollectionCommand
{
    public Show(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params) {
        collection.show();
    }

    @Override
    public String getHelp() {
        return "Prints all elements of collection.";
    }
}