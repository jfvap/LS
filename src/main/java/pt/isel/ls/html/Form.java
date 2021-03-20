package pt.isel.ls.html;

import java.util.LinkedList;

public class Form {
    public static class InputForm {
        public String label;
        public String name;
        public String constraints;

        public InputForm(String label, String name, String constraints) {
            this.label = label;
            this.name = name;
            this.constraints = constraints;
        }
    }

    private final String title;
    private final LinkedList<InputForm> in;

    public Form(String title, LinkedList<InputForm> in) {
        this.title = title;
        this.in = in;
    }

    /**
     * Returns HTML code for form containt the correct labels, names and constraints
     */
    public Element getElementForm() {
        StringBuilder html = new StringBuilder();
        html.append(Html.h2(title));
        html.append("<form method=\"POST\">\n<p></p>\n");
        for (InputForm obj : in) {
            html.append("<label>").append(obj.label).append("</label>\n");
            html.append("<input name=\"").append(obj.name).append("\"").append(obj.constraints).append(">\n");
            html.append("<p></p>\n");
        }
        html.append("<button type=\"submit\">SUBMIT</button>\n");
        html.append("<form>\n");
        return new Element(html.toString());
    }
}
