package pt.isel.ls.exception;

public class IllegalPathParametersException extends Exception {
    private String message;

    public IllegalPathParametersException() {
        this.message = "ILLEGAL PARAMS PATH EXCEPTION";
    }

    public IllegalPathParametersException(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }
}
