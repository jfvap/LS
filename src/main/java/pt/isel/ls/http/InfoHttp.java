package pt.isel.ls.http;

public class InfoHttp {
    public String messageError;
    public StatusCode statusCode;

    public InfoHttp(String messageError, StatusCode statusCode) {
        this.messageError = messageError;
        this.statusCode = statusCode;
    }

    public InfoHttp(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
