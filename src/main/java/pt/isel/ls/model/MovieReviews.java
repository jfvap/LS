package pt.isel.ls.model;

import java.util.LinkedList;

public class MovieReviews implements Result {
    public Movie movie;
    public LinkedList<Result> listReviews;// Result = ReviewUser

    public MovieReviews(Movie movie) {
        listReviews = new LinkedList<>();
        this.movie = movie;
    }

    public void addReview(Result review) {
        listReviews.add(review);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var rev : listReviews) {
            sb.append(rev.toString()).append("\n");
        }
        return "MovieReviews{" +
                "movie=" + movie.toString() +
                ", listReviews=\n" + sb.toString() +
                '}';
    }
}
