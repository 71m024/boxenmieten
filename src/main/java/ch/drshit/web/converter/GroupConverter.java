/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.drshit.web.converter;

import ch.drshit.domain.model.Role;
import ch.drshit.domain.services.RoleService;

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
@FacesConverter("ch.drshit.GroupConverter")
public class GroupConverter implements Converter{
    
    @Inject
    private RoleService service;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return ((Optional<Role>) service.getById(new Integer(value).intValue())).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return ((Role) value).getId().toString();
    }
    
}
