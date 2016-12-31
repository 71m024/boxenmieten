package ch.drshit.domain.services;

import ch.drshit.domain.model.Role;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by timo on 16.12.16.
 */
@Named
@RequestScoped
public class RoleService extends GenericService<Role>{

    @Inject
    private transient Logger logger;

    public RoleService() {
        super(Role.class);
    }

}
