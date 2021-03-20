package pt.isel.ls.http.redirect;

import pt.isel.ls.model.CompleteReview;
import pt.isel.ls.model.Result;

public class RedirectReviewsByMovieId implements Redirect {

    @Override
    public String getRedirectUri(String path, Result results) {
        return path + '/' + ((CompleteReview)results).review.id;
    }
}
