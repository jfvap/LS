package pt.isel.ls.http.redirect;

import pt.isel.ls.model.Result;

public interface Redirect {
    /**
     * @param paramsMap This map is useful for adding the parameters to the URI that we will be redirecting to.
     * @return returns a String corresponding to the URI with the correct parameters.
     */
    /**
     * @param path for adding the parameters to the URI that we will be redirecting to
     * @param results results related to a POST, which we will add to the path.
     * @return returns a String corresponding to the URI with the correct parameters.
     */
    String getRedirectUri(String path, Result results);
}
