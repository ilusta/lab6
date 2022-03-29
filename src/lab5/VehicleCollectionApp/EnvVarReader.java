package lab5.VehicleCollectionApp;

import java.util.Map;
import lab5.VehicleCollectionApp.Exceptions.InputException;

public class EnvVarReader
{
    public static String getValue(String varName) throws SecurityException, InputException {

        Map<String, String> env = System.getenv();

        if (!env.containsKey(varName)) throw new InputException("Environment variable " + varName + " does not exist.");

        return env.get(varName);
    }
}