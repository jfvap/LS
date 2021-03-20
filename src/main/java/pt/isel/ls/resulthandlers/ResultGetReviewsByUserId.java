package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.*;

public class ResultGetReviewsByUserId extends ResultsHandler {
    private UserReviews user;

    @Override
    public void addResult(Result result) {
        this.user = (UserReviews) result;
    }

    @Override
    public boolean isEmpty() {
        return user.listReviews.isEmpty();
    }

    @Override
    public Result getResults() {
        return user;
    }

    @Override
    public String toString() {
        return "ResultGetReviewsByUserId";
    }
}
