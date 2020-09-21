#!/bin/bash

# Source and destination accounts overlap

curl -X PUT -H "Content-Type: application/json" -H "Accept: application/json"  \
  --data @invalid-tx-6-body.json \
  http://localhost:8080/api/v1/transaction/46f2a305-a7d9-4466-8f43-c3344b074064 -v
