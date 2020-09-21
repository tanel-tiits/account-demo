#!/bin/bash
curl -X PUT -H "Content-Type: application/json" -H "Accept: application/json"  \
  --data @tx-3-body.json \
  http://localhost:8080/transaction/0b0b0cc1-f890-4c68-a1c1-77e0690932db -v
