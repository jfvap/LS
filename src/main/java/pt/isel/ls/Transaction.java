package pt.isel.ls;

import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;

import java.sql.Connection;
import java.sql.SQLException;

public interface Transaction {
    CommandResult trans(Connection con, CommandRequest req) throws SQLException;
}
