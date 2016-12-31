/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.drshit.web.exception;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 *
 * @author timo
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {
    private static final Logger log = Logger.getLogger(CustomExceptionHandler.class.getCanonicalName());
    private ExceptionHandler wrapped;
 
    CustomExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }
 
    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }
 
    @Override
    public void handle() throws FacesException {
 
        final Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
 
            // get the exception from context
            Throwable t = context.getException();
 
            final FacesContext fc = FacesContext.getCurrentInstance();
            final Map<String, Object> requestMap = fc.getExternalContext().getRequestMap();
            final NavigationHandler nav = fc.getApplication().getNavigationHandler();

            boolean handled = false;

            //here you do what ever you want with exception
            try {

                UnauthorizedException unauthorizedException = ExceptionUtil.getCauseOfInstance(t, UnauthorizedException.class);
                if (unauthorizedException != null) {
                    ExceptionUtil.forbidden(unauthorizedException.getMessage());
                    handled = true;
                }
                ResourceNotFoundException resourceNotFoundException = ExceptionUtil.getCauseOfInstance(t, ResourceNotFoundException.class);
                if (resourceNotFoundException != null) {
                    ExceptionUtil.notFound(resourceNotFoundException.getMessage());
                    handled = true;
                }
                UnauthenticatedException unauthenticatedException = ExceptionUtil.getCauseOfInstance(t, UnauthenticatedException.class);
                if (unauthenticatedException != null) {
                    ExceptionUtil.unauthorized("You are not authenticated. See the server.log for more detailed message...");
                    handled = true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //remove it from queue
                if (handled) {
                    i.remove();
                }
            }
        }
        //parent hanle
        getWrapped().handle();
    }
}
