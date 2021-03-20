package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.InternalServerSqlQueryException;
import pt.isel.ls.model.Movie;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultPostMovies;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


//POST /movies title=o+batman231&releaseYear=1998
public class PostMovies implements CommandHandler {
    // private static final String TITLE = "title";
    // private static final String RELEASE_YEAR = "releaseYear";

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, InternalServerSqlQueryException {
        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            String title = (String) commandRequest.getParam(Keys.TITLE);
            int year = (Integer) commandRequest.getParam(Keys.RELEASE_YEAR);

            PreparedStatement ps = con.prepareStatement("INSERT INTO movies (title, releaseYear) VALUES (?, ?);",
                    PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setInt(2, year);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();

            ResultPostMovies commandResult = new ResultPostMovies();
            Movie movie;
            if (keys.next()) {
                int key;
                movie = new Movie(key = keys.getInt(1), title, year);
                commandResult.setMessage("MOVIE KEY INSERTED" + key);
            } else {
                throw new InternalServerSqlQueryException("MOVIE INSERTION FAILED!");
            }
            ps.close();
            commandResult.addResult(movie);
            return commandResult;
        }
        return commandResultError;
    }

    @Override
    public ResultError isValid(CommandRequest commandRequest, Connection con) throws SQLException {
        ResultError commandResultError = new ResultError();
        LinkedList<String> errs = new LinkedList<>();

        String title;
        if ((title = (String) commandRequest.getParam(Keys.TITLE)) == null) {
            errs.add("TITLE NOT FOUND");
        }
        Object year;
        if ((year = commandRequest.getParam(Keys.RELEASE_YEAR)) == null) {
            errs.add("RELEASE_YEAR NOT FOUND");
        }

        if (!errs.isEmpty()) {
            commandResultError.addResult(new Errors(errs));
            return commandResultError;
        }

        Validator validator = new Validator();

        if (!validator.isValidString(title)) {
            errs.add("THE TITLE IS INVALID");
        }
        Integer y;
        if ((y = validator.isValidInt(year)) == null) {
            errs.add("THE RELEASE YEAR IS INVALID");
        } else if (!validator.isValidSizeYear((String) year)) {
            errs.add("THE YEAR MUST HAVE THE FORMAT OF YYYY");
        } else {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM movies WHERE title = ? and releaseYear = ?");
            ps.setString(1, title);
            ps.setInt(2, y);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                errs.add("A MOVIE WITH THAT NAME ALREADY EXISTS IN THAT YEAR");
            } else {
                commandRequest.setParam(Keys.RELEASE_YEAR, y);
            }
        }
        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }
}