#!/bin/bash

docker-compose up -d
sleep 60
curl -viX POST \
  'http://localhost:1026/v2/subscriptions/' \
  -H 'Content-Type: application/json' \
  -d '{"description": "Test subscription","subject": {"entities": [{"idPattern": ".*","type": "Person"}],"condition": {"attrs": ["payload"]}},"notification": {"http": {"url": "http://quantumleap:8668/v2/notify"},"attrs": ["payload"],"metadata": ["dateCreated", "dateModified"]},"throttling": 5}'



