package pt.isel.ls.view.html;

import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;
import pt.isel.ls.model.CompleteReview;
import java.io.Writer;
import static pt.isel.ls.html.Html.*;

public class GetCompleteReviewByUserIdHtml extends OverviewHtml {
    private static final String[] HEADERS = {"Review ID", "Movie Title", "User Name", "Summary", "Review", "Rating"};
    private static final String SECTION = "Complete Review";
    private static final String TITLE = "Review";

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        CompleteReview review = (CompleteReview) commandResult.getResults();
        Element headers = getHtmlHeaders(review);
        Element results = getHtmlByModel(review);
        Element html =
                html(
                        head(
                                title(TITLE)
                        ),
                        body(
                                headers,
                                h1(SECTION),
                                results
                        )

                );

        return printResultHtml(writer, html);
    }
    private Element getHtmlHeaders(CompleteReview review) {
        String class__ = "";
        return new Element(
                anchor(
                        "Go to HOME",
                        null,
                        "/",
                        new Element("HOME")
                )
                +
                anchor(
                        "Go to reviews user" + review.user.name,
                        class__,
                        "/users/" + review.user.id + "/reviews",
                        new Element("Reviews user " + review.user.name)
                )
        );
    }


    private Element getHtmlByModel(CompleteReview review) {
        return ul(
                li(HEADERS[0] + ": "+ review.review.id)
                        + li(HEADERS[1] + ": "+ review.movie.title)
                        + li(HEADERS[2] + ": "+ review.user.id)
                        + li(HEADERS[3] + ": "+ review.review.summary)
                        + li(HEADERS[4] + ": "+ review.review.review)
                        + li(HEADERS[5] + ": "+ review.review.rating)
        );
    }
}
