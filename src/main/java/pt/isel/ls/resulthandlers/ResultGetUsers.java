package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.Result;
import pt.isel.ls.model.Users;

public class ResultGetUsers extends ResultsHandler {
    private Users users;

    @Override
    public void addResult(Result result) {
        this.users = (Users) result;
    }

    @Override
    public Result getResults() {
        return users;
    }

    @Override
    public boolean isEmpty() {
        return users.isEmpty();
    }

    @Override
    public String toString() {
        return "CommandResultGetUsers";
    }
}
