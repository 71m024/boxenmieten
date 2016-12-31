package ch.drshit.domain.services;

import ch.drshit.domain.model.Permission;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by timo on 16.12.16.
 */
@Named
@RequestScoped
public class PermissionService extends GenericService<Permission> {

    public PermissionService() {
        super(Permission.class);
    }

}
