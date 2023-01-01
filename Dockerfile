FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk17

# Create test admin user-manager
RUN /opt/jboss/wildfly/bin/add-user.sh admin admin --silent

# Deploy application api .war
ADD ./taparia-api/target/api.war /opt/jboss/wildfly/standalone/deployments/

# Deploy application app .war
ADD ./taparia-app/target/app.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080
EXPOSE 9990
