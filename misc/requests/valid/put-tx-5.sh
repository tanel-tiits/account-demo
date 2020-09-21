#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -H "Accept: application/json"  \
  --data @tx-5-body.json \
  http://localhost:8080/api/v1/transaction/6c636654-a9f2-4cc8-ac12-2d990ba3972d -v
