package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.ForeignkeyException;
import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.exception.InternalServerSqlQueryException;
import pt.isel.ls.model.*;
import pt.isel.ls.model.Errors;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultPostReviewByMovieId;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

// POST /movies/1/reviews uid=1&reviewSummary=filme+assustador&review=este+filme+é+assustador&rating=4
public class PostReviewByMovieId implements CommandHandler {
    // private static final String MOVIE_PARAM = "mid";
    // private static final String USER_PARAM = "uid";
    // private static final String REVIEW_SUMMARY = "reviewSummary";
    // private static final String REVIEW = "review";
    // private static final String RATING = "rating";

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, InternalServerSqlQueryException, ForeignkeyException, IllegalPathParametersException {

        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            int mid = (int) commandRequest.getPathParam(Keys.MID);
            int uid = (int) commandRequest.getParam(Keys.UID);

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE id = ?");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                ps.close();
                throw new ForeignkeyException("THE USER WITH IDENTIFIER " + uid + " DOES NOT EXIST!!!");
            }
            ps = con.prepareStatement("INSERT INTO "
                    + "reviews (mid, uid, summary, review, rating) "
                    + "VALUES (?, ?, ?, ?, ?);",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            ps.setInt(1, mid);
            ps.setInt(2, uid);
            ps.setString(3, (String) commandRequest.getParam(Keys.REVIEW_SUMMARY));
            ps.setString(4, (String) commandRequest.getParam(Keys.REVIEW));
            ps.setInt(5, (int) commandRequest.getParam(Keys.RATING));

            ResultPostReviewByMovieId commandResult = new ResultPostReviewByMovieId();
            CompleteReview review;
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int key;
                review = new CompleteReview(
                        new Movie(mid),
                        new User(uid),
                        new Review( key = keys.getInt(1))
                );
                commandResult.setMessage("REVIEW KEY INSERTED" + key);
            } else {
                throw new InternalServerSqlQueryException("REVIEW INSERTION TO MOVIE WITH ID" + mid + " FAILED");
            }
            String ratingN = "rating" + (int) commandRequest.getParam(Keys.RATING);
            ps = con.prepareStatement("UPDATE movies"
                    + " SET " + ratingN + " = " + ratingN + " + 1"
                    + " WHERE id = ?");
            ps.setInt(1, mid);

            if (ps.executeUpdate() <= 0) {
                throw new SQLException();
            }
            ps.close();
            commandResult.addResult(review);
            return commandResult;
        }
        return commandResultError;
    }

    @Override
    public ResultError isValid(CommandRequest commandRequest, Connection con) {
        ResultError commandResultError = new ResultError();
        LinkedList<String> errs = new LinkedList<>();


        Object uid;
        if ((uid = commandRequest.getParam(Keys.UID)) == null) {
            errs.add("PARAMETER " + Keys.UID + " NOT FOUND");
        }
        Object sum;
        if ((sum = commandRequest.getParam(Keys.REVIEW_SUMMARY)) == null) {
            errs.add("PARAMETER " + Keys.REVIEW_SUMMARY + " NOT FOUND");
        }
        Object complete;
        if ((complete = commandRequest.getParam(Keys.REVIEW)) == null) {
            errs.add("PARAMETER " + Keys.REVIEW + " NOT FOUND");
        }
        Object rating; // declaration must be here because of checkstyle
        if ((rating = commandRequest.getParam(Keys.RATING)) == null) {
            errs.add("PARAMETER " + Keys.RATING + " NOT FOUND");
        }

        if (!errs.isEmpty()) {
            commandResultError.addResult(new Errors(errs));
            return commandResultError;
        }

        Validator validator = new Validator();

        Integer userId;
        if ((userId = validator.isValidInt(uid)) == null) {
            errs.add("THE USER IDENTIFIER SHOULD BE AN INTEGER");
        } else {
            commandRequest.setParam(Keys.UID, userId);
        }
/*
        Integer mid;
        if ((mid = validator.isValidInt(commandRequest.getPathParam(Keys.MID))) == null) {
            errs.add("THE MOVIE IDENTIFIER SHOULD BE AN INTEGER");
        } else {
            commandRequest.setPathParam(Keys.MID, mid);
        }*/

        if (!validator.isValidString((String) sum)) {
            errs.add("THE REVIEW SUMMARY IS INVALID");
        }
        if (!validator.isValidSizeSummary((String) sum)) {
            errs.add("THE NUMBER OF REVIEW SUMMARY CHARACTERS IS INVALID");
        }
        if (!validator.isValidString((String) complete)) {
            errs.add("THE REVIEW IS INVALID");
        }
        if (!validator.isValidSizeCompleteReview((String) complete)) {
            errs.add("THE REVIEW SUMMARY IS INVALID");
        }
        Integer rat;
        if ((rat = validator.isValidRating((String) rating)) == null) {
            errs.add("RATING VALUE IS INVALID, MUST BE BETWEEN 0 AND 5");
        } else {
            commandRequest.setParam(Keys.RATING, rat);
        }
        if (complete != null && sum != null && ((String) sum).length() > ((String) complete).length()) {
            errs.add("THE REVIEW MUST BE BIGGER THAN THE REVIEW SUMMARY");
        }
        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }
}