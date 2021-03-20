package pt.isel.ls.view.html;

import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;
import pt.isel.ls.model.MovieReviews;
import pt.isel.ls.model.Result;
import pt.isel.ls.model.ReviewUser;

import java.io.Writer;
import java.util.LinkedList;

import static pt.isel.ls.html.Html.*;
import static pt.isel.ls.html.TableHtml.*;

public class GetMovieByIdHtml extends OverviewHtml {
    private static final String[] HEADERS_MOVIE = {"Movie ID", "Title", "Release Year"};
    private static final String[] HEADERS_REVIEW = {"Review ID", "User Name", "Summary", "Rating"};
    private static final String SECTION = "Movie details";
    private static final String TITLE = "Movie Detail";

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        MovieReviews movie = (MovieReviews) commandResult.getResults();
        Element headers = getHtmlHeaders(movie);
        Element results = getHtmlByModel(movie);
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
                        "Go to movies",
                        null,
                        "/movies" + "?skip=0&top=5",
                        new Element("MOVIES ")
                )
                +
                anchor(
                        "Go to Reviews",
                        null,
                        "/movies/" + movie.movie.id + "/reviews",
                        new Element("REVIEWS")
                )
                +
                anchor(
                        "Go to Ratings",
                        null,
                        "/movies/" + movie.movie.id + "/ratings",
                        new Element("RATINGS")
                )
        );
    }


    private Element getHtmlByModel(MovieReviews movie) {
        String movieDetail =
                ul(
                li(HEADERS_MOVIE[0] + ": "+ movie.movie.id)
                        + li(HEADERS_MOVIE[1] + ": "+ movie.movie.title)
                        + li(HEADERS_MOVIE[2] + ": "+ movie.movie.releaseYear)
        ).elem;
        //{"Review ID", "User Name", "Summary", "Rating"}
        String revs =
                movie.listReviews.isEmpty()
                ?
                p("   - No reviews!!!!!")
                :
                table(
                        tr(
                                th(HEADERS_REVIEW[0])
                                +th(HEADERS_REVIEW[1])
                                +th(HEADERS_REVIEW[2])
                                +th(HEADERS_REVIEW[3])
                        )
                        + trTdReviews(movie.listReviews, movie.movie.id)
                );
        return new Element(movieDetail + revs);
    }

    private String trTdReviews(LinkedList<Result> list, int id) {
        StringBuilder sb = new StringBuilder();

        sb.append(h2("Movie Reviews"));
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
}
