package pt.isel.ls.model;

import java.util.LinkedList;

public class Users implements Result {
    public LinkedList<Result> listUsers;// Result = User

    public Users() {
        this.listUsers = new LinkedList<>();
    }

    public boolean isEmpty() {
        return listUsers.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var user : listUsers) {
            sb.append(user.toString()).append("\n");
        }
        return sb.toString();
    }
}
