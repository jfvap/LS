package pt.isel.ls.model;

public class Review implements Result {
    public int id;
    public String summary;
    public String review;
    public int rating;
    public boolean includeComplete;

    public Review(int id) {
        this.id = id;
    }

    public Review(int id, String summary, String review, int rating) {
        this.id = id;
        this.summary = summary;
        this.review = review;
        this.rating = rating;
        this.includeComplete = true;
    }

    public Review(int id, String summary, int rating) {
        this.id = id;
        this.summary = summary;
        this.review = null;
        this.rating = rating;
        this.includeComplete = false;
    }

    @Override
    public String toString() {
        String s = "";
        s += "Review {"
                + "id=" + id
                + ", summary='" + summary + '\'';
        if (includeComplete) {
            s += ", review='" + review + '\'';
        }
        s += ", rating=" + rating + '}';
        return s;
    }
}