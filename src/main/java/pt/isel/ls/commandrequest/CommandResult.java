package pt.isel.ls.commandrequest;

import pt.isel.ls.model.Result;

public interface CommandResult {

    void addResult(Result result);

    /**
     * @return returns model results
     */
    Result getResults();

    boolean isEmpty();

    String getMessage();

    void setMessage(String message);

    boolean isError();
}
