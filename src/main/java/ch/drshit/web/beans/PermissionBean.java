package ch.drshit.web.beans;

import ch.drshit.domain.model.Permission;
import ch.drshit.domain.services.PermissionService;
import ch.drshit.web.exception.ExceptionUtil;
import ch.drshit.web.security.ShiroSecured;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Timo Bühlmann
 */
@Named
@ViewScoped
@ShiroSecured
public class PermissionBean implements Serializable {

    @Inject
    private transient PermissionService service;
    
    private Permission permission;
    
    private Integer roleId;

    private List<Permission> permissions;

    @RequiresPermissions("permission:read")
    public String actionIndex() {
        permissions = service.getAll();
        return null;
    }

    @RequiresPermissions("permission:create")
    public String actionNew() {
        this.permission = new Permission();
        return null;
    }

    @RequiresPermissions("permission:update")
    public String actionEdit() throws IOException {
        Optional<Permission> result = service.getById(roleId);
        if (result.isPresent()) {
            this.permission = result.get();
        } else {
            ExceptionUtil.notFound("Permission not found.");
        }
        return null;
    }

    @RequiresPermissions("permission:delete")
    public String actionDelete(Permission permission) {
        service.delete(permission);
        System.out.println("Permission deleted!");
        return null;
    }

    @RequiresPermissions({"permission:create", "permission:update"})
    public String actionSave() {
        permission = service.merge(permission);
        return "/pages/permission/index.xhtml";
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Integer getPermissionId() {
        return roleId;
    }

    public void setPermissionId(Integer roleId) {
        this.roleId = roleId;
    }
}
