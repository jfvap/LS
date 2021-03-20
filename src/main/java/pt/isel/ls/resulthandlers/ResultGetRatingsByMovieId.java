package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.NumberRatingsByValue;
import pt.isel.ls.model.Result;

public class ResultGetRatingsByMovieId extends ResultsHandler {
    private NumberRatingsByValue ratingsByValue = null;

    @Override
    public void addResult(Result result) {
        this.ratingsByValue = (NumberRatingsByValue) result;
    }

    @Override
    public Result getResults() {
        return ratingsByValue;
    }

    @Override
    public boolean isEmpty() {
        return ratingsByValue.mapVotes.isEmpty();
    }

    @Override
    public String toString() {
        return "ResultGetRatingsByMovieId";
    }
}
