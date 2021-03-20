package pt.isel.ls.exception;

public class NoCommandException extends Exception {
    private String message;

     public NoCommandException() {
        message = "Invalid handler";
    }
    public NoCommandException(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }
}
