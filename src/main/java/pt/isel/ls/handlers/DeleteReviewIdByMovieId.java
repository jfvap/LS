package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.exception.InternalServerSqlQueryException;
import pt.isel.ls.exception.ResourceNotFoundException;
import pt.isel.ls.resulthandlers.ResultDeleteReviewByMovieId;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.*;
import pt.isel.ls.model.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

// DELETE /movies/{mid}/review/{rid}
public class DeleteReviewIdByMovieId implements CommandHandler {
    // private static final String MOVIE_PARAM = "mid";
    // private static final String REVIEW_PARAM = "rid";

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, InternalServerSqlQueryException, ResourceNotFoundException, IllegalPathParametersException {

        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            ResultDeleteReviewByMovieId commandResult = new ResultDeleteReviewByMovieId();
            int mid = (int) commandRequest.getPathParam(Keys.MID);
            int rid = (int) commandRequest.getPathParam(Keys.RID);
            int rat;
            PreparedStatement ps = con.prepareStatement("SELECT uid, summary, review, rating"
                    + " FROM reviews"
                    + " WHERE id = ? AND mid = ? ");
            ps.setInt(1, rid);
            ps.setInt(2, mid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rat = rs.getInt("rating");
                    new CompleteReview(
                        new Movie(mid),
                        new User(rs.getInt("uid")),
                        new Review(
                            rid,
                            rs.getString("summary"),
                            rs.getString("review"),
                            rat
                        )
                    );
            } else {
                throw new ResourceNotFoundException("THE REVIEW " + rid + " OF MOVIE " + mid + " NOT EXISTS ");
            }

            ps = con.prepareStatement("DELETE FROM reviews WHERE id = ? AND mid = ? ");
            ps.setInt(1, rid);
            ps.setInt(2, mid);
            String ratingN = "rating" + rat;

            if (ps.executeUpdate() > 0) {
                ps = con.prepareStatement("UPDATE movies"
                        + " SET " + ratingN + " = " + ratingN + " - 1"
                        + " WHERE id = ?");
                ps.setInt(1, mid);
                ps.executeUpdate();
                commandResult.putMessage("DELETED REVIEW" + rid);
            } else {
                throw new InternalServerSqlQueryException("SOMETHING WRONG WITH DE DELETING REVIEW "
                        + rid + " OF MOVIE ID " + mid);
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
}
