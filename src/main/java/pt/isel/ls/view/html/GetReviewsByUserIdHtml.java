package pt.isel.ls.view.html;

import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;
import pt.isel.ls.model.ReviewMovie;
import pt.isel.ls.model.UserReviews;
import pt.isel.ls.resulthandlers.ResultGetReviewsByUserId;

import java.io.Writer;

import static pt.isel.ls.html.Html.*;
import static pt.isel.ls.html.TableHtml.*;

public class GetReviewsByUserIdHtml extends OverviewHtml {
    private static final String[] HEADERS = {"Review ID", "Movie Title", "Summary", "Rating"};
    private static final String SECTION = "Reviews list";
    private static final String TITLE = "Reviews";

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        ResultGetReviewsByUserId result = (ResultGetReviewsByUserId) commandResult;
        UserReviews user = (UserReviews) result.getResults();
        Element headers = getHtmlHeaders(user);
        Element results = getHtmlByModel(user);
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

    private Element getHtmlHeaders(UserReviews user) {
        return new Element(
                anchor(
                        "Go to HOME",
                        null,
                        "/",
                        new Element("HOME")
                )
                        + anchor(
                        "Go to users",
                        null,
                        "/users/" + user.user.id,
                        new Element("USER " + user.user.name)
                )
        );
    }

    private Element getHtmlByModel(UserReviews user) {
        // {"Review ID", "Movie Title", "Summary", "Rating"};
        return new Element(
                user.listReviews.isEmpty()
                        ?
                        p("   - No reviews!!!!!")
                        :
                        table(
                                tr(
                                        th(HEADERS[0])
                                                + th(HEADERS[1])
                                                + th(HEADERS[2])
                                                + th(HEADERS[3])
                                )
                                        + trTdReviews(user)
                        )
        );
    }

    private String trTdReviews(UserReviews user) {
        StringBuilder sb = new StringBuilder();
        for (var review : user.listReviews) {
            ReviewMovie rev = (ReviewMovie) review;
            sb.append(
                    tr(
                            td(
                                    anchor(null,
                                            null,
                                            "/users/" + user.user.id + "/reviews/" + rev.review.id,
                                            new Element(rev.review.id + "")
                                    )
                            )///users/1/movies/1
                                    + td(
                                    anchor(null,
                                            "",
                                            "/movies/" + rev.movie.id,
                                            new Element(rev.movie.title + "")
                                    )
                            )
                                    + td(rev.review.summary)
                                    + td(rev.review.rating + "")
                    )
            );
        }
        return sb.toString();
    }
}