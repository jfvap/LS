package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.Transact;
import pt.isel.ls.exception.InternalServerSqlQueryException;
import pt.isel.ls.model.Users;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultGetUsers;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// GET /users accept:text/html
// GET /users
public class GetUsers implements CommandHandler {

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, InternalServerSqlQueryException {

        return new Transact().transaction((a,b) -> result(a,b), con, commandRequest);
        /*
        Statement st = con.createStatement();

        Users users = new Users();
        ResultSet rs = st.executeQuery("SELECT id, name, email FROM users");
        while (rs.next()) {
            users.listUsers.add(new User(rs.getInt(Keys.ID), rs.getString(Keys.NAME), rs.getString(Keys.EMAIL)));
        }
        st.close();

        ResultGetUsers commandResult = new ResultGetUsers();
        commandResult.addResult(users);
        if (commandResult.isEmpty()) {
            commandResult.setMessage("NO USERS FOUND");
        }

        return commandResult;*/
    }

    public CommandResult result(Connection con, CommandRequest commandRequest) throws SQLException {
        Statement st = con.createStatement();

        Users users = new Users();
        ResultSet rs = st.executeQuery("SELECT id, name, email FROM users");
        while (rs.next()) {
            users.listUsers.add(new User(rs.getInt(Keys.ID), rs.getString(Keys.NAME), rs.getString(Keys.EMAIL)));
        }
        st.close();

        ResultGetUsers commandResult = new ResultGetUsers();
        commandResult.addResult(users);
        if (commandResult.isEmpty()) {
            commandResult.setMessage("NO USERS FOUND");
        }

        return commandResult;
    }

    @Override
    public ResultError isValid(CommandRequest commandRequest, Connection con) {
        return null;
    }

    @Override
    public String toString() {
        return "GetUsers";
    }
}
