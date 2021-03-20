package pt.isel.ls.model;

import java.util.LinkedList;

public class Errors implements Result {
    public LinkedList<String> errors;

    public Errors(LinkedList<String> errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var err : errors) {
            sb.append("ERROR : ").append(err);
        }
        return sb.toString();
    }
}
