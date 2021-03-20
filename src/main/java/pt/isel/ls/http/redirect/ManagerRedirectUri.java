package pt.isel.ls.http.redirect;

import java.util.HashMap;

/**
 * Manages the available redirects from forms (POST requests)
 */
public class ManagerRedirectUri {
    HashMap<String, Redirect> mapPostGet;

    public ManagerRedirectUri() {
        mapPostGet = new HashMap<>();
        setMap();
    }

    /**
     * Assigns each URI from a POST, to an object that knows how to reconstruct the corresponding redirect.
     */
    private void setMap() {
        mapPostGet.put("/users", new RedirectUsersById());
        mapPostGet.put("/movies", new RedirectMoviesById());
        mapPostGet.put("/movies/{mid}/ratings", new RedirectMovieIdRatings());
        mapPostGet.put("/movies/{mid}/reviews", new RedirectReviewsByMovieId());
    }

    public Redirect getRedirect(String pathTemplate) {
        return mapPostGet.get(pathTemplate);
    }
}
