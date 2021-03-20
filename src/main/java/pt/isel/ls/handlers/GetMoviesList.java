package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.model.MoviesList;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultGetMoviesList;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.Errors;
import pt.isel.ls.model.Movie;
import pt.isel.ls.model.TopMovie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

// GET /tops/ratings n=1&average=lowest&min=1
// GET /tops/ratings n=3&average=highest&min=1
public class GetMoviesList implements CommandHandler {

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest) throws SQLException {
        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {

            PreparedStatement ps = con.prepareStatement("SELECT id, title, releaseYear,"
                    + " ((rating1*1.0 + rating2*2.0 + rating3*3.0 + rating4*4.0 + rating5*5.0)"
                    + "/(rating1 + rating2 + rating3 + rating4 + rating5))::NUMERIC(10, 2) AS avg"
                    + " FROM MOVIES"
                    + " WHERE (rating1 + rating2 + rating3 + rating4 + rating5) >= ?"
                    + " GROUP BY (id, title, releaseYear)"
                    + " ORDER BY avg "
                    + (commandRequest.getParam(Keys.AVG).equals("highest") ? "DESC" : "ASC")
                    + " LIMIT ?"); // (n)
            int min = (int) commandRequest.getParam(Keys.NUMBER_MIN_VOTES);
            int n = (int) commandRequest.getParam(Keys.TOP_S);
            ps.setInt(1, min);
            ps.setInt(2, n);

            ResultGetMoviesList commandResult = new ResultGetMoviesList();
            MoviesList moviesList = new MoviesList();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                moviesList.topMoviesList.add(
                    new TopMovie(
                        new Movie(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3)
                        ),
                        rs.getDouble(4)
                    )
                );
            }
            ps.close();
            rs.close();
            commandResult.addResult(moviesList);
            if (moviesList.topMoviesList.isEmpty()) {
                commandResult.setMessage("NO MOVIE FOUND WITH THOSE PARAMETERS");
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
        Integer n;
        if ((n = validator.isValidInt(commandRequest.getParam(Keys.TOP_S))) == null) {
            errs.add("THE " + Keys.TOP_S + " MUST BE INTEGER!");
        } else {
            commandRequest.setParam(Keys.TOP_S, n);
        }

        Integer min;
        if ((min = validator.isValidInt(commandRequest.getParam(Keys.NUMBER_MIN_VOTES))) == null) {
            errs.add("THE " + Keys.NUMBER_MIN_VOTES + " MUST BE INTEGER!");
        } else {
            commandRequest.setParam(Keys.NUMBER_MIN_VOTES, min);
        }

        String avg;
        if ((avg = (String) commandRequest.getParam(Keys.AVG)) == null
                || !avg.equals(Keys.HIGHEST) && !avg.equals(Keys.LOWEST)) {
            errs
                    .add("THE " + Keys.AVG + " MUST BE " + Keys.HIGHEST + " OR " + Keys.LOWEST);
        }
        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }

    @Override
    public String toString() {
        return "GetMoviesList";
    }
}