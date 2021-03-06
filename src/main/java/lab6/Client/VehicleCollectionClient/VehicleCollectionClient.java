package lab6.Client.VehicleCollectionClient;

import java.io.*;

import lab6.Commands.*;
import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.ConnectionException;
import lab6.Exceptions.EOFInputException;
import lab6.Commands.CommandBuilder;
import lab6.Commands.CommandExecutor;
import lab6.UserInput.UserInput;

import java.util.ArrayList;


public class VehicleCollectionClient
{

    private static final ArrayList<Command> commandList = new ArrayList<>();
    private static final ArrayList<Command> allCommandList = new ArrayList<>();


    public void run() {
        System.out.println("Welcome to the Vehicle Collection Client!");
        UserInput.setDefaultReader(new BufferedReader(new InputStreamReader(System.in)));

        commandList.add(new Help());
        commandList.add(new Connect());
        commandList.add(new Disconnect());
        commandList.add(new Exit());
        commandList.add(new History());
        commandList.add(new ExecuteScript());

        Help.attachCommandList(commandList);

        CommandBuilder commandBuilder = new CommandBuilder();
        CommandExecutor executor = new CommandExecutor();
        CommandBuilder.setCommandList(commandList);

        System.out.println("Initialization complete");
        while (Exit.getRunFlag()) {
            try {
                if(UserInput.getFilesStackSize() == 0) System.out.print("->");
                Command command = commandBuilder.build();

                if (!commandList.contains(command)) {
                    if (ClientConnectionHandler.isConnected()) {
                        ClientConnectionHandler.write(command);
                        System.out.println(ClientConnectionHandler.read());
                    }
                    else
                        throw new CommandExecutionException("Not connected to the server");
                }
                else {
                    System.out.println(executor.execute(command));
                }
            } catch (Exception e) {
                if (e instanceof EOFInputException) {
                    if(UserInput.getFilesStackSize() > 0){
                        UserInput.removeReader();
                        continue;
                    }
                    break;
                }
                System.out.println("Error: " + e.getMessage());
                if(e instanceof ConnectionException) disconnect();
            }
        }


        ClientConnectionHandler.disconnect();
        UserInput.removeReader();
        System.out.println("Goodbye!");
    }


    public static void disconnect(){
        ClientConnectionHandler.disconnect();

        allCommandList.clear();
        allCommandList.addAll(commandList);
        Help.attachCommandList(allCommandList);
        CommandBuilder.setCommandList(allCommandList);
        System.out.println("\tServer commands deleted from list");
    }

    public static void connect(String[] args){
        try {
            ClientConnectionHandler.connect(args);

            System.out.println("Receiving available commands:");
            allCommandList.clear();
            allCommandList.addAll(commandList);

            int counter = 0;
            while (true) {
                Object obj = ClientConnectionHandler.read();
                if (obj instanceof String && obj.equals("End"))
                    break;
                else if (obj instanceof Command) {
                    counter++;
                    allCommandList.add((Command) obj);
                }
            }
            Help.attachCommandList(allCommandList);
            CommandBuilder.setCommandList(allCommandList);
            System.out.println("\t" + counter + " commands received");
        }
        catch (Exception e){
            System.out.println("\tError occurred while connecting to server: " + e);
            disconnect();
        }
    }
}