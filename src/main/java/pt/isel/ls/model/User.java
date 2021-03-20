package pt.isel.ls.model;

public class User implements Result {
    public int id;
    public String name;
    public String email;

    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Users {"
                + "id=" + id
                + ", name='" + name + '\''
                + ", email='" + email + '\''
                + '}';
    }

}