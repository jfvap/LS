package pt.isel.ls.view.html;

import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;

import java.io.Writer;
import java.nio.charset.StandardCharsets;

import static pt.isel.ls.html.Html.*;

/**
 * Root page HTML View
 */
public class RootHtml extends OverviewHtml  {
    public static final Element rootPage =
        html(
            head(
                title("Home page")
            ),
            body(
                new Element(
                    anchor("Go to MOVIES",
                    "button",
                    "/movies?skip=0&top=5",
                    new Element("<button style=\"margin:2px;\">" + "MOVIES" + "</button>")
                    )
                    +
                    anchor(
                        "Go to USERS",
                        "button",
                        "/users",
                        new Element("<button style=\"margin:2px;\">" + "USERS" + "</button>")
                    )
                    +
                    anchor("Go to TOP RATINGS",
                       "button",
                       "/tops/ratings?n=5&average=highest&min=1",
                        new Element("<button style=\"margin:2px;\">" + "TOP RATINGS" + "</button>")
                    )
                )
            )
        );

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        return printResultHtml(writer, rootPage);
    }
}
