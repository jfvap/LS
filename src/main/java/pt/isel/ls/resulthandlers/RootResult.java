package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.Result;

public class RootResult extends ResultsHandler {

    @Override
    public void addResult(Result result) {
        return;
    }

    @Override
    public Result getResults() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public String toString() {
        return "RootResult";
    }
}
