package pt.isel.ls;

import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.exception.*;
import pt.isel.ls.handlers.CommandHandler;
import pt.isel.ls.input.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Services {
    private static final String ENVIRONMENT_VARIABLE = "JDBC_DATABASE_URL";
    private static final int METHOD_IDX = 0;
    private static final int PATH_IDX = 1;
    private final Router router;
    //ConnectionDataBase conDataBase = new ConnectionDataBase(ENVIRONMENT_VARIABLE);
    PGSimpleDataSource ds;


    public Services(Router router) {
        this.router = router;
        String url =  System.getenv(ENVIRONMENT_VARIABLE);
        this.ds = new PGSimpleDataSource();
        this.ds.setURL(url);
    }

    public CommandResult run(String[] args)
            throws NoCommandException, InternalServerSqlQueryException, SQLException,
            ResourceNotFoundException, ForeignkeyException, IOException, IllegalParametersException, IllegalPathParametersException {

        Path path;
        Method method = Method.valueOf(args[METHOD_IDX].toUpperCase());
        Parameters parameters = new Parameters();
        parameters.setParameters(args);
        try {
            path = new Path(args[PATH_IDX]);
        } catch (Exception e) {
            path = new Path("");
        }

        CommandHandler commandHandler = router.findHandler(method, path);
        CommandRequest commandRequest = new CommandRequest(path, parameters);
        return executeCommand(commandHandler, commandRequest);
    }

    /**
     * Executes command making sure that if an exception is thrown it rollbacks.
     */
    public CommandResult executeCommand(CommandHandler commandHandler, CommandRequest commandRequest)
            throws SQLException, ResourceNotFoundException, IOException,
            InternalServerSqlQueryException, ForeignkeyException, IllegalPathParametersException {

        return commandHandler.execute(ds.getConnection(), commandRequest);
        /* Connection con = null;
        CommandResult commandResult;
        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            commandResult = commandHandler.execute(con, commandRequest);
            con.commit();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            //e.printStackTrace();
            throw new InternalServerSqlQueryException("Error in DATABASE");
        } finally {
            if (con != null) {
                con.close();
            }
        }
        return commandResult;*/
    }

}