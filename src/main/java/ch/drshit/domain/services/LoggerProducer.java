package ch.drshit.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * Created by timo on 16.12.16.
 */
public class LoggerProducer {

    @Produces
    Logger getLogger(InjectionPoint ip) {

        String category = ip.getMember().getDeclaringClass().getName();

        return LoggerFactory.getLogger(category);

    }
}
