package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.exception.ResourceNotFoundException;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultGetUserById;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.*;
import pt.isel.ls.model.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;


//GET /users/3
public class GetUserById implements CommandHandler {

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, ResourceNotFoundException, IllegalPathParametersException {

        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            int uid = (int) commandRequest.getPathParam(Keys.UID);
            PreparedStatement ps = con.prepareStatement("SELECT R.id as rid, M.id as mid, M.title, R.summary, R.rating,"
                    + " U.name, U.email"
                    + " FROM users as U"
                    + " LEFT JOIN reviews as R"
                    + " on U.id = R.uid"
                    + " LEFT JOIN movies as M"
                    + " on M.id = R.mid"
                    + " WHERE U.id = ?");
            ps.setInt(1, uid);

            UserReviews user;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new UserReviews(
                    new User(
                        uid,
                        rs.getString("name"),
                        rs.getString("email")
                    )
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
                                    rs.getString("name")
                                )
                            )
                        );
                    } while (rs.next());
                }
            } else {
                throw new ResourceNotFoundException("NO USER FOUND WITH THE ID " + uid);
            }

            ResultGetUserById commandResult = new ResultGetUserById();
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
        }*/

        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }

    @Override
    public String toString() {
        return "GetUserById";
    }
}

