package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.*;

public class ResultGetReviewsByMovieId extends ResultsHandler {
    private MovieReviews movie;

    @Override
    public void addResult(Result result) {
        this.movie = (MovieReviews) result;
    }

    @Override
    public Result getResults() {
        return movie;
    }

    @Override
    public boolean isEmpty() {
        return movie.listReviews.isEmpty();
    }

    @Override
    public String toString() {
        return "ResultGetReviewsByMovieId";
    }
}

