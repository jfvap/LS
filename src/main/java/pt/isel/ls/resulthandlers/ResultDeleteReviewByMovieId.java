package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.CompleteReview;
import pt.isel.ls.model.Result;

public class ResultDeleteReviewByMovieId extends ResultsHandler {
    private CompleteReview review = null;
    private String message = null;

    @Override
    public boolean isEmpty() {
        return review == null;
    }

    @Override
    public Result getResults() {
        return review;
    }

    @Override
    public void addResult(Result result) {
        this.review = (CompleteReview) result;
    }

    @Override
    public String toString() {
        return "ResultDeleteReviewByMovieId";
    }

    public void putMessage(String message) {
        this.message = message;
    }
}
