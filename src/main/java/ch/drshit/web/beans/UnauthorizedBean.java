package ch.drshit.web.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by timo on 29.11.16.
 */
@Named
@RequestScoped
public class UnauthorizedBean {

    private String redirectUrl;

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
