package pt.isel.ls.view.html;

import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;
import pt.isel.ls.http.InfoHttp;
import pt.isel.ls.model.Errors;
import pt.isel.ls.resulthandlers.ResultError;

import java.io.Writer;

import static pt.isel.ls.html.TableHtml.*;
import static pt.isel.ls.html.Html.*;

public class ErrorParametersHtml extends OverviewHtml {

    public boolean printResult(Writer writer, InfoHttp infoError, CommandResult result) {
        ResultError resultError = (ResultError) result;
        Errors errors = (Errors) result.getResults();
        Element elems = new Element(
                table(
                        tr(th("Errors Found (" + errors.errors.size() + ")"))
                        + getTrTdErrors(errors)
                )
        );
        Element html =
                html(
                        head(
                                title("Error")
                        ),
                        body(new Element(""),
                                h1("Parameters are invalid!"),
                                elems
                        )
                );
        return printResultHtml(writer, html);

        /*
        LinkedList<Pair> elemButton = new LinkedList<>();
        LinkedList<Element> elems = new LinkedList<>();
        LinkedList<Result> errors = null;// result.getListResult();

        Element html = TableHtml.table(
                new String[]{"Errors Found (" + errors.size() + ")"},
                errors,
                new ErrorFormatHtml());
        elems.add(html);

        Element htmlElement =
                Html.getHtmlElement(
                        "Error",
                        infoError.section,
                        elems,
                        elemButton
                );

        return printResultHtml(writer, htmlElement);
         */
    }

    private String getTrTdErrors(Errors errors) {
        StringBuilder sb = new StringBuilder();
        for (var err : errors.errors) {
            sb.append(
                    tr(
                            td(
                                    err
                            )
                    )
            );
        }
        return sb.toString();
    }

}
