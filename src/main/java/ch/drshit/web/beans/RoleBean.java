package ch.drshit.web.beans;

import ch.drshit.domain.model.Permission;
import ch.drshit.domain.model.Role;
import ch.drshit.domain.services.PermissionService;
import ch.drshit.domain.services.RoleService;
import ch.drshit.web.exception.ExceptionUtil;
import ch.drshit.web.security.ShiroSecured;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.primefaces.model.DualListModel;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Timo Bühlmann
 */
@Named
@ViewScoped
@ShiroSecured
public class RoleBean implements Serializable {

    @Inject
    private transient RoleService service;
    
    @Inject
    private transient PermissionService permissionService;
    
    private Role role;
    
    private Integer roleId;

    private List<Role> roles;
    
    private DualListModel<Permission> permissionModel;

    @RequiresPermissions("role:read")
    public String actionIndex() {
        roles = service.getAll();
        return null;
    }

    @RequiresPermissions("role:create")
    public String actionNew() {
        this.role = new Role();
        initPermissionModel();
        return null;
    }

    @RequiresPermissions("role:update")
    public String actionEdit() throws IOException {
        Optional<Role> result = service.getById(roleId);
        if (result.isPresent()) {
            this.role = result.get();
        } else {
            ExceptionUtil.notFound("Role not found.");
        }
        initPermissionModel();
        return null;
    }

    @RequiresPermissions("role:delete")
    public String actionDelete(Role role) {
        service.delete(role);
        System.out.println("User deleted!");
        return null;
    }

    @RequiresPermissions({"role:create", "role:update"})
    public String actionSave() {
        role.setPermissions(permissionModel.getTarget());
        role = service.merge(role);
        return "/pages/role/index.xhtml";
    }

    private void initPermissionModel() {
        List<Permission> targetPermissions = role.getPermissions() == null ? new ArrayList<>() : role.getPermissions();
        List<Permission> sourcePermissions = permissionService.getAll();
        sourcePermissions.removeAll(targetPermissions);
        permissionModel = new DualListModel<>(sourcePermissions, targetPermissions);
    }

    public List<Role> getRoles() {
        return roles;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role user) {
        this.role = user;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer groupId) {
        this.roleId = groupId;
    }
        
    public DualListModel<Permission> getPermissionModel() {
        return permissionModel;
    }
    
    public void setPermissionModel(DualListModel<Permission> permissions) {
        this.permissionModel = permissions;
    }
}
