package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.exception.ResourceNotFoundException;
import pt.isel.ls.model.Movie;
import pt.isel.ls.model.NumberRatingsByValue;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultGetRatingsByMovieId;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

// GET /movies/1/ratings
public class GetRatingsByMovieId implements CommandHandler {

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest) throws SQLException, ResourceNotFoundException, IllegalPathParametersException {

        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            int mid = (int) commandRequest.getPathParam(Keys.MID);
            PreparedStatement ps = con.prepareStatement("SELECT title, rating1, rating2, rating3, rating4, rating5"
                    + " FROM movies"
                    + " WHERE id = ?");
            ps.setInt(1, mid);

            NumberRatingsByValue ratingsByValue = new NumberRatingsByValue();
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ratingsByValue.movie = new Movie(mid, rs.getString("title"));
                ratingsByValue.add(1, rs.getInt("rating1"));
                ratingsByValue.add(2, rs.getInt("rating2"));
                ratingsByValue.add(3, rs.getInt("rating3"));
                ratingsByValue.add(4, rs.getInt("rating4"));
                ratingsByValue.add(5, rs.getInt("rating5"));
            } else {
                throw new ResourceNotFoundException("THE MOVIE NOT FOUND WITH ID " + mid);
            }
            ps.close();
            rs.close();

            ResultGetRatingsByMovieId commandResult = new ResultGetRatingsByMovieId();
            commandResult.addResult(ratingsByValue);
            return commandResult;
        }
        return commandResultError;
    }

    @Override
    public ResultError isValid(CommandRequest commandRequest, Connection con) {
        ResultError commandResultError = new ResultError();
        LinkedList<String> errs = new LinkedList<>();
/*
        Validator validator = new Validator();
        Integer mid;
        if ((mid = validator.isValidInt(commandRequest.getPathParam(Keys.MID))) == null) {
            errs.add("THE MOVIE IDENTIFIER SHOULD BE AN INTEGER");
        } else {
            commandRequest.setPathParam(Keys.MID, mid);
        }
*/
        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }

    @Override
    public String toString() {
        return "GetRatingsByMovieId";
    }
}