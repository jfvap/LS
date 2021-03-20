package pt.isel.ls.http.redirect;

import pt.isel.ls.model.Movie;
import pt.isel.ls.model.Result;

public class RedirectMoviesById implements Redirect {

    @Override
    public String getRedirectUri(String path, Result results) {
        return path + '/' + ((Movie) results).id;
    }
}
