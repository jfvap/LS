package pt.isel.ls.model;

public class Movie implements Result {
    public int id;
    public String title;
    public int releaseYear;

    public Movie(int id, String title, int releaseYear) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public Movie(int id) {
        this.id = id;
    }

    public Movie(int id, String title) {
        this.id = id;
        this.title = title;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseYear='" + releaseYear + '\'' +
                '}';
    }
}
