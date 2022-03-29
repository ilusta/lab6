package lab5.VehicleCollectionApp.UserInput;

import lab5.VehicleCollectionApp.Exceptions.InputException;
import java.io.IOException;
import java.io.BufferedReader;

public class UserInput
{
    static BufferedReader reader;

    public static void setReader(BufferedReader reader) {
        UserInput.reader = reader;
    }

    public static void closeReader() {
        try {
            UserInput.reader.close();
        }
        catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }

    public static String readLine() throws IOException {
        return UserInput.reader.readLine();
    }

    public static String getString(String inputName) throws IOException {
        System.out.print("Enter " + inputName + ": ");
        return readLine();
    }

    public static String getWord(String inputName) throws IOException {
        return getString(inputName).split(" +")[0];
    }

    public static Integer getInteger(String inputName) throws IOException, InputException {
        String input = getWord(inputName);
        if (input.equals("")) return null;

        Integer i = null;
        try {
            i = Integer.parseInt(input);
        }
        catch (NumberFormatException e) {
            throw new InputException("Input is not integer");
        }

        return i;
    }

    public static Double getDouble(String inputName) throws IOException, InputException {
        String input = getWord(inputName);
        if (input.equals("")) return null;

        Double d = null;
        try {
            d = Double.parseDouble(input);
        }
        catch (NumberFormatException e) {
            throw new InputException("Input is not double");
        }

        return d;
    }

    public static Long getLong(String inputName) throws IOException, InputException {
        String input = getWord(inputName);
        if (input.equals("")) return null;

        Long l = null;
        try {
            l = Long.parseLong(input);
        }
        catch (NumberFormatException e) {
            throw new InputException("Input is not long");
        }

        return l;
    }
}