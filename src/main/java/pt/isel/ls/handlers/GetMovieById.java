package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.exception.ResourceNotFoundException;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultGetMovieById;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.*;
import pt.isel.ls.model.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

// GET /movies/1
public class GetMovieById implements CommandHandler {

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, ResourceNotFoundException, IllegalPathParametersException {

        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            int mid = (int) commandRequest.getPathParam(Keys.MID);
            PreparedStatement ps = con.prepareStatement("SELECT R.id as rid, title, releaseYear, U.id as uid, name,"
                    + " summary, rating"
                    + " FROM reviews as R"
                    + " FULL JOIN  users as U"
                    + " ON U.id = R.uid"
                    + " FULL JOIN  movies as M"
                    + " ON R.mid = M.id"
                    + " WHERE M.id = ?"
            );
            ps.setInt(1, mid);
            ResultSet rs = ps.executeQuery();
            ResultGetMovieById commandResult = new ResultGetMovieById();

            MovieReviews movieReviews;
            if (rs.next()) {
                commandResult.addResult(
                        movieReviews = new MovieReviews(
                                new Movie(
                                        mid,
                                        rs.getString("title"),
                                        rs.getInt("releaseYear")
                                )
                        )
                );
                if (rs.getInt("rid") > 0) {
                    do {
                        movieReviews.addReview(
                                new ReviewUser(
                                        new Review(
                                                rs.getInt("rid"),
                                                rs.getString("summary"),
                                                rs.getInt("rating")
                                        ),
                                        new User(
                                                rs.getInt("uid"),
                                                rs.getString("name")
                                        )
                                )
                        );
                    } while (rs.next());
                }
            } else {
                throw new ResourceNotFoundException("NO MOVIE FOUND WITH THE ID " + mid);
            }
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
        }

        commandRequest.setPathParam(Keys.MID, mid);*/
        Errors errors = new Errors(errs);
        commandResultError.addResult(errors);
        return commandResultError;
    }

    @Override
    public String toString() {
        return "GetMovieById";
    }
}