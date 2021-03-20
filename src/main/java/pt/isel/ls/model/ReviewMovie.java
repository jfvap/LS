package pt.isel.ls.model;

public class ReviewMovie implements Result{
    public Review review;
    public Movie movie;

    public ReviewMovie(Review review, Movie movie) {
        this.review = review;
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "ReviewMovie{" +
                "review=" + review +
                ", movie=" + movie +
                '}';
    }
}
