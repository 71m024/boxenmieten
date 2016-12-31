/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.drshit.db;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.sql.DataSource;
import java.util.logging.Logger;

/**
 *
 * @author timo
 */
@Singleton
@Startup
@TransactionManagement(TransactionManagementType.BEAN)
public class Migrator {
    private static final Logger LOG = Logger.getLogger(Migrator.class.getName());

    @Resource(type = DataSource.class, name = "bmDataSource")
    private DataSource dataSource;

    @PostConstruct
    private void onStartup() {

        if (dataSource == null) {
            LOG.severe("no datasource found to execute the db migrations!");
            throw new EJBException(
                "no datasource found to execute the db migrations!");
        }

        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        for (MigrationInfo i : flyway.info().all()) {
            StringBuilder migrationLog = new StringBuilder();
            migrationLog
                .append("migrate task: ").append(i.getVersion())
                .append(" : ").append(i.getDescription())
                .append(" from file: ").append(i.getScript());
            LOG.info(migrationLog.toString());
        }
        flyway.migrate();
    }
}
