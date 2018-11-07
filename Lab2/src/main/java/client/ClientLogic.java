package client;

import cnrypt.MatrixCipher;
import cnrypt.Md5;
import cnrypt.VigenereCipher;
import cnrypt.XorCipher;
import jsonParser.JsonMessage;
import jsonParser.MessageType;
import org.json.JSONArray;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ClientLogic {
    private String login = "";
    private Object mainMenuLock;
    private Object loggedMenuLock;
    private ObjectOutputStream output = null;
    private ObjectInputStream input = null;
    boolean xor = true;
    boolean vigienere = true;
    boolean matrix = true;

    public ClientLogic(Object mainMenuLock) {

        this.mainMenuLock = mainMenuLock;
    }

    public JsonMessage Reg(String login, String pass1, String pass2) {
        JsonMessage jsonMessage = new JsonMessage(MessageType.REG, login, pass1, pass2);
        return jsonMessage;
    }

    public JsonMessage Log(String login, String pass1) {
        JsonMessage jsonMessage = new JsonMessage(MessageType.LOGIN, login, pass1);
        this.login = login;
        return jsonMessage;
    }

    public void doAction(JsonMessage jsonMessage) {
        JSONArray jsonArray;
        List<Object> list;
        switch (jsonMessage.getMsgType()) {
            case LOGIN:
                System.out.println(jsonMessage.getP2());
                if (jsonMessage.getP1().equals("true"))
                    new LoggedMenu(this);
                else {
                    login = "";
                    continueMainMenu();
                }
                break;
            case REG:
                System.out.println(jsonMessage.getP2());
                continueMainMenu();
                break;
            case LOGOUT:
                if (jsonMessage.getP1().equals("true"))
                    continueMainMenu();
                break;
            case LIST:
                jsonArray = new JSONArray(jsonMessage.getP1());
                list = jsonArray.toList();
                for (Object object:list) {
                    System.out.println((String)object);
                }
                continueLoggedMenu();
                break;
            case FIND:
                jsonArray = new JSONArray(jsonMessage.getP1());
                list = jsonArray.toList();
                for (Object object:list) {
                    System.out.println((String)object);
                }
                continueLoggedMenu();
                break;
            case TEXT:
                if(jsonMessage.getP1().equals("true")) {
                    System.out.println("Wiadomosc dostarczono");
                } else {
                    if(jsonMessage.getP3()==null) {
                        System.out.println(jsonMessage.getP2());
                    } else {
                        System.out.println("Wiadnomosc od " + jsonMessage.getP1());
                        String message = jsonMessage.getP3();
                        if (matrix)
                            message = MatrixCipher.Decrypt(message);
                        if (xor)
                            message = XorCipher.Decrypt(jsonMessage.getP1(), jsonMessage.getP2(), message);
                        if (vigienere)
                            message = VigenereCipher.Decrypt(jsonMessage.getP1(), jsonMessage.getP2(), message);
                        System.out.println(message);
                    }
                }
                break;
            case PONG:

                break;
                default:
                    System.out.println("nieobslugiwany format wiadomosci");
        }
    }

    public String getLogin() {
        return login;
    }

    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }


    public void Logout(String login) {
        JsonMessage jsonMessage = new JsonMessage(MessageType.LOGOUT, login);
        sendMessage(jsonMessage);
    }

    public void sendMessage(JsonMessage jsonMessage) {
        if(jsonMessage.getMsgType()==MessageType.TEXT)
        {
            String message = jsonMessage.getP3();

            if(vigienere)
                message = VigenereCipher.Encrypt(jsonMessage.getP1(), jsonMessage.getP2(), message);
            if(xor)
                message = XorCipher.Encrypt(jsonMessage.getP1(), jsonMessage.getP2(), message);
            if(matrix)
                message = MatrixCipher.Encrypt(message);
            //System.out.println(message);

            jsonMessage=new JsonMessage(
                    jsonMessage.getP0(),
                    jsonMessage.getP1(),
                    jsonMessage.getP2(),
                    message);
        } else if (jsonMessage.getMsgType()==MessageType.LOGIN) {
            jsonMessage=new JsonMessage(
                    jsonMessage.getP0(),
                    jsonMessage.getP1(),
                    Md5.Hash(jsonMessage.getP2()));
        } else if (jsonMessage.getMsgType()==MessageType.REG)
        {
            jsonMessage=new JsonMessage(
                    jsonMessage.getP0(),
                    jsonMessage.getP1(),
                    Md5.Hash(jsonMessage.getP2()),
                    Md5.Hash(jsonMessage.getP3()));
        }
        sendMessage(jsonMessage.toString());
    }

    private void sendMessage(String jssonMessage) {
        try {
            System.out.println("output " + jssonMessage);
            output.writeObject(jssonMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void continueMainMenu() {
        synchronized (mainMenuLock) {
            mainMenuLock.notify();
        }
    }

    private void continueLoggedMenu() {
        synchronized (loggedMenuLock) {
            loggedMenuLock.notify();
        }
    }

    public void List(boolean logged) {
        String x = logged ? "true" : "false";
        JsonMessage jsonMessage = new JsonMessage(MessageType.LIST, login, x);
        sendMessage(jsonMessage);
    }

    public void text(String recip, String message) {
        JsonMessage jsonMessage = new JsonMessage(MessageType.TEXT, login, recip, message);
        sendMessage(jsonMessage);
    }

    public Object getLoggedMenuLock() {
        return loggedMenuLock;
    }

    public void setLoggedMenuLock(Object loggedMenuLock) {
        this.loggedMenuLock = loggedMenuLock;
    }

    public void find(String buffor, boolean logged) {
        String x = logged ? "true" : "false";
        JsonMessage jsonMessage = new JsonMessage(MessageType.FIND, login, buffor, x);
        sendMessage(jsonMessage);
    }
}
