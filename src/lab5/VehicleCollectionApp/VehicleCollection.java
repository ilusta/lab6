package lab5.VehicleCollectionApp;

import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import lab5.VehicleCollectionApp.Exceptions.NullException;
import lab5.VehicleCollectionApp.Vehicle.Vehicle;

import java.util.LinkedHashMap;


public class VehicleCollection {
    LinkedHashMap<String, Vehicle> collection;
    String fileName = null;

    public VehicleCollection() {
        this.collection = new LinkedHashMap<String, Vehicle>();
    }

    public void setFileName(String fileName) throws NullException {
        if (fileName == null) throw new NullException("File name is NULL.");
        this.fileName = fileName;
    }

    public void open() throws SecurityException, FileNotFoundException, IOException {
        InputStreamReader input = new InputStreamReader(new FileInputStream(fileName));
        input.close();
    }

    public void save() throws FileNotFoundException, IOException {
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(fileName));

        String data = "";

        Set<String> keys = this.collection.keySet();
        for (String key : keys) {
            String s = key + "," +
                    collection.get(key).getID() + ";" +
                    collection.get(key).getName() + ";" +
                    collection.get(key).getCoordinates().getXCoordinate() + ";" +
                    collection.get(key).getCoordinates().getYCoordinate() + ";" +
                    collection.get(key).getCreationDate() + ";" +
                    collection.get(key).getEnginePower() + ";" +
                    collection.get(key).getNumberOfWheels() + ";" +
                    collection.get(key).getCapacity() + ";" +
                    collection.get(key).getType().toString() + "\r\n";

            data += s;
        }

        output.write(data.getBytes());
        output.close();

        System.out.println("Collection saved to " + fileName);
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
        this.collection.put(newKey, new Vehicle((Set) IDList));
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