package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.exception.InternalServerSqlQueryException;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultPostRatingByMovieId;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;

//POST /movies/{1}/ratings rating=5
public class PostRatingByMovieId implements CommandHandler {
    // private static final String RATING = "rating";
    // private static final String MOVIE_PARAM = "mid";

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, InternalServerSqlQueryException, IllegalPathParametersException {

        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            int mid = (int) commandRequest.getPathParam(Keys.MID);
            String ratingN = "rating" + commandRequest.getParam(Keys.RATING);
            PreparedStatement ps = con.prepareStatement("UPDATE movies"
                    + " SET " + ratingN + " = " + ratingN + " + 1"
                    + " WHERE id = ?");
            ps.setInt(1, mid);
            int i = ps.executeUpdate();

            ResultPostRatingByMovieId commandResult = new ResultPostRatingByMovieId();
            if (i > 0) {
                commandResult.setMessage("UPDATE SUCCESSFUL - " + i + " NUMBER ROWS AFFECTED");
            } else {
                throw new InternalServerSqlQueryException("RATING INSERTION FAILED");
            }
            ps.close();
            return commandResult;
        }
        return commandResultError;
    }

    @Override
    public ResultError isValid(CommandRequest commandRequest, Connection con) {
        ResultError commandResultError = new ResultError();
        LinkedList<String> errs = new LinkedList<>();

        Object rating;

        if ((rating = commandRequest.getParam(Keys.RATING)) == null) {
            errs.add("RATING NOT FOUND");
            return commandResultError;
        }

        Validator validator = new Validator();

        Integer rat;
        if ((rat = validator.isValidRating((String) rating)) == null) {
            errs.add("RATING VALUE IS INVALID, MUST BE BETWEEN 0 AND 5");
        } else {
            commandRequest.setPathParam(Keys.RATING, rat);
        }
        /*
        Integer mid;
        if ((mid = validator.isValidInt(commandRequest.getPathParam(Keys.MID))) == null) {
            errs.add("THE MOVIE IDENTIFIER SHOULD BE AN INTEGER");
        }
        commandRequest.setPathParam(Keys.MID, mid);*/
        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }
}
