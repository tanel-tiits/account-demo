#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -H "Accept: application/json"  \
  --data @tx-2-body.json \
  http://localhost:8080/api/v1/transaction/10e157d1-a170-418e-8754-50d67e65c5c7 -v
