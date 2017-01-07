package ch.drshit.domain;

import ch.drshit.domain.model.BmUser;
import ch.drshit.domain.services.PasswordHash;
import ch.drshit.domain.services.UserService;
import org.apache.shiro.util.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.filter.ExcludeRegExpPaths;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by timo on 31.12.16.
 */

@RunWith(Arquillian.class)
public class UserServiceTest {

    @Inject
    private UserService service;

    @Deployment
    public static WebArchive deployment() {
        return ShrinkWrap.create(WebArchive.class, "hello.war")
                .addClasses(UserService.class, BmUser.class, PasswordHash.class);
    }

    @Test
    public void should_find_a_user() {
        List<BmUser> users = service.getAll();
        Assert.notEmpty(users);
    }


}
