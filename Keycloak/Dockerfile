FROM quay.io/keycloak/keycloak:24.0.1

USER root
RUN mkdir -p /opt/keycloak/data/import
COPY realms Users/laame/Downloads/keycloak-26.1.4/data

USER 1000
ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start-dev", "--import-realm"]
