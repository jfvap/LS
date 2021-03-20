package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.*;

public class ResultGetMovieById extends ResultsHandler {
    private MovieReviews movie = null;

    @Override
    public void addResult(Result result) {
        this.movie = (MovieReviews) result;
    }

    @Override
    public boolean isEmpty() {
        return movie == null;
    }

    @Override
    public Result getResults() {
        return movie;
    }

    @Override
    public String toString() {
        return "CommandResultGetMovieById";
    }
}

