package pt.isel.ls.view.html;

import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;
import pt.isel.ls.model.ReviewMovie;
import pt.isel.ls.model.UserReviews;

import java.io.Writer;

import static pt.isel.ls.html.Html.*;
import static pt.isel.ls.html.TableHtml.*;

public class GetUserByIdHtml extends OverviewHtml {
    private static final String[] HEADERS_USER = {"User ID", "Name", "Email"};
    private static final String[] HEADERS_REVIEW = {"Review ID", "Movie Title", "Summary", "Rating"};
    private static final String SECTION = "User detail";
    private static final String TITLE = "User";
// todo falta o botao para reviews do user id

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        UserReviews user = (UserReviews) commandResult.getResults();
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
                        +
                        anchor(
                                "Go to users",
                                null,
                                "/users",
                                new Element("USERS ")
                        )
                        +
                        anchor(
                                "Go to Reviews",
                                null,
                                "/users/" + user.user.id + "/reviews",
                                new Element("REVIEWS")
                        )
        );
    }

    private Element getHtmlByModel(UserReviews user) {
        //{"User ID", "Name", "Email"};
        String userDetail =
                ul(
                        li(HEADERS_USER[0] + ": " + user.user.id)
                                + li(HEADERS_USER[1] + ": " + user.user.name)
                                + li(HEADERS_USER[2] + ": " + user.user.email)
                ).elem;
        //{"Review ID", "Movie Title", "Summary", "Rating"};
        String revs =
                user.listReviews.isEmpty()
                        ?
                        p(" - No reviews!!!!!")
                        :
                        table(
                                tr(
                                        th(HEADERS_REVIEW[0])
                                                + th(HEADERS_REVIEW[1])
                                                + th(HEADERS_REVIEW[2])
                                                + th(HEADERS_REVIEW[3])
                                )
                                        + trTdReviews(user)
                        );
        return new Element(userDetail + revs);
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
                                            "/movies/" + rev.movie.id + "/reviews/" + rev.review.id,
                                            new Element(rev.review.id + "")
                                    )
                                            + td(rev.movie.title)
                                            + td(rev.review.summary)
                                            + td(rev.review.rating + "")
                            )
                    )
            );
        }
        return sb.toString();
    }
}
