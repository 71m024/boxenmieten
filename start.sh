#!/bin/sh

/usr/local/glassfish4/bin/asadmin start-domain --debug
/usr/local/glassfish4/bin/asadmin -u admin deploy /Boxenmieten-1.0-SNAPSHOT.war

apt-get update && apt-get install -y wget unzip expect
expect -c 'spawn asadmin --user admin change-admin-password;expect "password";send "\n";expect "password";send "password\n";expect "password";send "password\n";expect eof;spawn asadmin enable-secure-admin;expect "admin";send "admin\n";expect "password";send "password\n";expect eof;exit'

apt-get -y install cron
chmod 0644 /etc/cron.d/glassfish-memory-cron
chmod 0700 /memory_logger.sh
chmod +x /memory_logger.sh
touch /var/log/glassfish_memory.log
service cron start
crontab /etc/cron.d/glassfish-memory-cron
expect -c 'spawn asadmin enable-monitoring --modules jvm=LOW;expect "name";send "admin\n";expect "password";send "password\n";expect eof;exit'

/usr/local/glassfish4/bin/asadmin stop-domain
/usr/local/glassfish4/bin/asadmin start-domain

expect -c 'spawn asadmin;expect "certificate";send "y\n";expect "asadmin";send "exit\n";expect eof;exit'

/usr/local/glassfish4/bin/asadmin stop-domain
/usr/local/glassfish4/bin/asadmin start-domain --debug --verbose
