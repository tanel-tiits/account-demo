#!/bin/bash

# Transferrable cash amount is missing

curl -X PUT -H "Content-Type: application/json" -H "Accept: application/json"  \
  --data @invalid-tx-7-body.json \
  http://localhost:8080/transaction/46f2a305-a7d9-4466-8f43-c3344b074064 -v
