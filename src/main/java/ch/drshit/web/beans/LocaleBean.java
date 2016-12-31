package ch.drshit.web.beans;

import ch.drshit.domain.model.Locale;
import ch.drshit.domain.services.LocaleService;
import ch.drshit.web.exception.ResourceNotFoundException;
import ch.drshit.web.security.ShiroSecured;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Timo Bühlmann
 */
@Named
@ViewScoped
@ShiroSecured
public class LocaleBean implements Serializable {

    @Inject
    private transient LocaleService service;
    
    private Locale locale;

    private MapModel mapModel;
    
    private Integer localeId;

    private List<Locale> locales;

    @RequiresPermissions("locale:read")
    public String actionIndex() {
        locales = service.getAll();
        return null;
    }

    @RequiresPermissions("locale:create")
    public String actionNewLocale() throws IOException {
        this.locale = new Locale();
        initMapModel();
        return null;
    }

    @RequiresPermissions("locale:update")
    public String actionEditLocale() throws IOException {
        Optional<Locale> result = service.getById(localeId);
        if (!result.isPresent()) {
            throw new ResourceNotFoundException("Locale not found.");
        }
        this.locale = result.get();
        initMapModel();
        return null;
    }

    @RequiresPermissions("locale:delete")
    public String actionDelete(Locale locale) throws IOException {
        service.delete(locale);
        return null;
    }

    @RequiresPermissions({"locale:create", "locale:update"})
    public String actionSaveLocale() {
        locale = service.merge(locale);
        return "/pages/locale/index.xhtml";
    }

    private void initMapModel() {
        MapModel model = new DefaultMapModel();
        LatLng coord = new LatLng(locale.getLatitude(), locale.getLongitude());

        model.addOverlay(new Marker(coord, "Lager"));
        mapModel = model;
    }
      
    public void onPointSelect(PointSelectEvent event) {
        LatLng latlng = event.getLatLng();

        locale.setLatitude((float) latlng.getLat());
        locale.setLongitude((float) latlng.getLng());
        initMapModel();
    }

    public List<Locale> getLocales() {
        return locales;
    }
    
    public DayOfWeek[] getDaysOfWeek() {
        return DayOfWeek.values();
    }
    
    public MapModel getMapModel() {
        return mapModel;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Integer getLocaleId() {
        return localeId;
    }

    public void setLocaleId(Integer localeId) {
        this.localeId = localeId;
    }
    
    public Date getLocalePickUpTimeStart() {
        return new Date(locale.getPickUpTimeStart() == null ? 0 : locale.getPickUpTimeStart().getTime());
    }
    
    public void setLocalePickUpTimeStart(Date time) {
        this.locale.setPickUpTimeStart(new Time(time.getTime()));
    }
    
    public Date getLocalePickUpTimeEnd() {
        return new Date(locale.getPickUpTimeEnd() == null ? 0 : locale.getPickUpTimeEnd().getTime());
    }
    
    public void setLocalePickUpTimeEnd(Date time) {
        this.locale.setPickUpTimeEnd(new Time(time.getTime()));
    }
    
    public Date getLocaleReturnTimeStart() {
        return new Date(locale.getReturnTimeStart() == null ? 0 : locale.getReturnTimeStart().getTime());
    }
    
    public void setLocaleReturnTimeStart(Date time) {
        this.locale.setReturnTimeStart(new Time(time.getTime()));
    }
    
    public Date getLocaleReturnTimeEnd() {
        return new Date(locale.getReturnTimeEnd() == null ? 0 : locale.getReturnTimeEnd().getTime());
    }
    
    public void setLocaleReturnTimeEnd(Date time) {
        this.locale.setReturnTimeEnd(new Time(time.getTime()));
    }
}
