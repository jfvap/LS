package pt.isel.ls.model;

import java.util.LinkedList;

public class MoviesList implements Result{
    public LinkedList<Result> topMoviesList; // Result = TopMovie

    public MoviesList() {
        this.topMoviesList = new LinkedList<>();
    }

    public boolean isEmpty() {
        return this.topMoviesList.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var movie : topMoviesList) {
            sb.append(movie.toString()).append("\n");
        }
        return "MoviesList{" +
                "topMoviesList=" + sb +
                '}';
    }
}
