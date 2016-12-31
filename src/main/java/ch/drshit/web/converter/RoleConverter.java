/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.drshit.web.converter;

import ch.drshit.domain.model.Permission;
import ch.drshit.domain.services.PermissionService;

import java.util.Optional;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

/**
 *
 * @author timo
 */
@FacesConverter("ch.drshit.RoleConverter")
public class RoleConverter implements Converter{
    
    @Inject
    private PermissionService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return ((Optional<Permission>) service.getById(new Integer(value))).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Permission) value).getId().toString();
    }
    
}
