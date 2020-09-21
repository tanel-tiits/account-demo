#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -H "Accept: application/json"  \
  --data @tx-1-body.json \
  http://localhost:8080/api/v1/transaction/ee73c29d-eca2-4cdf-b240-c035fdc765e6 -v
