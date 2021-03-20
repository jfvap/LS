package pt.isel.ls.model;

public class TopMovie implements Result {
    public Movie movie;
    public double averageRating;

    public TopMovie(Movie movie, double averageRating) {
        this.movie = movie;
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "Movie {"
                + movie.toString()
                + ", averageRating=" + averageRating
                + '}';
    }

}
