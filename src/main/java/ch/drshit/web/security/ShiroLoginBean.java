package ch.drshit.web.security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.LoggerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.List;
import java.util.logging.Logger;


/**
 * Created by timo on 08.11.16.
 */
@Named
@ViewScoped
public class ShiroLoginBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(ShiroLoginBean.class.getName());

    private String username;

    private String password;

    private Boolean rememberMe = false;

    private String redirectUrl;

    public String actionLogout(String target) {
        SecurityUtils.getSubject().logout();
        return target;
    }

    public boolean isPermitted(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

    public boolean isPermittedOne(List<String> permissions) {
        boolean permitted = false;
        boolean[] permittedArray = SecurityUtils.getSubject().isPermitted(permissions.toArray(new String[0]));
        for (boolean permittedElement : permittedArray) {
            permitted |= permittedElement;
        }
        return permitted;
    }

    /**
     * Try and authenticate the user
     */
    public String actionLogin() throws IOException {
        UsernamePasswordToken token = new UsernamePasswordToken(getUsername(), getPassword(), getRememberMe());
        try {
            SecurityUtils.getSubject().login(token);

            if (redirectUrl != null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect(URLDecoder.decode(redirectUrl, "UTF-8"));
            }
        }
        catch (UnknownAccountException ex) {
            facesError("Unknown account");
            LOG.severe(ex.getMessage());
        }
        catch (IncorrectCredentialsException ex) {
            facesError("Wrong password");
            LOG.severe(ex.getMessage());
        }
        catch (LockedAccountException ex) {
            facesError("Locked account");
            LOG.severe(ex.getMessage());
        }
        catch (AuthenticationException ex) {
            facesError("Unknown error: " + ex.getMessage());
            LOG.severe(ex.getMessage());
        }
        finally {
            token.clear();
        }
        return "/pages/login.xhtml";
    }

    /**
     * Adds a new SEVERITY_ERROR FacesMessage for the ui
     * @param message Error Message
     */
    private void facesError(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String senha) {
        this.password = senha;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean lembrar) {
        this.rememberMe = lembrar;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}

