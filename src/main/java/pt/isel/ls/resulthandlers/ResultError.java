package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.Errors;
import pt.isel.ls.model.Result;

public class ResultError extends ResultsHandler {
    private static final boolean ERROR = true;
    public Errors errors;

    @Override
    public void addResult(Result result) {
        this.errors = (Errors) result;
    }

    @Override
    public Result getResults() {
        return errors;
    }

    @Override
    public boolean isEmpty() {
        return errors.errors.isEmpty();
    }

    @Override
    public boolean isError() {
        return ERROR;
    }

    @Override
    public String toString() {
        return "CommandResultError";
    }
}
