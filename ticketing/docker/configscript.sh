#!/bin/bash
mkdir -p ./volumes/loki
sudo chown 10001:10001 ./volumes/loki
mkdir -p ./volumes/grafana
sudo chown 472:472 ./volumes/grafana
mkdir -p ./volumes/tempo
mkdir -p ./volumes/postgres