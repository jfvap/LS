package pt.isel.ls.input;

public enum Method {
    EXIT, POST, GET, DELETE, OPTIONS, LISTEN;

    public boolean isEnum(String cmdInserted) {
        return this.name().equals(cmdInserted.toUpperCase());
    }
}
