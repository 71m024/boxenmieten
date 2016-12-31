/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.drshit.web.exception;

import ch.drshit.web.beans.UnauthorizedBean;
import ch.drshit.web.security.ShiroLoginBean;

import java.io.IOException;
import java.net.URLEncoder;
import javax.enterprise.inject.spi.CDI;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.lifecycle.Lifecycle;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author timo
 */
public class ExceptionUtil {
    
    public static void notFound(String message) throws IOException {
        sendError(HttpServletResponse.SC_NOT_FOUND, message);
    }

    public static void forbidden(String message) throws IOException {
        sendError(HttpServletResponse.SC_FORBIDDEN, message);
    }

    public static void unauthorized(String message) throws IOException {
        UnauthorizedBean loginBean = CDI.current().select(UnauthorizedBean.class).get();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String originalUri = (String) externalContext.getRequestMap().get(RequestDispatcher.FORWARD_REQUEST_URI);
        loginBean.setRedirectUrl(URLEncoder.encode(originalUri, "UTF-8"));
        sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }

    private static void sendError(int code, String message) throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().responseSendError(code, message);
        context.responseComplete();
    }
    
    public static <T> T getCauseOfInstance(Throwable t, Class<T> clazz) {
        if (t == null) {
            return null;
        }
        if (clazz.isInstance(t)) {
            return (T)t;
        }
        return getCauseOfInstance(t.getCause(), clazz);
    }
}
