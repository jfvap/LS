package pt.isel.ls.view.html;

import pt.isel.ls.html.Element;
import pt.isel.ls.http.InfoHttp;

import java.io.Writer;

import static pt.isel.ls.html.Html.*;

public class ErrorView extends OverviewHtml {
    //private static final String TITLE = "error";

    public boolean printResult(Writer writer, InfoHttp infoError) {
        Element headers = new Element(
                h2("HTTP status code - " + infoError.statusCode.getStatus() + " "
                        + infoError.statusCode.name())
                + (infoError.messageError != null ? p(infoError.messageError) : "")
        );
        Element html =
                html(
                        head(
                                title(infoError.statusCode.name())
                        ),
                        body(
                                headers
                        )
                );
        return printResultHtml(writer, html);
    }
}
