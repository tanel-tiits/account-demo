#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -H "Accept: application/json"  \
  --data @tx-4-body.json \
  http://localhost:8080/api/v1/transaction/2770a044-0931-4df7-bdf1-40433163b43b -v
