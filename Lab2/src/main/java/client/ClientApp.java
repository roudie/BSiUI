package client;

import client.connection.ConnectionThread;
import cnrypt.MatrixCipher;
import cnrypt.VigenereCipher;
import jsonParser.JsonMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
    private String serverAdress = "";
    private int serverPort = 0;
    private Object lock;

    public ClientApp() {
        if(serverAdress=="") serverAdress = askServerAdress();
        if(serverPort==0) serverPort = askServerPort();
        if(serverPort==0) serverPort = askServerPort();

        lock = new Object();

        Socket socket = null;
        try {
            socket = new Socket(serverAdress, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClientLogic clientLogic = new ClientLogic(lock);

        ConnectionThread connectionThread = null;
        try {
            connectionThread = new ConnectionThread(socket, clientLogic);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connectionThread.start();
        menu(connectionThread);
    }

    public static void main(String[] args){
        ClientApp clientApp = new ClientApp();
    }

    private void menu(ConnectionThread connectionThread) {
        boolean end = false;
        do {
            System.out.println("1. Zaloguj się");
            System.out.println("2. Załóż konto");
            Scanner scanner = new Scanner(System.in);
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    loginMenu(connectionThread);
                    break;
                case 2:
                    regMenu(connectionThread);
                    break;
                default:
                    end = true;
            }
        } while (!end);
    }

    private void regMenu(ConnectionThread connectionThread) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj login");
        String login = scanner.next();
        System.out.println("Podaj haslo");
        String pass1 = scanner.next();
        System.out.println("Podaj haslo");
        String pass2 = scanner.next();
        try {
            JsonMessage jssonMessage = connectionThread.getClientLogic().Reg(login, pass1, pass2);
            connectionThread.getClientLogic().sendMessage(jssonMessage);
            synchronized (lock) {
                lock.wait();
            }
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void loginMenu(ConnectionThread connectionThread) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj login");
        String login = scanner.next();
        System.out.println("Podaj haslo");
        String pass1 = scanner.next();
        JsonMessage jsonMessage = connectionThread.getClientLogic().Log(login, pass1);
        connectionThread.getClientLogic().sendMessage(jsonMessage);
        try {
            synchronized (lock) {
                lock.wait();
            }
        }  catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static int askClientPort() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj port");
        return scanner.nextInt();
    }

    private static int askServerPort() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj port serwera");
        return scanner.nextInt();
    }

    private static String askServerAdress() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj adres servera (np 192.168.1.21");
        return scanner.next();
    }

    public Object getLock() {
        return lock;
    }
}
