package pt.isel.ls.input;

import pt.isel.ls.commandrequest.CommandResult;
import pt.isel.ls.view.*;
import pt.isel.ls.view.html.*;
import pt.isel.ls.view.text.TextView;

import java.util.HashMap;

public class ViewsRouter {
    private HashMap<TypePrint, HashMap<String, View>> mapViews = new HashMap<>();

    public ViewsRouter() {
        this.mapViews = new HashMap<>();
        addAllViews();
    }
    private void addAllViews() {//todo adicionar aqui as novas rotas os suas respectivas views
        addView(TypePrint.HTML, "ResultGetCompleteReviewByUserById",
                new GetCompleteReviewByUserIdHtml());
        addView(TypePrint.HTML, "ResultGetCompleteReviewByMovieById",
                new GetCompleteReviewByMovieIdHtml());
        addView(TypePrint.HTML, "ResultGetMovieById", new GetMovieByIdHtml());
        addView(TypePrint.HTML, "ResultGetMovies", new GetMoviesHtml());
        addView(TypePrint.HTML, "ResultGetMoviesList", new GetMoviesListHtml());
        addView(TypePrint.HTML, "ResultGetRatingsByMovieId", new GetRatingsByMovieIdHtml());
        addView(TypePrint.HTML, "ResultGetReviewsByMovieId", new GetReviewsByMovieIdHtml());
        addView(TypePrint.HTML, "ResultGetReviewsByUserId", new GetReviewsByUserIdHtml());
        addView(TypePrint.HTML, "ResultGetUserById", new GetUserByIdHtml());
        addView(TypePrint.HTML, "ResultGetUsers", new GetUsersHtml());
        addView(TypePrint.HTML, "ResultPostMovies", new TextView());
        addView(TypePrint.HTML, "ResultPostRatingByMovieId", new TextView());
        addView(TypePrint.HTML, "ResultPostReviewByMovieId", new TextView());
        addView(TypePrint.HTML, "ResultPostUsers", new TextView());
        addView(TypePrint.HTML, "ResultDeleteReviewByMovieId", new TextView());
        addView(TypePrint.HTML, "ResultError", new TextView());
        addView(TypePrint.HTML, "RootResult", new RootHtml());

        addView(TypePrint.PLAIN, "ResultGetCompleteReviewByMovieById", new TextView());
        addView(TypePrint.PLAIN, "ResultGetCompleteReviewByUserById", new TextView());
        addView(TypePrint.PLAIN, "ResultGetMovieById", new TextView());
        addView(TypePrint.PLAIN, "ResultGetMovies", new TextView());
        addView(TypePrint.PLAIN, "ResultGetMoviesList", new TextView());
        addView(TypePrint.PLAIN, "ResultGetRatingsByMovieId", new TextView());
        addView(TypePrint.PLAIN, "ResultGetReviewsByMovieId", new TextView());
        addView(TypePrint.PLAIN, "ResultGetReviewsByUserId", new TextView());
        addView(TypePrint.PLAIN, "ResultGetUserById", new TextView());
        addView(TypePrint.PLAIN, "ResultGetUsers", new TextView());
        addView(TypePrint.PLAIN, "ResultPostMovies", new TextView());
        addView(TypePrint.PLAIN, "ResultPostRatingByMovieId", new TextView());
        addView(TypePrint.PLAIN, "ResultPostReviewByMovieId", new TextView());
        addView(TypePrint.PLAIN, "ResultPostUsers", new TextView());
        addView(TypePrint.PLAIN, "ResultDeleteReviewByMovieId", new TextView());
        addView(TypePrint.PLAIN, "ResultError", new TextView());
        addView(TypePrint.PLAIN, "ResultOptions", new TextView());
    }

    private void addView(TypePrint typePrint, String commandResult, View view) {
        if (mapViews.containsKey(typePrint)) {
            HashMap<String, View> map = mapViews.get(typePrint);
            map.put(commandResult, view);
            return;
        }
        HashMap<String, View> map = new HashMap<>();
        map.put(commandResult, view);
        mapViews.put(typePrint, map);
    }

    public View findView(CommandResult commandResult, TypePrint typePrint) {
        String nameCommandResult = commandResult.getClass().getSimpleName();
        if (mapViews.containsKey(typePrint)) {
            return mapViews.get(typePrint).get(nameCommandResult);
        }
        return null;
    }
}
