package pt.isel.ls.html;

public class Element {
    public String elem;

    public Element(String string) {
        this.elem = string;
    }

    public void add(String string) {
        this.elem += string;
    }

    @Override
    public String toString() {
        return elem;
    }
}