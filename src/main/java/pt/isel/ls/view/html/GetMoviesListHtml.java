package pt.isel.ls.view.html;

import pt.isel.ls.model.*;
import pt.isel.ls.resulthandlers.ResultGetMoviesList;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;

import java.io.Writer;
import static pt.isel.ls.html.Html.*;
import static pt.isel.ls.html.TableHtml.*;
import static pt.isel.ls.html.TableHtml.td;

public class GetMoviesListHtml extends OverviewHtml {
    private static final String[] HEADERS = {"Movie ID", "Title", "Release Year", "Avg. Rating"};
    private static final String SECTION = "Movies";
    private static final String TITLE = "Movies List";

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        ResultGetMoviesList result = (ResultGetMoviesList) commandResult;
        MoviesList movies = (MoviesList) result.getResults();//<td style="text-align:center;background-color:red;">2.2</td>
        Element headers = getHtmlHeaders(movies);
        Element results = getHtmlByModel(movies);
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

    private Element getHtmlHeaders(MoviesList movies) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                anchor(
                        "Go to HOME",
                        null,
                        "/",
                        new Element("HOME")
                )
        ).append(
                anchor(
                        "Go to movies",
                        null,
                        "/movies" + "?skip=0&top=5",
                        new Element("MOVIES ")
                )
        );

        return new Element(sb.toString());
    }


    private Element getHtmlByModel(MoviesList movies) {
        //HEADERS = {"Movie ID", "Title", "Release Year", "Avg. Rating"};
        return new Element(
                movies.isEmpty()
                        ?
                        p("   - No top ratings!!!!!")
                        :
                        table(
                                tr(
                                        th(HEADERS[0])
                                                + th(HEADERS[1])
                                                + th(HEADERS[2])
                                                + th(HEADERS[3])
                                )
                                        + trTdMovies(movies)
                        )
        );
    }

    private String trTdMovies(MoviesList movies) {
        StringBuilder sb = new StringBuilder();
        for (var movie : movies.topMoviesList) {
            TopMovie mov = (TopMovie) movie;
            sb.append(
                    tr(
                            td(
                                    anchor(null,
                                            null,
                                            "/movies/" + mov.movie.id,
                                            new Element(mov.movie.id + "")
                                    )
                            )
                            + td(mov.movie.title)
                            + td(mov.movie.releaseYear + "")
                            + td(mov.averageRating + "", getColor(mov.averageRating))
                            //+ td(getSpan(
                            //        String.format("%.2f", mov.averageRating), getColor(mov.averageRating)
                            //))
                    )
            );
        }
        return sb.toString();
    }


    private String getColor(double rating) {
        if (rating <= 2.5) {
            return "red";
        } else if (rating >= 4) {
            return "limegreen";
        } else {
            return "darkorange";
        }
    }
}