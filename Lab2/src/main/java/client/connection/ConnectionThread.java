package client.connection;

import client.ClientLogic;
import jsonParser.JsonMessage;
import jsonParser.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionThread extends Thread {
    private Socket socket;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean work;
    private ClientLogic clientLogic;

    public ConnectionThread(Socket socket, ClientLogic clientLogic) throws IOException {
        this.socket = socket;
        this.clientLogic = clientLogic;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
        clientLogic.setInput(input);
        clientLogic.setOutput(output);
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public ObjectInputStream getInput() {
        return input;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public boolean isWork() {
        return work;
    }

    public void setWork(boolean work) {
        this.work = work;
    }

    @Override
    public void run() {
        work = true;
        try {

            while (work) {
                String message = (String) input.readObject();
                System.out.println("input " + message);
                JsonMessage jsonMessage = new JsonMessage(message);
                clientLogic.doAction(jsonMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            output.close();
        } catch (IOException e) {
        }
        try {
            input.close();
        } catch (IOException e) {
        }
        try {
            socket.close();
        } catch (IOException e) {
        }
    }

    public ClientLogic getClientLogic() {
        return clientLogic;
    }
}
