package pt.isel.ls.html;

public class Html {

    public static Element html(Element head, Element body) {
        return new Element("<html>" + head.elem + body.elem + "\n</html>");
    }

    public static Element head(String head) {
        return new Element("\n\t<head>" + head + "\t</head>");
    }

    public static String title(String title) {
        return "\n\t\t<title> " + title + " </title>";
    }

    public static Element body(Element headers) {
        return new Element("\n\t<body>"+ headers.elem + "\n\t</body>");
    }

    public static Element body(Element headers, Element section, Element results) {
        return new Element("\n\t<body>"+ headers.elem + section.elem + results.elem + "\n\t</body>");
    }

    //anchor(title, class, Element elem)
    public static String anchor(String title, String class__, String link, Element elem) {
        return
            "<a "
                    + "href=\"" + link + "\""
                    + (title == null ? "" : "title=\"" + title + "\"")
                    + (class__ == null ? "" :  "class=\"" + class__ + "\"")
                    + "style=\"margin:2px;\""
                    + ">"
                    + (elem == null ? "" : elem.elem)
            + "</a>";
    }

    public static Element h1(String section) {
        return new Element("\n\t\t<h1> " + section + " </h1>");
    }

    public static Element ul(String section) {
        return new Element("\n\t\t<ul>" + section + "\n\t\t</ul>");
    }

    public static String getSpan(String rating, String color) {
        return "<span style=\"color:" + color + ";font-weight:bold;;\">" + rating + "</span>";
    }

    public static String h2(String header2) {
        return "\n\t\t<h2> " + header2 + " </h2>";
    }

    public static String p(String text) {
        return "<p>" + text + "</p>";
    }

    public static String li(String text) {
        return "\n\t\t\t<li>" + text + "</li>";
    }

}
