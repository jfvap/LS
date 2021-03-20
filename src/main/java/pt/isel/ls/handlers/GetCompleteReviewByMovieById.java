package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.exception.ResourceNotFoundException;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultGetCompleteReviewByMovieById;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.*;
import pt.isel.ls.model.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

// GET /movies/1/reviews/1
public class GetCompleteReviewByMovieById implements CommandHandler {

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, ResourceNotFoundException, IllegalPathParametersException {

        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            int mid = (int) commandRequest.getPathParam(Keys.MID);
            int rid = (int) commandRequest.getPathParam(Keys.RID);
            PreparedStatement ps = con.prepareStatement("SELECT R.id as rid, R.summary, R.review, R.rating,"
                    + " U.id as uid,"
                    + " U.name as name, M.title as title"
                    + " FROM movies as M"
                    + " INNER JOIN reviews as R"
                    + " ON M.id = R.mid "
                    + " INNER JOIN users as U"
                    + " ON U.id = R.uid "
                    + " WHERE M.id = ? AND R.id = ?");

            ps.setInt(1, mid);
            ps.setInt(2, rid);

            ResultSet rs = ps.executeQuery();
            ResultGetCompleteReviewByMovieById commandResult = new ResultGetCompleteReviewByMovieById();

            if (rs.next()) {
                commandResult.addResult(
                        new CompleteReview(
                                new Movie(mid, rs.getString("title")),
                                new User(rs.getInt("uid"), rs.getString("name")),
                                new Review(
                                        rid,
                                        rs.getString("summary"),
                                        rs.getString("review"),
                                        rs.getInt("rating"))
                        )
                );
            } else {
                throw new ResourceNotFoundException("NO REVIEW FOUND WITH THE ID " + rid + " FROM MOVIE " + mid);
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
        } else {
            commandRequest.setPathParam(Keys.MID, mid);
        }
        Integer rid;
        if ((rid = validator.isValidInt(commandRequest.getPathParam(Keys.RID))) == null) {
            errs.add("THE REVIEW IDENTIFIER SHOULD BE AN INTEGER");
        } else {
            commandRequest.setPathParam(Keys.RID, rid);
        }*/
        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }

    @Override
    public String toString() {
        return "GetCompleteReviewByMovieById";
    }
}
