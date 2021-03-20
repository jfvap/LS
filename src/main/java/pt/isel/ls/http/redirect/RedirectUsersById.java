package pt.isel.ls.http.redirect;

import pt.isel.ls.model.CompleteReview;
import pt.isel.ls.model.Result;
import pt.isel.ls.model.User;

import java.util.HashMap;

public class RedirectUsersById implements Redirect {

    @Override
    public String getRedirectUri(String path, Result results) {
        return path + '/' + ((User)results).id;
    }
}
