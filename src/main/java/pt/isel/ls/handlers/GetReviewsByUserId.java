package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.exception.ResourceNotFoundException;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultGetReviewsByUserId;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.*;
import pt.isel.ls.model.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

//GET /users/1/reviews
public class GetReviewsByUserId implements CommandHandler {
    // private static final String USER_PARAM = "uid";

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, ResourceNotFoundException, IllegalPathParametersException {
        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            int uid = (int) commandRequest.getPathParam(Keys.UID);
            PreparedStatement ps = con.prepareStatement("SELECT R.id as rid, R.mid as mid, title, summary, rating,"
                    + " name, email"
                    + " FROM reviews as R"
                    + " FULL JOIN users as U"
                    + " ON U.id = R.uid"
                    + " FULL JOIN movies as M"
                    + " ON R.mid = M.id"
                    + " WHERE U.id = ?");
            ps.setInt(1, uid);
            UserReviews user;

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserReviews(
                        new User(uid, rs.getString("name"))
                );
                if (rs.getInt("rid") > 0) {
                    do {
                        user.addReview(
                            new ReviewMovie(
                                new Review(
                                    rs.getInt("rid"),
                                    rs.getString("summary"),
                                    rs.getInt("rating")
                                ),
                                new Movie(
                                    rs.getInt("mid"),
                                    rs.getString("title")
                                )
                            )
                        );
                    } while (rs.next());
                }
            } else {
                throw new ResourceNotFoundException("NO REVIEWS FOUND FOR USER WITH THE ID " + uid);
            }

            ResultGetReviewsByUserId commandResult = new ResultGetReviewsByUserId();
            commandResult.addResult(user);
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
        Integer uid;
        if ((uid = validator.isValidInt(commandRequest.getPathParam(Keys.UID))) == null) {
            errs.add("THE USER IDENTIFIER SHOULD BE AN INTEGER");
        } else {
            commandRequest.setPathParam(Keys.UID, uid);
        }
         */

        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }

    @Override
    public String toString() {
        return "GetReviewsByUserId";
    }
}