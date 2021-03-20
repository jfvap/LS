package pt.isel.ls;

import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.exception.InternalServerSqlQueryException;

import java.sql.Connection;
import java.sql.SQLException;

public class Transact {

    public CommandResult transaction(Transaction transact, Connection con, CommandRequest commandRequest)
            throws SQLException, InternalServerSqlQueryException {
        CommandResult commandResult;
        try {
            con.setAutoCommit(false);
            commandResult = transact.trans(con, commandRequest);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            //e.printStackTrace();
            throw new InternalServerSqlQueryException("Error in DATABASE");
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return commandResult;
    }
}
