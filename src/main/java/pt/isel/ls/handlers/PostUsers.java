package pt.isel.ls.handlers;

import pt.isel.ls.Keys;
import pt.isel.ls.exception.InternalServerSqlQueryException;
import pt.isel.ls.model.User;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.ResultPostUsers;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.model.Errors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

//POST /users name=joão+pedro&email=pedrojohnny@gmail.com
//POST /users name=dummy&email=dummy@gmail.com
public class PostUsers implements CommandHandler {
    // private static final String NAME = "name";
    // private static final String EMAIL = "email";

    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, InternalServerSqlQueryException {
        ResultError commandResultError = isValid(commandRequest, con);
        if (commandResultError.isEmpty()) {
            ResultPostUsers commandResult = new ResultPostUsers();
            String name = (String) commandRequest.getParam(Keys.NAME);
            String email = (String) commandRequest.getParam(Keys.EMAIL);

            PreparedStatement ps =
                    con.prepareStatement("INSERT INTO users (name, email) VALUES (?, ?);",
                            PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                int key;
                commandResult.addResult(new User(key = keys.getInt(1)));
                commandResult.setMessage("USER KEY INSERTED - " + key);
            } else {
                throw new InternalServerSqlQueryException("USER INSERTION FAILED");

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

        String name;
        if ((name = (String) commandRequest.getParam(Keys.NAME)) == null) {
            errs.add("PARAMETER " + Keys.NAME + " NOT FOUND");
        }
        String email;
        if ((email = (String) commandRequest.getParam(Keys.EMAIL)) == null) {
            errs.add("PARAMETER " + Keys.EMAIL + " NOT FOUND");
        }

        if (!errs.isEmpty()) {
            return commandResultError;
        }

        Validator validator = new Validator();

        if (!validator.nameIsValid(name)) {
            errs.add("NAME IS INVALID");
        }
        if (!validator.isValidSizeName(name)) {
            errs.add("THE NUMBER OF NAME CHARACTERS IS INVALID");
        }
        if (!validator.isValidEmail(email)) {
            errs.add("EMAIL IS INVALID");
        }
        if (!validator.isValidSizeEmail(email)) {
            errs.add("THE NUMBER OF EMAIL CHARACTERS IS INVALID");
        }
        commandResultError.addResult(new Errors(errs));
        return commandResultError;
    }

}


