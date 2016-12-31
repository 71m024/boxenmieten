package ch.drshit.web.beans;

import ch.drshit.domain.model.BmUser;
import ch.drshit.domain.model.Role;
import ch.drshit.domain.services.RoleService;
import ch.drshit.domain.services.UserService;
import ch.drshit.web.exception.ExceptionUtil;
import ch.drshit.web.security.ShiroSecured;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.primefaces.model.DualListModel;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
public class UserBean implements Serializable {

    @Inject
    private transient UserService service;

    @Inject
    private transient RoleService roleService;

    private BmUser user;
        
    private String userName;
    
    private String oldPassword;
    
    private String newPassword;
    
    private DualListModel<Role> roleModel;

    private List<BmUser> users;

    @RequiresPermissions("user:read")
    public String actionIndex() {
        users = service.getAll();
        return null;
    }

    @RequiresPermissions("user:create")
    public String actionNewUser() {
        this.user = new BmUser();
        initRoleModel();
        return null;
    }

    @RequiresPermissions("user:update")
    public String actionEditUser() throws IOException {
        Optional<BmUser> result = service.getByName(userName);
        if (result.isPresent()) {
            this.user = result.get();
        } else {
            ExceptionUtil.notFound("User not found.");
        }
        initRoleModel();
        return null;
    }

    @RequiresPermissions({"user:update", "user:create"})
    public String actionSaveUser() {
        if (user.getId() == null) {
            service.setPassword(user, newPassword);
        }
        user.setRoles(roleModel.getTarget());
        if (user.getId() == null) {
            service.persist(user);
        } else {
            user = service.merge(user);
        }
        return "/pages/user/index.xhtml?faces-redirect=true";
    }

    @RequiresPermissions("user:update")
    public String actionUpdatePassword() {
        if (service.validatePassword(user, oldPassword)) {
            service.setPassword(user, newPassword);
            user = service.merge(user);
            oldPassword = null;
            newPassword = null;
        } else {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR ,"Ungültiges Passwort", "Das alte Passwort ist ungültig.");
            facesContext.addMessage(null, facesMessage);
            facesContext.validationFailed();
        }
        return null;
    }

    @RequiresPermissions("user:delete")
    public String actionDelete(BmUser user) {
        service.delete(user);
        System.out.println("User deleted!");
        return null;
    }

    private void initRoleModel() {
        List<Role> targetRoles = user.getRoles() == null ? new ArrayList<>() : user.getRoles();
        List<Role> sourceRoles = new ArrayList<>(roleService.getAll());
        sourceRoles.removeAll(targetRoles);
        roleModel = new DualListModel<>(sourceRoles, targetRoles);
    }

    public List<BmUser> getUsers() {
        return users;
    }

    public BmUser getUser() {
        return user;
    }

    public void setUser(BmUser user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public DualListModel<Role> getRoleModel() {
        return roleModel;
    }
    
    public void setRoleModel(DualListModel<Role> roleModel) {
        this.roleModel = roleModel;
    }
    
}
