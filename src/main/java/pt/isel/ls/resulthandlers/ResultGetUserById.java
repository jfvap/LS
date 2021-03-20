package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.*;

public class ResultGetUserById extends ResultsHandler {
    private UserReviews user = null;

    @Override
    public void addResult(Result result) {
        this.user = (UserReviews) result;
    }

    @Override
    public boolean isEmpty() {
        return this.user == null;
    }

    @Override
    public Result getResults() {
        return user;
    }

    @Override
    public String toString() {
        return "ResultGetUserById";
    }
}
