package pt.isel.ls.model;

import java.util.LinkedList;

public class UserReviews implements Result {
    public LinkedList<Result> listReviews = new LinkedList<>();//Result = ReviewMovie
    public User user;

    public UserReviews(User user) {
        this.user = user;
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

        return "UserReviews{" +
                ", user=" + user +
                "listReviews=" + sb +
                '}';
    }
}
