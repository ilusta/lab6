package lab6.Commands;

import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.EOFInputException;
import lab6.Exceptions.InputException;
import lab6.UserInput.UserInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;


public class ExecuteScript extends Command
{
    private static ArrayList<Command> commandList;

    @Override
    public String getName() {
        return "execute_script";
    }
    @Override
    public CommandType getType() {
        return CommandType.ALL;
    }
    @Override
    public String getHelp() {
        return "[file] | Execute commands from given file.";
    }

    private String filePath = "";

    @Override
    public Command build(String[] params) throws InputException, EOFInputException {
        if (params.length < 2) throw new InputException("File name is missing");
        filePath = params[1];
        return this;
    }

    @Override
    public String execute() throws CommandExecutionException {
        StringBuilder message = new StringBuilder("Switching to file:" + filePath + "\n");

        try{
            UserInput.addFileReader(new File(filePath));
        }
        catch (Exception e){
            throw new CommandExecutionException("Unable to open file: " + e.getMessage());
        }

        message.append("\tDone\n");
        return message.toString();
    }
}
