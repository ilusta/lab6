package lab6.Server.VehicleCollectionServer;

import lab6.Exceptions.EOFInputException;
import lab6.Exceptions.InputException;
import lab6.UserInput.UserInput;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.channels.SocketChannel;

public class ServerConnectionHandler {

    static boolean connected = false;
    static boolean serverStarted = false;
    static private ServerSocket serverSocket = null;
    static private Socket socket = null;
    static private ObjectInputStream inputStream = null;
    static private ObjectOutputStream outputStream = null;
    static private SocketChannel socketChannel = null;


    public static void startServer(){
        while (true) {
            try {
                System.out.println("Enter port to start server:");
                System.out.print("->");
                String[] words = UserInput.readLine().split(" +");
                int port = Integer.parseInt(words[0]);

                serverSocket = new ServerSocket(port);
                serverSocket.setSoTimeout(1000);
                InetAddress inetAddress = serverSocket.getInetAddress();
                System.out.println("Server is started at " + inetAddress.getHostAddress() + ":" + serverSocket.getLocalPort());
                //serverSocketChannel =
                serverStarted = true;
                break;
            } catch (Exception e) {
                if (e.getClass() == EOFInputException.class){
                    break;
                }
                System.out.println("Unable to start server: " + e.getMessage());
                System.out.println("Please, try again.");
            }
        }
    }


    public static boolean isServerStarted(){
        return serverStarted;
    }


    public static void listenForConnection(){
        try {
            //System.out.println("Listening for connection...");
            socket = serverSocket.accept();
            System.out.println("Client connecting");
            System.out.println("\tSocket has been created: " + socket);

            socket.setSoTimeout(1000);
            //socketChannel = socket.getChannel();
            outputStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            outputStream.flush();
            inputStream = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));

            System.out.println("\tClient has connected");
            connected = true;
        } catch (Exception e) {
            connected = false;
            if (!(e instanceof SocketTimeoutException)) {
                System.out.println("\tUnable to accept connection: " + e.getMessage());
            }
        }
    }


    public static String disconnect(){
        StringBuilder message = new StringBuilder("Disconnecting from clients:\n");
        try {
            socket.close();
            connected = false;
            message.append("\tDisconnected\n");
        }
        catch(Exception e){
            message.append("\tError occurred while closing socket: " + e.getMessage() + "\n");
        }
        return message.toString();
    }


    public static boolean isConnected(){
        return connected;
    }


    public static void write(Object obj){
        try {
            outputStream.writeObject(obj);
            outputStream.flush();
        }
        catch(Exception e){
            if(e instanceof IOException || e instanceof NullPointerException){
                System.out.println("Connection with client is lost: " + e.getMessage());
                disconnect();
            }
            else {
                System.out.println("Error occurred while sending object to client: " + e.getMessage());
            }
        }
    }


    public static Object read(){
        Object obj = null;
        try{
            obj = inputStream.readObject();
        }
        catch(Exception e){
            if(!(e instanceof SocketTimeoutException)) {
                if (e instanceof IOException || e instanceof NullPointerException) {
                    System.out.println("Connection with client is lost: " + e.getMessage());
                    disconnect();
                } else {
                    System.out.println("Error occurred while reading object from client: " + e.getMessage());
                }
            }
        }
        return obj;
    }

}
