package pt.isel.ls.view.html;

import pt.isel.ls.Keys;
import pt.isel.ls.html.Form;
import pt.isel.ls.model.*;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;
import pt.isel.ls.resulthandlers.ResultGetReviewsByMovieId;

import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;

import static pt.isel.ls.html.Html.*;
import static pt.isel.ls.html.TableHtml.*;
import static pt.isel.ls.html.TableHtml.td;

public class GetReviewsByMovieIdHtml extends OverviewHtml {
    private static final String[] HEADERS = {"Review ID", "User Name", "Summary", "Rating"};
    private static final String SECTION = "Reviews list";
    private static final String TITLE = "Reviews";
    private static final String CONSTRAINTS_INPUT_FORM_UI =
            "type=\"number\" required=\"true\"  min=\"1\"";
    private static final String CONSTRAINTS_INPUT_FORM_RS =
            "type=\"text\" required=\"true\"  maxlength=\"120\"";
    private static final String CONSTRAINTS_INPUT_FORM_RV =
            "type=\"text\" required=\"true\" maxlength=\"1024\"";
    private static final String CONSTRAINTS_INPUT_FORM_RT =
            "type=\"number\" required=\"true\"  min=\"1\" max=\"5\"";

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        ResultGetReviewsByMovieId result = (ResultGetReviewsByMovieId) commandResult;
        MovieReviews reviews = (MovieReviews) result.getResults();
        Element headers = getHtmlHeaders(reviews);
        Element results = getHtmlByModel(reviews);
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

    private Element getHtmlHeaders(MovieReviews movie) {
        return new Element(
                anchor(
                        "Go to HOME",
                        null,
                        "/",
                        new Element("HOME")
                        )
                        +
                        anchor(
                                "Go to movie",
                                null,
                                "/movies/" + movie.movie.id,
                                new Element("MOVIE " + movie.movie.title)
                        )
        );
    }


    private Element getHtmlByModel(MovieReviews movie) {
        return new Element(
                (movie.listReviews.isEmpty()
                ?
                p(" No reviews!!!!!")
                :
                table(
                        tr(
                                th(HEADERS[0])
                                        +th(HEADERS[1])
                                        +th(HEADERS[2])
                                        +th(HEADERS[3])
                        )
                        + trTdReviews(movie.listReviews, movie.movie.id)
                ))
                + getForm()
        );
    }

    private String trTdReviews(LinkedList<Result> list, int id) {
        StringBuilder sb = new StringBuilder();
        for (var review : list) {
            ReviewUser rev = (ReviewUser) review;
            sb.append(
                    tr(
                            td(
                                    anchor(null,
                                            null,
                                            "/movies/" + id + "/reviews/" + rev.review.id,
                                            new Element(rev.review.id + "")
                                    )
                                            + td(rev.user.name)
                                            + td(rev.review.summary)
                                            + td(rev.review.rating + "")
                            )
                    )
            );
        }
        return sb.toString();
    }

    private Element getForm() {
        LinkedList<Form.InputForm> listInForm = new LinkedList<>(Arrays.asList(
                new Form.InputForm("User identifier: ", Keys.UID, CONSTRAINTS_INPUT_FORM_UI),
                new Form.InputForm("Summary: ", Keys.REVIEW_SUMMARY, CONSTRAINTS_INPUT_FORM_RS),
                new Form.InputForm("Review: ", Keys.REVIEW, CONSTRAINTS_INPUT_FORM_RV),
                new Form.InputForm("Rating: ", Keys.RATING, CONSTRAINTS_INPUT_FORM_RT)
        ));
        return new Form("Add a new Review", listInForm).getElementForm();
    }
}
