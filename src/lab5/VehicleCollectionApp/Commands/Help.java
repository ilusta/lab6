package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.InputException;
import java.util.HashMap;

public class Help extends Command
{
    HashMap<String, Command> commandList;

    public Help(HashMap<String, Command> commandList) {
        this.commandList = commandList;
    }

    @Override
    public void execute(String[] params) throws InputException {
        commandList.forEach((key, command) -> System.out.println("\t" + key + " - " + command.getHelp()));
    }

    @Override
    public String getHelp() {
        return "Prints all supported commands.";
    }
}