package pt.isel.ls.html;

public class TableHtml {

    /**
     * Creates a new table and returns the string for that table (made out
     * of array 'headers' and results (list 'data')
     */
    public static String table(String htmlText) {
        String s = "\n\t\t\t<table border=1>"
                + htmlText
                + "\n\t\t\t</table>";
        return s;
    }

    public static String td(String text) {
        return "\n\t\t\t<td style=\"text-align:center;\">" + text + "</td>";
    }

    public static String td(String text, String color) {
        String background = color == null ? "" : "background-color:" + color;
        return "\n\t\t\t<td style=\"text-align:center;" + background + "\">" + text + "</td>";
    }

    /**
     * @return a new 'table row' with 'section'
     */
    public static String tr(String section) {
        return "\n\t\t\t<tr>\t\t\t" + section + "\n\t\t\t</tr>";
    }

    public static String th(String header) {
        String thi = "\n\t\t\t<th bgcolor=\"#382e28\" style=\"color: white\">";
        String thf = "</th>";
        return thi + header + thf;
    }
}
