package pt.isel.ls.view.html;

import pt.isel.ls.Keys;
import pt.isel.ls.html.Form;
import pt.isel.ls.model.*;
import pt.isel.ls.resulthandlers.ResultGetMovies;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;

import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;

import static pt.isel.ls.html.Html.*;
import static pt.isel.ls.html.TableHtml.*;
import static pt.isel.ls.html.TableHtml.td;

public class GetMoviesHtml extends OverviewHtml {
    private static final String SECTION = "Movies List";
    private static final String[] HEADERS = {"Movie ID", "Title", "Release Year"};
    private static final String TITLE = "Movies";
    private static final int LIMIT = 5;
    private static final String CONSTRAINTS_INPUT_FORM_TT =
            "type=\"text\" required=\"true\"";
    private static final String CONSTRAINTS_INPUT_FORM_RY =
            "type=\"number\" required=\"true\"";

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        ResultGetMovies result = (ResultGetMovies) commandResult;
        Movies movies = (Movies) result.getResults();
        Element headers = getHtmlHeaders(result);
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

    private Element getHtmlHeaders(ResultGetMovies result) {
        int sizeMovies = result.numberOfMovies;
        int skip = result.skip;
        int top = result.top;
        StringBuilder sb = new StringBuilder();
        sb.append(
                anchor(
                        "Go to HOME",
                        null,
                        "/",
                        new Element("<button style=\"margin:2px;\">" + "HOME" + "</button>")
                        //new Element("HOME")
                )
        );
        if (skip > 0) {
            sb.append(
                    anchor(
                            "Go to movies",
                            null,
                            "/movies?skip=" + (skip - LIMIT) + "&top=5",
                            new Element("<button style=\"margin:2px;\">" + "PREV" + "</button>")
                    )
            );
        }
        if (top > 0 && skip + top < sizeMovies) {
            sb.append(
                    anchor(
                            "Go to movies",
                            null,
                            "/movies?skip=" + (skip + LIMIT) + "&top=5",
                            new Element("<button style=\"margin:2px;\">" + "NEXT" + "</button>")
                    )
            );
        }
        return new Element(sb.toString());
    }

    private Element getHtmlByModel(Movies movies) {
        //HEADERS = {"Movie ID", "Title", "Release Year"};
        return new Element(
                (movies.isEmpty()
                        ?
                        p("   - No movies!!!!!")
                        :
                        table(
                            tr(
                                th(HEADERS[0])
                                +th(HEADERS[1])
                                +th(HEADERS[2])
                            )
                            + trTdMovies(movies)
                        )
                )
                + getForm()// o form ja vem com titulo
        );
    }

    private String trTdMovies(Movies movies) {
        StringBuilder sb = new StringBuilder();
        for (var movie : movies.listMovies) {
            Movie mov = (Movie) movie;
            sb.append(
                tr(
                    td(
                        anchor(null,
                                null,
                                "movies/" + mov.id,
                                new Element(mov.id + "")
                        )
                    )
                    + td(mov.title)
                    + td(mov.releaseYear + "")
                )
            );
        }
        return sb.toString();
    }

    private Element getForm() {
        LinkedList<Form.InputForm> listInForm = new LinkedList<>(Arrays.asList(
                new Form.InputForm("Title: ", Keys.TITLE, CONSTRAINTS_INPUT_FORM_TT),
                new Form.InputForm("Release Year: ", Keys.RELEASE_YEAR, CONSTRAINTS_INPUT_FORM_RY)
        ));
        return new Form("Add a new Movie", listInForm).getElementForm();
    }
}
