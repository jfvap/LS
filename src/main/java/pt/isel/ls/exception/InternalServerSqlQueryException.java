package pt.isel.ls.exception;

public class InternalServerSqlQueryException extends Exception {
    private final String message;

    public InternalServerSqlQueryException() {
        this.message = "INTERNAL SERVER EXCEPTION IN DATABASE";
    }

    public InternalServerSqlQueryException(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }
}
