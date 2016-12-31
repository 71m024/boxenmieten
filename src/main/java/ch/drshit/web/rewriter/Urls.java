package ch.drshit.web.rewriter;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

import javax.servlet.ServletContext;

/**
 * Created by timo on 29.11.16.
 */
@RewriteConfiguration
public class Urls extends HttpConfigurationProvider {

    public Configuration getConfiguration(ServletContext context) {
        return ConfigurationBuilder.begin()

                .addRule(Join.path("/home").to("/pages/home.xhtml"))
                .addRule(Join.path("/").to("/pages/promotion-page.xhtml"))
                .addRule(Join.path("/login").to("/pages/login.xhtml"))

                .addRule(Join.path("/users").to("/pages/user/index.xhtml"))
                .addRule(Join.path("/user/edit").to("/pages/user/edit.xhtml"))
                .addRule(Join.path("/user/edit/{name}").to("/pages/user/edit.xhtml"))

                .addRule(Join.path("/roles").to("/pages/role/index.xhtml"))
                .addRule(Join.path("/role/edit").to("/pages/role/edit.xhtml"))
                .addRule(Join.path("/role/edit/{id}").to("/pages/role/edit.xhtml"))

                .addRule(Join.path("/locales").to("/pages/locale/index.xhtml"))
                .addRule(Join.path("/locale/edit").to("/pages/locale/edit.xhtml"))
                .addRule(Join.path("/locale/edit/{id}").to("/pages/locale/edit.xhtml"))

                .addRule(Join.path("/permissions").to("/pages/permission/index.xhtml"))
                .addRule(Join.path("/permission/edit").to("/pages/permission/edit.xhtml"))
                .addRule(Join.path("/permission/edit/{id}").to("/pages/permission/edit.xhtml"))
        ;
    }


    public int priority() {
        return 0;
    }
}
