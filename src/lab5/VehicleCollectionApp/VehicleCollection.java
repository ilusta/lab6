package lab5.VehicleCollectionApp;

import lab5.VehicleCollectionApp.CSVParser.CSVParser;
import lab5.VehicleCollectionApp.Exceptions.CommandExecutionException;

import java.io.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import lab5.VehicleCollectionApp.Exceptions.EOFInputException;
import lab5.VehicleCollectionApp.Exceptions.InputException;
import lab5.VehicleCollectionApp.Exceptions.NullException;
import lab5.VehicleCollectionApp.UserInput.UserInput;
import lab5.VehicleCollectionApp.Vehicle.Vehicle;
import lab5.VehicleCollectionApp.Vehicle.VehicleType;


public class VehicleCollection {
    LinkedHashMap<String, Vehicle> collection;
    String fileName = null;
    private ZonedDateTime creationDate;

    public VehicleCollection() {
        this.collection = new LinkedHashMap<>();
        this.creationDate = ZonedDateTime.now();
    }

    public void setFileName(String fileName) throws NullException {
        if (fileName == null) throw new NullException("File name is NULL.");
        this.fileName = fileName;
    }

    public void open() throws SecurityException, IOException, InputException, DateTimeParseException {
        System.out.println("Loading collection from " + fileName);

        InputStreamReader input = new InputStreamReader(new FileInputStream(fileName));
        Set<Long> IDList = new HashSet<>();

        if(input.ready())
        try {
            ArrayList<String> params = CSVParser.readLine(input);
            if (params.size() < 1) throw new InputException("Argument missing.");
            this.creationDate = ZonedDateTime.parse(params.get(0), DateTimeFormatter.ISO_ZONED_DATE_TIME);
        }
        catch (Exception e) {
            System.out.println("Loading error. " + e.getMessage());
        }

        while(input.ready()) {
            try {
                ArrayList<String> params = CSVParser.readLine(input);
                if(params.size() < 10) throw new InputException("Argument missing.");

                Double p6;
                Long p7;
                if (params.get(6).equals("")) p6 = null;
                else p6 = Double.parseDouble(params.get(6));
                if (params.get(7).equals("")) p7 = null;
                else p7 = Long.parseLong(params.get(7));

                collection.put(params.get(0), new Vehicle(Long.parseLong(params.get(1)),
                        params.get(2),
                        Integer.parseInt(params.get(3)),
                        Integer.parseInt(params.get(4)),
                        params.get(5),
                        p6,
                        p7,
                        Double.parseDouble(params.get(8)),
                        params.get(9),
                        IDList));
                IDList.add(Long.parseLong(params.get(1)));
            }
            catch (Exception e)
            {
                System.out.println("Loading error. " + e.getMessage());
            }
        }

        input.close();
        System.out.println("Load completed: " + collection.size() + " vehicles loaded.");
    }

    public void save() throws FileNotFoundException, IOException {
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(fileName));
        StringBuilder data = new StringBuilder("\"" + this.getCreationDate().toString() + "\";\n");

        Set<String> keys = this.collection.keySet();
        for (String key : keys) {
            String line[] = {key,
                    String.valueOf(collection.get(key).getID()),
                    String.valueOf(collection.get(key).getName()),
                    String.valueOf(collection.get(key).getCoordinates().getXCoordinate()),
                    String.valueOf(collection.get(key).getCoordinates().getYCoordinate()),
                    String.valueOf(collection.get(key).getCreationDate()),
                    String.valueOf(collection.get(key).getEnginePower()),
                    String.valueOf(collection.get(key).getNumberOfWheels()),
                    String.valueOf(collection.get(key).getCapacity()),
                    String.valueOf(collection.get(key).getType())};
            data.append(CSVParser.convertToLine(line));
        }

        output.write(data.toString().getBytes());
        output.close();

        System.out.println("Collection saved to " + fileName);
    }

    public void show() {
        Set<String> keys = this.collection.keySet();

        if (keys.isEmpty()) System.out.println("Collection is empty");

        for (String key : keys) {
            System.out.println("\tKey=" + key + ": " + this.collection.get(key).toString());
        }
    }

    public void insert(String newKey) throws CommandExecutionException, EOFInputException {
        Set<String> keys = this.collection.keySet();
        Set<Long> IDList = new HashSet<>();

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

    public void update(Long ID) throws CommandExecutionException, EOFInputException {
        Set<String> keys = this.collection.keySet();

        if(ID == null) throw new CommandExecutionException("Impossible ID.");

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
        this.creationDate = ZonedDateTime.now();
    }

    public Integer getSize() {
        return this.collection.size();
    }

    public Long getSumOfWheels(){
        Set<String> keys = this.collection.keySet();

        Long sum = (long)0;
        for (String k : keys) {
            Long x = collection.get(k).getNumberOfWheels();
            if(x == null) x = (long)0;
            sum += x;
        }

        return sum;
    }

    public void removeLower() throws EOFInputException, CommandExecutionException{
        Vehicle x = new Vehicle(new HashSet<>());
        int counter = 0;

        Set<String> keys = this.collection.keySet();
        if (keys.isEmpty()) throw new CommandExecutionException("Collection is empty");
            for (String k : keys) {
                if (x.compareTo(collection.get(k)) > 0) {
                    collection.remove(k);
                    counter++;
                }
            }
        System.out.println(counter + " vehicles deleted.");
    }

    public void removeGreaterKey(String key) throws CommandExecutionException{
        int counter = 0;

        Set<String> keys = this.collection.keySet();
        if (keys.isEmpty()) throw new CommandExecutionException("Collection is empty");

            for (String k : keys) {
                if (k.compareTo(key) > 0) {
                    collection.remove(k);
                    counter++;
                }
            }
            System.out.println(counter + " vehicles deleted.");
    }

    public String maxByCoordinates() throws CommandExecutionException{
        Set<String> keys = this.collection.keySet();
        if (keys.isEmpty()) throw new CommandExecutionException("Collection is empty");

            String max = "";
            boolean flag = false;
            for (String k : keys) {
                if (flag == false) {
                    max = k;
                    flag = true;
                }
                if (collection.get(k).getCoordinates().compareTo(collection.get(max).getCoordinates()) > 0) {
                    max = k;
                }
            }
            return collection.get(max).toString();
    }

    public String filterByType() throws CommandExecutionException, EOFInputException{

        VehicleType t = null;
        boolean flag = false;
        while (!flag) {
            try {
                System.out.println("Possible vehicle types:");
                System.out.println(VehicleType.convertToString());
                t = VehicleType.valueOf(UserInput.getString("type"));
                flag = true;
            }
            catch (IOException | IllegalArgumentException e) {
                System.out.println(e.getMessage() + ". Wrong type. Please, enter valid value.");
            }
        }

        Set<String> keys = this.collection.keySet();
        if (keys.isEmpty()) throw new CommandExecutionException("Collection is empty");

        StringBuilder output = new StringBuilder();
        for (String k : keys) {
            if (collection.get(k).getType().equals(t)) {
                output.append(collection.get(k).toString() + "\n");
            }
        }
        return output.toString();
    }

    public ZonedDateTime getCreationDate(){
        return this.creationDate;
    }

    public String info(){
        return "\tLinked hash map collection\n" +
                "\tConsists of " + this.getSize() + " vehicles\n" +
                "\tCreation date: " + this.getCreationDate().toString();
    }
}