package pt.isel.ls.resulthandlers;

import pt.isel.ls.commandrequest.CommandResult;

public abstract class ResultsHandler implements CommandResult {
    private static final boolean ERROR = false;
    private String message = null;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean isError() {
        return ERROR;
    }
}
