package lab5.VehicleCollectionApp.Commands;

import lab5.VehicleCollectionApp.Exceptions.InputException;
import java.util.ArrayList;

public class History extends Command
{
    private ArrayList<String> commandList;

    public History(ArrayList<String> commandList) {
        this.commandList = commandList;
    }

    @Override
    public void execute(String[] params) throws InputException {
        int x = commandList.size();

        if (x == 0) System.out.println("History is empty.");
        else {
            if (x > 11) x = 11;
            for (int i = 0; i < x; ++i) {
                System.out.println(commandList.get(i));
            }
        }
    }

    @Override
    public String getHelp() {
        return "Prints last 11 executed commands.";
    }
}
