package pt.isel.ls.http.redirect;

import pt.isel.ls.model.Result;

import java.util.HashMap;

public class RedirectMovieIdRatings implements Redirect {

    @Override
    public String getRedirectUri(String path, Result results) {
        return path;
    }
}
