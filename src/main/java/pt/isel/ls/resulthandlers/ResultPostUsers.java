package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.Result;
import pt.isel.ls.model.User;

public class ResultPostUsers extends ResultsHandler {
    private User user = null;

    @Override
    public void addResult(Result result) {
        this.user= (User) result;
    }

    @Override
    public Result getResults() {
        return user;
    }

    @Override
    public boolean isEmpty() {
        return getResults() == null;
    }

    @Override
    public String toString() {
        return "ResultPostUsers";
    }
}
