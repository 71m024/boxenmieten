FROM tomee:8-jdk-7.0.1-webprofile

COPY tomeeCustomization/tomee.xml /usr/local/tomee/conf/
COPY tomeeCustomization/mysql-connector-java-5.1.40-bin.jar /usr/local/tomee/lib/
COPY tomeeCustomization/tomcat-users.xml /usr/local/tomee/conf/tomcat-users.xml

ADD autodeploy/ROOT.war /usr/local/tomee/webapps/

EXPOSE 8080 8089

# delete this line and the standart (catalina.sh run) will be used...
ENV JPDA_OPTS "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"
CMD ["catalina.sh", "jpda", "run"]