package pt.isel.ls.exception;

public class ForeignkeyException extends Exception {
    private final String message;

    public ForeignkeyException(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }

}
