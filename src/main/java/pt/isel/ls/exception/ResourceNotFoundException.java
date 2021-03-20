package pt.isel.ls.exception;

public class ResourceNotFoundException extends Exception {
    private String message;

    public ResourceNotFoundException() {
        this.message = "NOT FOUND";
    }

    public ResourceNotFoundException(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }
}
