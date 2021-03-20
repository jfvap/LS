package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.CompleteReview;
import pt.isel.ls.model.Result;

public class ResultPostReviewByMovieId extends ResultsHandler {
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
        return "ResultPostReviewByMovieId";
    }
}
