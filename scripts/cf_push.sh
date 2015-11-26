cf push sparrow-auth-server -f ../sparrow-auth-server/manifest.yml
cf push sparrow-config-server -f ../sparrow-config-server/manifest.yml
cf push sparrow-eureka -f ../sparrow-eureka/manifest.yml
cf push sparrow-catalog -f ../sparrow-catalog/manifest.yml
cf push sparrow-reviews -f ../sparrow-reviews/manifest.yml
cf push sparrow-recommendations -f ../sparrow-recommendations/manifest.yml
cf push sparrow-web -f ../sparrow-web/manifest.yml
#cf push sparrow-api-gateway -f ../sparrow-api-gateway/manifest.yml
