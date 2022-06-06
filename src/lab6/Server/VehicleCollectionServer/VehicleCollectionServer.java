package lab6.Server.VehicleCollectionServer;

import lab6.Commands.*;
import lab6.Exceptions.CommandExecutionException;
import lab6.Exceptions.EOFInputException;
import lab6.UserInput.UserInput;

import java.io.*;
import java.util.ArrayList;


public class VehicleCollectionServer {

    final ArrayList<Command> commandList = new ArrayList<>();
    final ArrayList<Command> clientCommandList = new ArrayList<>();

    public void run() {
        System.out.println("Welcome to the Vehicle Collection Server!");
        UserInput.setDefaultReader(new BufferedReader(new InputStreamReader(System.in)));

        final VehicleCollection collection = new VehicleCollection();

        try {
            final String file = EnvVarReader.getValue("VEHICLE_COLLECTION_PATH");
            collection.setFileName(file);
            collection.open();
        } catch (Exception e) {
            System.out.println("Error occurred while reading file:");
            System.out.println(e.getMessage());
        }

        commandList.add(new Help());
        commandList.add(new Exit());
        commandList.add(new History());
        commandList.add(new Save());

        clientCommandList.add(new Info());
        clientCommandList.add(new Show());
        clientCommandList.add(new Insert());
        clientCommandList.add(new Update());
        clientCommandList.add(new RemoveKey());
        clientCommandList.add(new Clear());
        clientCommandList.add(new SumOfNumberOfWheels());
        clientCommandList.add(new MaxByCoordinates());
        clientCommandList.add(new FilterByType());
        clientCommandList.add(new RemoveGreaterKey());
        clientCommandList.add(new RemoveLower());

        ArrayList<Command> allCommandList = new ArrayList<>();
        allCommandList.addAll(commandList);
        allCommandList.addAll(clientCommandList);

        Help.attachCommandList(allCommandList);
        Save.attach(collection);
        Info.attach(collection);
        Show.attach(collection);
        Insert.attach(collection);
        Update.attach(collection);
        RemoveKey.attach(collection);
        Clear.attach(collection);
        SumOfNumberOfWheels.attach(collection);
        MaxByCoordinates.attach(collection);
        FilterByType.attach(collection);
        RemoveGreaterKey.attach(collection);
        RemoveLower.attach(collection);

        CommandExecutor executor = new CommandExecutor();
        CommandBuilder builder = new CommandBuilder();
        CommandBuilder.setCommandList(allCommandList);

        ServerConnectionHandler.startServer();

        System.out.println("Initialization complete");
        while(Exit.getRunFlag() && ServerConnectionHandler.isServerStarted()) {

            if (!ServerConnectionHandler.isConnected()) {
                ServerConnectionHandler.listenForConnection();

                if(ServerConnectionHandler.isConnected()) {
                    System.out.println("Sending available commands to client");
                    for (Command c : clientCommandList)
                        ServerConnectionHandler.write(c);
                    ServerConnectionHandler.write("End");
                    System.out.println("\tDone");
                }
            } else {
                try{
                    Command command = (Command) ServerConnectionHandler.read();
                    if(command != null) {
                        System.out.println("Received command from client");
                        if (command instanceof Exit)
                            throw new CommandExecutionException("\tDeprecated command");

                        System.out.println("\tExecuting command");
                        ServerConnectionHandler.write(executor.execute(command));
                        System.out.println("\tResponse send to client");
                    }
                } catch (Exception e) {
                    System.out.println("Error occurred while executing client`s command: " + e.getMessage());
                }
            }

            try {
                if (UserInput.available()) {
                    System.out.println(executor.execute(builder.build()));
                }
            } catch (Exception e) {
                if (e instanceof EOFInputException) break;
                System.out.println("Error while executing command: " + e.toString());
            }
        }

        try{
        System.out.println(collection.save());
        } catch(Exception e){
            System.out.println("Error occurred while saving collection: " + e.getMessage());
        }
        ServerConnectionHandler.disconnect();
        UserInput.removeReader();
        System.out.println("Goodbye!");
    }
}


















