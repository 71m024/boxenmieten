package ch.drshit.web.security;

import ch.drshit.domain.model.BmUser;
import ch.drshit.domain.model.Permission;
import ch.drshit.domain.model.Role;
import ch.drshit.domain.services.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by timo on 05.11.16.
 */
public class ApplicationRealm extends AuthorizingRealm {

    private transient Object request;

    private transient AuthorizationInfo authorizationInfo;

    public ApplicationRealm() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("SHA-256");
        matcher.setHashIterations(1024);
        setCredentialsMatcher(matcher);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        FacesContext context = FacesContext.getCurrentInstance();
        Object currentRequest = context == null ? null : context.getExternalContext().getRequest();

        if (currentRequest == null || currentRequest != request) { //caching per request

            request = currentRequest;

            String username = ((BmUser) principalCollection.getPrimaryPrincipal()).getName();

            SimpleAuthorizationInfo currentAuthorizationInfo = new SimpleAuthorizationInfo();

            Optional<BmUser> optionalUser = null;
            try {
                optionalUser = getUserService().getByName(username);
            } catch (NamingException e) {
                throw new AuthenticationException("Failed on resolving user-service.");
            }
            if (optionalUser.isPresent()) {
                BmUser user = optionalUser.get();
                Set<String> permissions = new HashSet<>();
                Set<String> roles = new HashSet<>();
                for (Role role : user.getRoles()) {
                    roles.add(role.getName());
                    for (Permission permission : role.getPermissions()) {
                        permissions.add(permission.getName());
                    }
                }
                currentAuthorizationInfo.setRoles(roles);
                currentAuthorizationInfo.setStringPermissions(permissions);
            }

            this.authorizationInfo = currentAuthorizationInfo;

        }

        return this.authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();

        UserService userService = null;
        try {
            userService = getUserService();
        } catch (NamingException e) {
            throw new AuthenticationException("Failed on resolving user-service.");
        }
        if (userService == null){
            throw new AuthenticationException("User-service wasn't found.");
        }

        Optional<BmUser> optionalUser = userService.getByName(username);
        if(!optionalUser.isPresent()) {
            throw new UnknownAccountException();
        }

        BmUser user = optionalUser.get();

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()  //realm name
        );
        return authenticationInfo;
    }

    private UserService getUserService() throws NamingException {
        UserService userService = null;
        BeanManager beanManager = (BeanManager) new InitialContext().lookup("java:comp/BeanManager");
        Bean<UserService> userServiceBean = (Bean<UserService>) beanManager.resolve(beanManager.getBeans(UserService.class));
        CreationalContext<UserService> creationalContext = beanManager.createCreationalContext(null);
        userService = userServiceBean.create(creationalContext);
        creationalContext.release();
        return userService;
    }
}
