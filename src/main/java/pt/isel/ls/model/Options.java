package pt.isel.ls.model;

import java.util.LinkedList;

public class Options implements Result {
    LinkedList<String> listOptions;

    public Options(LinkedList<String> listOptions) {
        this.listOptions = listOptions;
    }

    public boolean isEmpty() {
        return listOptions.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var op : listOptions) {
            sb.append(op).append("\n");
        }
        return sb.toString();
    }
}
