package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.VehicleCollection;

public class Clear extends CollectionCommand
{
    public Clear(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params) {
        collection.clear();
    }

    @Override
    public String getHelp() {
        return "Clears collection without saving.";
    }
}