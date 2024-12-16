package com.codeforall.online;

import java.io.*;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private Scanner scannerInput;

    private FileInputStream in;
    private BufferedOutputStream out;

    private BufferedInputStream inStream;
    private FileOutputStream outStream;

    public Client(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("Connected to " + host + ":" + port);
            scannerInput = new Scanner(System.in);
            sendMessage();

        } catch (IOException e) {
            System.err.println("Could not read message from the user");
        }
    }

    private void sendMessage() throws IOException {
        openStreams();
        String message;
        while (true) {
            message = readMessage();
            System.out.println(message);
            sendMessageToServer(message);

            if (message.equalsIgnoreCase("BYE")
                    || message.equalsIgnoreCase("DISCONNECT")
                    || message.equalsIgnoreCase("QUIT")) {
                readMessageFromServer();
                exit();
            }

            if (message.equalsIgnoreCase("HELP")) {
                String serverMessage = "";
                while (serverMessage != null) {
                    serverMessage = bufferedReader.readLine();
                    System.out.println(serverMessage);
                    if (Objects.equals(serverMessage, "")) {
                        break;
                    }
                }
            }

            if (message.equalsIgnoreCase("LS")) {
                String serverMessage;
                System.out.println("List of files on server:");
                while ((serverMessage = bufferedReader.readLine()) != null) {
                    if (!serverMessage.equals("no files")) {
                        System.out.println("  -> " + serverMessage);
                    }
                    if (serverMessage.equals("no files")) {
                        break;
                    }
                }
            }

            if (message.equalsIgnoreCase("PUT")) {
                System.out.println("Please specify the filename:");
                String userInput;
                while ((userInput = readMessage()) != null) {
                    System.out.println("Filename is: " + userInput);
                    sendFileName(userInput);
                    readFile(userInput);
                    break;
                }
            }

            if (message.equalsIgnoreCase("GET")) {
                System.out.println("Please write the filename and press enter:");
                String fileName = null;

                while (fileName == null) {
                    fileName = scannerInput.nextLine();
                }
                System.out.println(fileName);
                requestFile(fileName);
                receiveFile(fileName);
                System.out.println("File created");
            }

            if (message.equalsIgnoreCase("MKDIR")) {
                System.out.println("Please write the directory name and press enter:");
                String fileName = null;

                while (fileName == null) {
                    fileName = scannerInput.nextLine();
                }
                sendMessageToServer(fileName);
            }
        }
    }

    private void requestFile(String fileName) {
        sendMessageToServer(fileName);
    }

    private void sendFileName(String userInput) {
        sendMessageToServer(userInput);
    }

    private void readFile(String source) throws IOException {

        in = new FileInputStream("clientRoot/" + source);
        out = new BufferedOutputStream(socket.getOutputStream());

        byte[] buffer = new byte[1024];

        int bytesRead = in.read(buffer);

        while (bytesRead != -1) {
            out.write(buffer, 0, bytesRead);
            bytesRead = in.read(buffer);
        }
        out.flush();
        in.close();
    }

    private void receiveFile(String fileName) throws IOException {

        if (fileName == null) {
            return;
        }

        String destination = "clientRoot/" + fileName;

        inStream = new BufferedInputStream(socket.getInputStream());
        outStream = new FileOutputStream(destination);

        byte[] buffer = new byte[1024];

        int bytesRead = inStream.read(buffer);

        while (bytesRead != -1) {
            if (bytesRead < buffer.length) {
                break;
            }
            outStream.write(buffer, 0, bytesRead);
            bytesRead = inStream.read(buffer);
        }
    }

    private void exit() {
        closeResources();
        System.exit(0);
    }

    private void readMessageFromServer() {
       try {
           String clientMessage;
           clientMessage = bufferedReader.readLine();
           System.out.println(clientMessage);
       }
       catch (Exception e) {
           System.err.println("Couldn't read message: " + e.getMessage());
       }
    }

    private void sendMessageToServer(String message) {
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        }
        catch (IOException e) {
            System.err.println("Could not send message: " + e.getMessage());
        }
    }

    private String readMessage() {
        return scannerInput.nextLine();
    }

    private void closeResources() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("Could not close socket");
        }
    }

    private void openStreams() {
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            System.err.println("Could not open streams.");
        }
    }
}
