package pt.isel.ls.model;

import java.util.LinkedList;

public class Movies implements Result {
    public LinkedList<Result> listMovies;// Result = Movie

    public Movies() {
        this.listMovies = new LinkedList<>();
    }

    public boolean isEmpty() {
        return listMovies.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var movie : listMovies) {
            sb.append(movie.toString()).append("\n");
        }
        return "Movies{\n" +
                sb +
                '}';
    }
}
