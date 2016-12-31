package ch.drshit.domain.services;

import ch.drshit.domain.model.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Created by timo on 16.12.16.
 */
@Named
@RequestScoped
public class LocaleService extends GenericService<Locale> {

    public LocaleService() {
        super(Locale.class);
    }

}
