#!/bin/bash
URL="http://127.0.0.1:7474/db/data/"
USERNAME="neo4j"
PASSWORD="neo4j"

X=$IFS
IFS='/'

TOKENS=( $URL )
SEC_URL=${TOKENS[0]}//${USERNAME}:${PASSWORD}@${TOKENS[2]}/${TOKENS[3]}/${TOKENS[4]}

IFS=$X

JSON=`printf '{"neo4jUri":"%s"}' $SEC_URL`
cat $JSON
#cf cups sparrow-recommendations-db -p ${JSON}
