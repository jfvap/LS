package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.model.Movies;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultGetMovies;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.Errors;
import pt.isel.ls.model.Movie;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

// GET /movies
// GET /movies skip=3&top=2
public class GetMovies implements CommandHandler {

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest) throws SQLException {
        Statement st = con.createStatement();
        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            ResultGetMovies commandResult = new ResultGetMovies();
            int skip;
            int top;
            Movies movies = new Movies();
            String query = "";
            try {
                skip = (int) commandRequest.getParam(Keys.SKIP);
                top = (int) commandRequest.getParam(Keys.TOP);
                commandResult.skip = skip;
                commandResult.top = top;
                query = " LIMIT " + top + " OFFSET " + skip;
            } catch (NullPointerException e) {
                //NOTHING TODO
            }

            ResultSet rsCount = st.executeQuery("SELECT COUNT(id) FROM movies");
            if (rsCount.next()) {
                commandResult.setNumberOfMovies(rsCount.getInt(1));
            }

            ResultSet rs = st.executeQuery("SELECT id, title, releaseYear FROM movies ORDER BY id" + query);
            while (rs.next()) {
                movies.listMovies.add(
                        new Movie(
                                rs.getInt(Keys.ID),
                                rs.getString(Keys.TITLE),
                                rs.getInt(Keys.RELEASE_YEAR)
                        )
                );
            }
            commandResult.addResult(movies);
            if (movies.isEmpty()) {
                commandResult.setMessage("NO MOVIES. ADD A MOVIE DOWN!!!!!");
            }
            return commandResult;
        }
        return commandResultError;
    }

    @Override
    public ResultError isValid(CommandRequest commandRequest, Connection con) {
        ResultError commandResultError = new ResultError();
        Validator validator = new Validator();
        LinkedList<String> errs = new LinkedList<>();
        if (commandRequest.getParametersInstance() != null) {
            Integer skip;
            if ((skip = validator.isValidInt(commandRequest.getParam(Keys.SKIP))) != null) {
                //errs.add("INVALID SKIP");
            //} else {
                commandRequest.setParam(Keys.SKIP, skip);
                if (!validator.isPositiveIntAndZero(skip)) {
                    errs.add("SKIP HAS TO BE EQUAL OR BIGGER THAN ZERO");
                }
            }
            Integer top;
            if ((top = validator.isValidInt(commandRequest.getParam(Keys.TOP))) != null) {
                //errs.add("INVALID TOP");
            //} else {
                if (!validator.isPositiveInt(top)) {
                    errs.add("TOP HAS TO BE BIGGER THAN ZERO");
                }
                commandRequest.setParam(Keys.TOP, top);
            }


        }
        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }

    @Override
    public String toString() {
        return "GetMovies";
    }
}
