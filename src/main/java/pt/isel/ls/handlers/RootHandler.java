package pt.isel.ls.handlers;

import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.resulthandlers.ResultError;
import pt.isel.ls.resulthandlers.RootResult;

import java.sql.Connection;

public class RootHandler implements CommandHandler {
    @Override
    public CommandResult execute(Connection con, CommandRequest commandRequest) {
        return new RootResult();
    }

    @Override
    public ResultError isValid(CommandRequest commandRequest, Connection con) {
        return null;
    }
}
