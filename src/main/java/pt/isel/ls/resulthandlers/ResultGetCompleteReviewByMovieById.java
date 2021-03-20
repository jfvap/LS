package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.*;

public class ResultGetCompleteReviewByMovieById extends ResultsHandler {
    private CompleteReview review = null;

    @Override
    public void addResult(Result result) {
        this.review = (CompleteReview) result;
    }

    @Override
    public boolean isEmpty() {
        return review == null;
    }

    @Override
    public Result getResults() {
        return review;
    }

    @Override
    public String toString() {
        return "ResultGetCompleteReviewByMovieById";
    }
}
