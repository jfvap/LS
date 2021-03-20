package pt.isel.ls.exception;

public class IllegalParametersException extends Exception {
    private String message;

    public IllegalParametersException() {
        this.message = "ILLEGAL PARAMETERS EXCEPTION";
    }

    public IllegalParametersException(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }
}
