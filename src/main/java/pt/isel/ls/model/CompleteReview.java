package pt.isel.ls.model;

public class CompleteReview implements Result{
    public Movie movie;
    public User user;
    public Review review;

    public CompleteReview(Movie movie, User user, Review review) {
        this.movie = movie;
        this.user = user;
        this.review = review;
    }

    @Override
    public String toString() {
        return "CompleteReview{" +
                "movie=" + movie.toString() +
                ", user=" + user.toString() +
                ", review=" + review.toString() +
                '}';
    }
}
