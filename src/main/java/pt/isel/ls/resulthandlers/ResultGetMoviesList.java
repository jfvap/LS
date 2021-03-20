package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.MoviesList;
import pt.isel.ls.model.Result;

public class ResultGetMoviesList extends ResultsHandler {
    private MoviesList movies = null;

    @Override
    public void addResult(Result result) {
        this.movies = (MoviesList) result;
    }
    @Override
    public boolean isEmpty() {
        return movies.isEmpty();
    }

    @Override
    public Result getResults() {
        return movies;
    }

    @Override
    public String toString() {
        return "ResultGetMoviesList";
    }
}
