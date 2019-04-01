#!/bin/bash

LATEST=$(ls -t /opt/ns3/dist/*.jar | head -1)
ln -sf ${LATEST} /opt/ns3/latest.jar

systemctl daemon-reload
systemctl enable ns3.service
systemctl start ns3.service

nginx -s reload
