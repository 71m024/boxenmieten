package ch.drshit.web.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by timo on 27.11.16.
 */
@Named
@ApplicationScoped
public class UtilBean {

    public String getRequestUrl() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getRequestURI();
    }

}
