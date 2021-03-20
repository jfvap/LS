package pt.isel.ls.view.html;

import pt.isel.ls.Keys;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.html.Element;
import pt.isel.ls.html.Form;
import pt.isel.ls.model.User;
import pt.isel.ls.model.Users;
import pt.isel.ls.resulthandlers.ResultGetUsers;

import java.io.Writer;
import java.util.Arrays;
import java.util.LinkedList;

import static pt.isel.ls.html.Html.*;
import static pt.isel.ls.html.TableHtml.*;

public class GetUsersHtml extends OverviewHtml {
    private static final String[] HEADERS = {"User ID", "Name", "Email"};
    private static final String TITLE = "Users list";
    private static final String SECTION = "Users List";
    private static final String CONSTRAINTS_INPUT_FORM =
            "type=\"text\" required=\"true\"  maxlength=\"1\"";
    private static final String CONSTRAINTS_INPUT_FORM_NM =
            "type=\"text\" required=\"true\"  maxlength=\"120\"";
    private static final String CONSTRAINTS_INPUT_FORM_EM =
            "type=\"text\" required=\"true\"  maxlength=\"120\"";

    @Override
    public boolean printResult(CommandResult commandResult, Writer writer) {
        ResultGetUsers result = (ResultGetUsers) commandResult;
        Users users = (Users) result.getResults();
        Element headers = getHtmlHeaders(result);
        Element results = getHtmlByModel(users);
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

    private Element getHtmlHeaders(ResultGetUsers result) {
        return new Element(
                anchor(
                        "Go to HOME",
                        null,
                        "/",
                        new Element("HOME")
                )
        );
    }

    private Element getHtmlByModel(Users users) {
        //HEADERS = {"User ID", "Name", "Email"};
        return new Element(
                (users.isEmpty()
                        ?
                        p("   - No users!!!!!")
                        :
                        table(
                                tr(
                                        th(HEADERS[0])
                                                + th(HEADERS[1])
                                                + th(HEADERS[2])
                                )
                                        + trTdMovies(users)
                        )
                )
                        + getForm()// o form ja vem com titulo
        );
    }

    private String trTdMovies(Users users) {
        StringBuilder sb = new StringBuilder();
        for (var user : users.listUsers) {
            User u = (User) user;
            sb.append(
                    tr(
                            td(
                                    anchor(null,
                                            null,
                                            "/users/" + u.id,
                                            new Element(u.id + "")
                                    )
                            )
                                    + td(u.name)
                                    + td(u.email)
                    )
            );
        }
        return sb.toString();
    }

    private Element getForm() {
        LinkedList<Form.InputForm> listInForm = new LinkedList<>(Arrays.asList(
                new Form.InputForm("Name: ", Keys.NAME, CONSTRAINTS_INPUT_FORM_NM),
                new Form.InputForm("Email: ", Keys.EMAIL, CONSTRAINTS_INPUT_FORM_EM)
        ));
        return new Form("Add a new User", listInForm).getElementForm();
    }
}