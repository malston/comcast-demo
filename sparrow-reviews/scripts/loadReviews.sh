#!/bin/bash
ROUTE=${ROUTE:-http://sparrow-reviews.cfapps.pez.pivotal.io}

curl acme:acmesecret@sparrow-auth-server.cfapps.pez.pivotal.io/uaa/oauth/token \
-d grant_type=authorization_code -d client_id=acme \
-d redirect_uri=sparrow.cfapps.pez.pivotal.io/login -d code=UCuSxd
# {"access_token":"2219199c-966e-4466-8b7e-12bb9038c9bb","token_type":"bearer","refresh_token":"d193caf4-5643-4988-9a4a-1c03c9d657aa","expires_in":43199,"scope":"openid"}

http://sparrow-auth-server.cfapps.pez.pivotal.io/uaa/oauth/authorize?client_id=acme&redirect_uri=http://sparrow.cfapps.pez.pivotal.io/login&response_type=code&state=KehJyb

curl ${ROUTE}/reviews -X POST -d '{"userName":"mstine","mlId":"1","title":"Toy Story (1995)","review":"Great movie!","rating":"5"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"mstine","mlId":"2","title":"GoldenEye (1995)","review":"Pretty good...","rating":"3"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"starbuxman","mlId":"2","title":"GoldenEye (1995)","review":"BOND BOND BOND!","rating":"5"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"starbuxman","mlId":"4","title":"Get Shorty (1995)","review":"Meh","rating":"3" }}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"starbuxman","mlId":"5","title":"Copycat (1995)","review":"Nicely done!","rating":"4"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"littleidea","mlId":"2","title":"GoldenEye (1995)","review":"Good show!","rating":"4"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"littleidea","mlId":"3","title":"Four Rooms (1995)","review":"Could have been better...","rating":"3"}' -H "Content-Type: application/json"
curl ${ROUTE}/reviews -X POST -d '{"userName":"littleidea","mlId":"5","title":"Copycat (1995)","review":"Nicely done!","rating":"4"}' -H "Content-Type: application/json"
