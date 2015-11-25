#!/bin/bash
URL="http://sparrowrecommendationsdb.sb02.stations.graphenedb.com:24789/db/data/"
USERNAME="sparrow_recommendations_db"
PASSWORD="NQEgMqK3WVNZn3mTK4XF"

X=$IFS
IFS='/'

TOKENS=( $URL )
SEC_URL=${TOKENS[0]}//${USERNAME}:${PASSWORD}@${TOKENS[2]}/${TOKENS[3]}/${TOKENS[4]}

IFS=$X

JSON=`printf '{"neo4jUri":"%s"}' $SEC_URL`
cf cups sparrow-recommendations-db -p ${JSON}
