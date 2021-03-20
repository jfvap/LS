package pt.isel.ls.view.html;

import pt.isel.ls.Keys;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;
import pt.isel.ls.html.Form;
import pt.isel.ls.html.Html;
import pt.isel.ls.model.NumberRatingsByValue;
import pt.isel.ls.resulthandlers.ResultGetRatingsByMovieId;

import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import static pt.isel.ls.html.Html.*;
import static pt.isel.ls.html.TableHtml.*;
import static pt.isel.ls.html.TableHtml.td;

public class GetRatingsByMovieIdHtml extends OverviewHtml {
    private static final String[] HEADERS = {"Rating", "Number Of Ratings"};
    private static final String SECTION = "Ratings by Movie Id";
    private static final String TITLE = "Ratings";
    private static final String CONSTRAINTS_INPUT_FORM =
            "type=\"number\" required=\"true\"  min=\"1\" max=\"5\"";

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        ResultGetRatingsByMovieId result = (ResultGetRatingsByMovieId) commandResult;
        NumberRatingsByValue ratings = (NumberRatingsByValue) result.getResults();
        Element headers = getHtmlHeaders(ratings);
        Element results = getHtmlByModel(ratings);
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

    private Element getHtmlHeaders(NumberRatingsByValue ratings) {
        return new Element(
                anchor(
                        "Go to HOME",
                        null,
                        "/",
                        new Element("HOME")
                )
        );
    }

    private Element getHtmlByModel(NumberRatingsByValue ratings) {
        //HEADERS =  {"Rating", "Number Of Ratings"};
        return new Element(
                getAvg(ratings)
                + table(
                        tr(
                            th(HEADERS[0])
                             +th(HEADERS[1])
                        )
                        + trTdTopRats(ratings)
                )
                + getForm()// o form ja vem com titulo
        );
    }

    private String getAvg(NumberRatingsByValue ratings) {
        return
                Html.h2(
                        "Movie: "
                                + anchor(
                                    null,
                                    null,
                                    "/movies/" + ratings.movie.id,
                                    new Element(ratings.movie.title)
                                )
                )
                + Html.h2(
                        "Average = "
                        + getSpan(
                                String.format("%.2f", ratings.average()), getColor(ratings.average())
                        )
                );
    }

    private String trTdTopRats(NumberRatingsByValue ratings) {
        StringBuilder sb = new StringBuilder();
        HashMap<Integer, Integer> map = ratings.mapVotes;
        for (int i = 1; i <= 5; i++) {
            sb.append(
                    tr(
                        td(i + "", getColor(i))
                        + td(map.get(i) + "")
                    )
            );
        }
        return sb.toString();
    }

    private Element getForm() {
        LinkedList<Form.InputForm> listInForm = new LinkedList<>(Collections.singletonList(
                new Form.InputForm("Vote: ", Keys.RATING, CONSTRAINTS_INPUT_FORM)
        ));
        return new Form("Add a new Rating", listInForm).getElementForm();
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