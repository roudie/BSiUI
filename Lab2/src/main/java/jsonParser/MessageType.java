package jsonParser;

public enum MessageType {
    UNKNOWN("UNKNOWN"),
    REG("REG"),
    LOGIN("LOGIN"),
    TEXT("TEXT"),
    LIST("LIST"),
    FIND("FIND"),
    LOGOUT("LOGOUT"),
/*    LOAD("LOAD"),*/
    PONG("PONG"),
    PING("PING");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
