package pt.isel.ls.resulthandlers;

import pt.isel.ls.model.Movie;
import pt.isel.ls.model.Movies;
import pt.isel.ls.model.Result;

public class ResultGetMovies extends ResultsHandler {
    private Movies movies;
    public int numberOfMovies;
    public int skip;
    public int top;

    @Override
    public void addResult(Result result) {
        this.movies = (Movies) result;
    }

    @Override
    public Result getResults() {
        return movies;
    }

    @Override
    public boolean isEmpty() {
        return movies.listMovies.isEmpty();
    }

    @Override
    public String toString() {
        return "CommandResultGetMovies";
    }

    public void setNumberOfMovies(int len) {
        numberOfMovies = len;
    }

    /*
    public int getNumOfMovies() {
        return numberOfMovies;
    }

    public int getSkip() {
        return skip;
    }

    public int getTop() {
        return top;
    }*/
}
