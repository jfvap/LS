package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.exception.ResourceNotFoundException;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultGetCompleteReviewByUserById;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.*;
import pt.isel.ls.model.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

// GET /users/{uid}/reviews/{rid}
public class GetCompleteReviewByUserById implements CommandHandler {

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, ResourceNotFoundException, IllegalPathParametersException {

        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            int rid = (int) commandRequest.getPathParam(Keys.RID);
            int uid = (int) commandRequest.getPathParam(Keys.UID);
            PreparedStatement ps = con.prepareStatement("SELECT R.id as rid, R.summary, R.review, R.rating,"
                    + " U.id as uid, M.id as mid,"
                    + " U.name as name, M.title as title"
                    + " FROM movies as M"
                    + " INNER JOIN reviews as R"
                    + " ON M.id = R.mid "
                    + " INNER JOIN users as U"
                    + " ON U.id = R.uid "
                    + " WHERE R.id = ? AND U.id = ?");

            ps.setInt(1, rid);
            ps.setInt(2, uid);

            ResultSet rs = ps.executeQuery();
            ResultGetCompleteReviewByUserById commandResult = new ResultGetCompleteReviewByUserById();

            if (rs.next()) {
                commandResult.addResult(
                        new CompleteReview(
                                new Movie(rs.getInt("mid"), rs.getString("title")),
                                new User(uid, rs.getString("name")),
                                new Review(
                                        rid,
                                        rs.getString("summary"),
                                        rs.getString("review"),
                                        rs.getInt("rating"))
                        )
                );
            } else {
                throw new ResourceNotFoundException("NO REVIEW FOUND WITH THE ID " + rid
                        + " FROM USER WITH THE ID " + uid);
            }
            return commandResult;
        }
        return commandResultError;
    }

    @Override
    public ResultError isValid(CommandRequest commandRequest, Connection con) {
        ResultError commandResultError = new ResultError();
        LinkedList<String> errs = new LinkedList<>();

        /*Validator validator = new Validator();
        Integer uid;
        Integer rid;
        if ((uid = validator.isValidInt(commandRequest.getPathParam(Keys.UID))) == null) {
            errs.add("THE USER IDENTIFIER MUST BE AN INTEGER");
        } else {
            commandRequest.setPathParam(Keys.UID, uid);
        }
        if ((rid = validator.isValidInt(commandRequest.getPathParam(Keys.RID))) == null) {
            errs.add("THE REVIEW IDENTIFIER MUST BE AN INTEGER");
        } else {
            commandRequest.setPathParam(Keys.RID, rid);
        }*/
        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }

    @Override
    public String toString() {
        return "GetCompleteReviewByUserById";
    }
}
