package lab6.Server.VehicleCollectionServer;

import lab6.Exceptions.EOFInputException;
import lab6.UserInput.UserInput;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.apache.commons.lang3.*;

public class ServerConnectionHandler {

    static boolean connected = false;
    static boolean serverStarted = false;
    static private ObjectInputStream inputStream = null;
    static private ObjectOutputStream outputStream = null;
    static private SocketChannel socketChannel = null;
    static private ServerSocketChannel serverSocketChannel = null;
    static private ByteArrayOutputStream baos;
    static private ByteArrayInputStream bais;

    static private ByteBuffer rxBuffer;
    static private ByteBuffer txBuffer;


    public static void startServer(){
        while (true) {
            try {
                System.out.println("Enter port to start server:");
                System.out.print("->");
                String[] words = UserInput.readLine().split(" +");
                int port = Integer.parseInt(words[0]);

                InetSocketAddress inetAddress = new InetSocketAddress(port);
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.socket().bind(inetAddress);
                serverSocketChannel.configureBlocking(false);

                baos = new ByteArrayOutputStream();
                txBuffer = ByteBuffer.allocate(2048);
                rxBuffer = ByteBuffer.allocate(2048);

                System.out.println("Server is started at " + inetAddress.getAddress() + ":" + serverSocketChannel.socket().getLocalPort());
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

            socketChannel = serverSocketChannel.accept();
            if(socketChannel != null) {
                socketChannel.configureBlocking(false);

                System.out.println("Client connecting");
                System.out.println("\tChannel has been created: " + socketChannel);

                txBuffer.clear();
                rxBuffer.clear();

                outputStream = new ObjectOutputStream(baos);
                outputStream.flush();
                txBuffer.put(baos.toByteArray());
                baos.reset();

                bais = new ByteArrayInputStream(rxBuffer.array());
                inputStream = null;
                do {
                    try {
                        update();
                        inputStream = new ObjectInputStream(bais);
                        rxBuffer.position(rxBuffer.limit());
                        rxBuffer.compact();
                    }catch (Exception e) {
                        inputStream.close();
                    }
                } while(inputStream == null);

                System.out.println("\tClient has connected");
                connected = true;
            }
        } catch (Exception e) {
            connected = false;
            if (!(e instanceof SocketTimeoutException)) {
                System.out.println("\tUnable to accept connection: " + e);
            }
        }
    }


    public static String disconnect(){
        StringBuilder message = new StringBuilder("Disconnecting from clients:\n");
        try {
            socketChannel.close();
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


    public static void update(){
        try {
            int n = socketChannel.read(rxBuffer);
            if (n == -1) throw new IOException("Unable to read");

            txBuffer.flip();
            n = socketChannel.write(txBuffer);
            if (n == -1) throw new IOException("Unable to send");
            txBuffer.compact();
        }
        catch (Exception e){
            if (e instanceof IOException || e instanceof NullPointerException) {
                System.out.println("Connection with client is lost: " + e.getMessage());
                disconnect();
            } else {
                System.out.println("Error occurred while communicating with client: " + e.getMessage());
            }
        }
    }


    public static void write(Object obj){
        try {
            outputStream.writeObject(obj);
            outputStream.reset();
            outputStream.flush();
            if(baos.size() > txBuffer.remaining()){
                baos.reset();
                outputStream.writeObject("Unable to send response. Message is too big.\n");
                outputStream.reset();
                outputStream.flush();
            }
            txBuffer.put(baos.toByteArray());
            baos.reset();
        }
        catch(Exception e){
            System.out.println("Error occurred while serializing object: ");
        }
    }


    public static Object read(){
        Object obj = null;
        try {
            rxBuffer.flip();
            if(rxBuffer.remaining() != 0) {
                byte[] bytes = new byte[rxBuffer.remaining()+6];
                for (int i = rxBuffer.position(); i < rxBuffer.limit(); i++)
                    bytes[i+4] = rxBuffer.get(i);

                bytes[0] = -84;
                bytes[1] = -19;
                bytes[2] = 0;
                bytes[3] = 5;
                obj = SerializationUtils.deserialize(bytes);
            }
            rxBuffer.position(rxBuffer.limit());
            rxBuffer.compact();
        }
        catch(Exception e){
            System.out.println("Error occurred while deserializing object: " + e.getMessage());
        }

        return obj;
    }

}
