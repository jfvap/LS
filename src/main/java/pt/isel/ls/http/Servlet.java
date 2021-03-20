package pt.isel.ls.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Services;
import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.exception.*;
import pt.isel.ls.http.redirect.ManagerRedirectUri;
import pt.isel.ls.http.redirect.Redirect;
import pt.isel.ls.input.Method;
import pt.isel.ls.input.Path;
import pt.isel.ls.input.Router;
import pt.isel.ls.input.ViewsRouter;
import pt.isel.ls.input.TypePrint;
import pt.isel.ls.view.View;
import pt.isel.ls.view.html.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.Map;

public class Servlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(Servlet.class);
    private final ViewsRouter viewsRouter;
    private final Services services;
    private static Router router;

    public Servlet(Router router, ViewsRouter viewsRouter) {
        this.viewsRouter = viewsRouter;
        Servlet.router = router;
        this.services = new Services(router);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("incoming request: method={}, uri={}, header={} , parameters={}",
                req.getMethod(),
                req.getRequestURI(),
                req.getQueryString(),
                req.getParameterMap().toString());

        executeGet(req, resp);

        log.info("outgoing response: method={}, uri={}, status={}, Content-Type={}",
                req.getMethod(),
                req.getRequestURI(),
                resp.getStatus(),
                resp.getHeader("Content-Type"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("incoming request: method={}, uri={}, header={} , parameters={}",
                req.getMethod(),
                req.getRequestURI(),
                req.getQueryString(),
                req.getParameterMap().toString());

        executePost(req, resp);

        log.info("outgoing response: method={}, uri={}, status={}, Content-Type={}",
                req.getMethod(),
                req.getRequestURI(),
                resp.getStatus(),
                resp.getHeader("Content-Type"));
    }

    private void executeGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Path path = new Path(req.getRequestURI());
        String queryString = req.getQueryString();
        InfoHttp info = new InfoHttp(StatusCode.Ok);;
        int statusCode;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = getWriter(byteArrayOutputStream);
        CommandResult commandResult = null;

        try {
            String[] args = {Method.GET.name(), path.currPath, queryString};
            commandResult = services.run(args);

            if (commandResult == null) {
                throw new InternalServerSqlQueryException();
            } else if (commandResult.isError()) {
                throw new IllegalParametersException();
            }
            printResult(commandResult, writer);

        } catch (NoCommandException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.NotFound);
            printError(writer, info);
        } catch (IllegalParametersException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.BadRequest);
            printParametersError(writer, info, commandResult);
        } catch (IllegalPathParametersException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.BadRequest);
            printError(writer, info);
        } catch (ForeignkeyException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.NotFound);
            printError(writer, info);
        } catch (ResourceNotFoundException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.NotFound);
            printError(writer, info);
        } catch (InternalServerSqlQueryException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.InternalServerError);
            printError(writer, info);
        } catch (SQLException e) {
            info = new InfoHttp(e.getMessage(), StatusCode.InternalServerError);
            printError(writer, info);
        } finally {
            statusCode = info.statusCode.getStatus();
            sendRespData(resp, byteArrayOutputStream.toByteArray(), statusCode);
        }
    }

    private void executePost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Path path = new Path(req.getRequestURI());
        CommandResult commandResult = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = getWriter(byteArrayOutputStream);
        int statusCode;
        InfoHttp info = new InfoHttp(StatusCode.Ok);
        try {
            Map<String, String[]> map = req.getParameterMap();
            String queryString = getFormatQueryString(map);
            String[] args = {Method.POST.name(), path.currPath ,queryString};
            commandResult = services.run(args);

            if (commandResult == null) {
                throw new InternalServerSqlQueryException();
            } else if (commandResult.isError()) {
                throw new IllegalParametersException();
            }

            String redUri = getUrl(commandResult, path);
            resp.sendRedirect(redUri);

        } catch (NoCommandException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.NotFound);
            printError(writer, info);
        } catch (IllegalParametersException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.BadRequest);
            printParametersError(writer, info, commandResult);
        } catch (IllegalPathParametersException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.BadRequest);
            printError(writer, info);
        } catch (IllegalArgumentException e) {
            info = new InfoHttp(e.getMessage(), StatusCode.BadRequest);
            printError(writer, info);
        } catch (ResourceNotFoundException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.NotFound);
            printError(writer, info);
        } catch (ForeignkeyException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.NotFound);
            printError(writer, info);
        } catch (InternalServerSqlQueryException e) {
            info = new InfoHttp(e.getMsg(), StatusCode.InternalServerError);
            printError(writer, info);
            //sendRespData(resp, e.getMsg().getBytes(), StatusCode.InternalServerError.getStatus());
        } catch (SQLException e) {
            info = new InfoHttp(e.getMessage(), StatusCode.InternalServerError);
            printError(writer, info);
            //sendRespData(resp, e.getMessage().getBytes(), StatusCode.InternalServerError.getStatus());
        } finally {
            statusCode = info.statusCode.getStatus();
            sendRespData(resp, byteArrayOutputStream.toByteArray(), statusCode);
        }
    }

    /**
     * Sets the data (respBodyBytes) , the status code, the content Type (plain/html) the type of codification (UTF-8)
     * and writes it to an OutputStream
     */
    private void sendRespData(HttpServletResponse resp, byte[] respBodyBytes, int statusCode) throws IOException {
        Charset utf8 = StandardCharsets.UTF_8;
        resp.setContentType(String.format("text/html; charset=%s", utf8.name()));
        resp.setStatus(statusCode);
        OutputStream os = resp.getOutputStream();
        os.write(respBodyBytes);
        os.flush();
    }

    private void printResult(CommandResult commandResult, OutputStreamWriter writer) {
        View view = viewsRouter.findView(commandResult, TypePrint.HTML);
        view.printResult(commandResult, writer);
    }

    /**
     * Returns the complete redirect Url (with parameters if needed) assigned to the current Path from
     * where we got the POST request
     */
    private String getUrl(CommandResult commandResult, Path path) throws NoCommandException {
        String pathTemplate = router.findPathTemplate(Method.POST, path);//todo
        Redirect r = new ManagerRedirectUri().getRedirect(pathTemplate);
        return r.getRedirectUri(path.currPath, commandResult.getResults());
    }

    /**
     * Returns a new writer for writing the bytes of response from get/post requests
     */

    public OutputStreamWriter getWriter(ByteArrayOutputStream byteArrayOutputStream) {
        return new OutputStreamWriter(byteArrayOutputStream);
    }

    /**
     * Gets formated query string with the needed parameters from 'map'
     */
    private String getFormatQueryString(Map<String, String[]> map) {
        StringBuilder qs = new StringBuilder();
        for (String s : map.keySet()) {
            String str = map.get(s)[0].replace(" ", "+");
            qs.append(s).append('=').append(str).append('&');
        }
        if (qs.length() > 0) {
            qs = new StringBuilder(qs.substring(0, qs.length() - 1));
        }
        return qs.toString();
    }

    /**
     * Calls printResult() method of respective html view instance
     */
    private void printParametersError(OutputStreamWriter writer, InfoHttp info, CommandResult commandResult) {
        ErrorParametersHtml view = new ErrorParametersHtml();
        view.printResult(writer, info, commandResult);
    }

    /**
     * Calls printResult() method of respective html view instance
     */
    private void printError(OutputStreamWriter writer, InfoHttp info) {
        ErrorView view = new ErrorView();
        view.printResult(writer, info);
    }
}
