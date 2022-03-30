package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.VehicleCollection;

import java.io.FileNotFoundException;

public class Save extends CollectionCommand
{
    public Save(VehicleCollection collection) {
        super(collection);
    }

    @Override
    public void execute(String[] params){
        try {
            collection.save();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getHelp() {
        return "Saves collection.";
    }
}