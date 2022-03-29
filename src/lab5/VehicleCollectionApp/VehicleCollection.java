package lab5.VehicleCollectionApp;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import lab5.VehicleCollectionApp.Vehicle.Vehicle;
import java.util.LinkedHashMap;


public class VehicleCollection
{
    LinkedHashMap<String, Vehicle> collection;

    public VehicleCollection() {
        this.collection = new LinkedHashMap<String, Vehicle>();
    }

    public void fillFromFile(String fileName) throws SecurityException, FileNotFoundException, IOException {
        InputStreamReader input = new InputStreamReader(new FileInputStream(fileName));
        input.close();
    }

    public void show() {
        Set<String> keys = this.collection.keySet();

        if (keys.isEmpty()) System.out.println("Collection is empty");

        for (String key : keys) {
            System.out.println("Key=" + key + ": " + this.collection.get(key).toString());
        }
    }

    public void insert(String newKey) throws CommandExecutionException {
        Set<String> keys = this.collection.keySet();
        Set<Long> IDList = new HashSet<Long>();

        boolean flag = true;
        for (String key : keys) {
            IDList.add(this.collection.get(key).getID());
            if (key.equals(newKey)) {
                flag = false;
            }
        }

        if (!flag) throw new CommandExecutionException("This key already exists.");
        this.collection.put(newKey, new Vehicle((Set)IDList));
    }

    public void update(Long ID) throws CommandExecutionException {
        Set<String> keys = this.collection.keySet();

        String key = null;
        for (String k : keys) {
            if (ID.equals(this.collection.get(k).getID())) {
                key = k;
            }
        }

        if (key == null) throw new CommandExecutionException("Vehicle with id " + ID + " does not exist.");
        this.collection.get(key).update();
    }

    public void removeKey(String removeKey) throws CommandExecutionException {
        Set<String> keys = this.collection.keySet();

        String key = null;
        for (String k : keys) {
            if (removeKey.equals(k)) {
                key = k;
            }
        }

        if (key == null) throw new CommandExecutionException("Element with key " + removeKey + " does not exist.");
        this.collection.remove(key);
        System.out.println("Element with key " + removeKey + " deleted.");
    }

    public void clear() {
        this.collection.clear();
        System.out.println("Collection is cleared");
    }

    public Integer getSize() {
        return this.collection.size();
    }
}