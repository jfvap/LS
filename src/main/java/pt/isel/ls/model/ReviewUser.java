package pt.isel.ls.model;

public class ReviewUser implements Result {
    public Review review;
    public User user;

    public ReviewUser(Review review, User user) {
        this.review = review;
        this.user = user;
    }

    @Override
    public String toString() {
        return "ReviewUser{" +
                "review=" + review +
                ", user=" + user +
                '}';
    }
}
