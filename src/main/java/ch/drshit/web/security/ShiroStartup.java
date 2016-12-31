package ch.drshit.web.security;

import org.apache.shiro.jndi.JndiTemplate;

import javax.naming.NamingException;

/**
 * Created by timo on 05.11.16.
 */
//@Singleton
//@Startup
public class ShiroStartup {
    //@Inject private IBmUserService userService;

    private JndiTemplate jndiTemplate = new JndiTemplate();

    //@PostConstruct
    public void setup () {
        //constructor injection
        ApplicationRealm myAppRealm = new ApplicationRealm();

        try {
            //bind it so shiro can find it!
            jndiTemplate.bind("realms/ApplicationRealm", myAppRealm);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    //@PreDestroy
    public void destroy () {
        try {
            jndiTemplate.unbind("realms/ApplicationRealm"); //clean-up!
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
