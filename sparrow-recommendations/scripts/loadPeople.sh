#!/bin/bash
ROUTE=${ROUTE:-sparrow-recommendations.cfapps.pez.pivotal.io}
curl ${ROUTE}/people -X POST -d '{"userName":"mstine","firstName":"Matt","lastName":"Stine"}' -H "Content-Type: application/json"
curl ${ROUTE}/people -X POST -d '{"userName":"starbuxman","firstName":"Josh","lastName":"Long"}' -H "Content-Type: application/json"
curl ${ROUTE}/people -X POST -d '{"userName":"littleidea","firstName":"Andrew","lastName":"Shafer"}' -H "Content-Type: application/json"