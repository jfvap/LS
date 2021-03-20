package pt.isel.ls.handlers;

import pt.isel.ls.commandrequest.CommandRequest;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.exception.ForeignkeyException;
import pt.isel.ls.exception.IllegalPathParametersException;
import pt.isel.ls.exception.InternalServerSqlQueryException;
import pt.isel.ls.exception.ResourceNotFoundException;
import pt.isel.ls.resulthandlers.ResultError;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface CommandHandler {
    /**
     * @param commandRequest CommandRequest tem o conteudo necessario para execucao do Handler
     * @return retorna CommandResult com os resultados ( erros se houve inclusive)
     */
    CommandResult execute(Connection con, CommandRequest commandRequest)
            throws SQLException, IOException, InternalServerSqlQueryException, ResourceNotFoundException, ForeignkeyException, IllegalPathParametersException;

    /**
     * @param commandRequest Verifica a validade do conteúdo de CommandRequest
     * @return retorna CommandResult com os resultados ( erros se houve inclusive)
     */
    ResultError isValid(CommandRequest commandRequest, Connection con) throws SQLException;

    String toString();
}
