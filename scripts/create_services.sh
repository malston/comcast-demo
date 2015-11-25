cf cups sparrow-config-service -p '{"uri":"http://sparrow-config-server.cfapps.pez.pivotal.io/"}'
cf cups sparrow-service-registry -p '{"uri":"http://sparrow-service-registry.cfapps.pez.pivotal.io/"}'
cf cups sso -p '{"uri":"http://sparrow-auth-server.cfapps.pez.pivotal.io/"}'
