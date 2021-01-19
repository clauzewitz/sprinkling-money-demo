#!/usr/bin/env bash
sleep 5

mongo --host mongo1:27017 <<EOF
  use admin;
  db.auth("root", "pa22word@!");
  var cfg = {
    "_id": "mongo-rp",
    "version": 1,
    "protocolVersion" : NumberLong(1),
    "members": [
      {
        "_id": 0,
        "host": "mongo1:27017",
        "priority": 5
      },
      {
        "_id": 1,
        "host": "mongo2:27017",
        "priority": 0
      },
      {
        "_id": 2,
        "host": "mongo3:27017",
        "priority": 1,
        "arbiterOnly": true
      },
    ]
  };
  rs.initiate(cfg, {
    "force": true
  });
  rs.reconfig(cfg, {
    "force": true
  });
EOF

sleep 20

mongo --host mongo1:27017 <<EOF
  use admin;
  db.auth("root", "pa22word@!");

  db = db.getSiblingDB('demo');
  db.createUser(
      {
          user: 'demoUser',
          pwd: 'demoUser',
          roles: [{ role: 'readWrite', db: 'demo' }]
      }
  );
EOF