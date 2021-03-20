package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.Movie;
import pt.isel.ls.model.Result;

public class ResultPostMovies extends ResultsHandler {
    public Movie movie = null;

    @Override
    public void addResult(Result result) {
        this.movie = (Movie) result;
    }

    @Override
    public Result getResults() {
        return movie;
    }

    @Override
    public boolean isEmpty() {
        return movie == null;
    }

    @Override
    public String toString() {
        return "ResultPostMovies";
    }
}