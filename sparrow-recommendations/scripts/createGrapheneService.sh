#!/bin/bash
URL="http://192.168.9.205:40749/db/data/"
USERNAME="2fb33f6b-c36c-4af4-b8a6-0bab09852faf"
PASSWORD="5c417948b3cfdd283f4677fe20ce5576"

X=$IFS
IFS='/'

TOKENS=( $URL )
SEC_URL=${TOKENS[0]}//${USERNAME}:${PASSWORD}@${TOKENS[2]}/${TOKENS[3]}/${TOKENS[4]}

IFS=$X

JSON=`printf '{"neo4jUri":"%s"}' $SEC_URL`
cf cups sparrow-recommendations-db -p ${JSON}
