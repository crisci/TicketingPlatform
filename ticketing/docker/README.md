# Run configscript.sh or follow the commands below to setup the volumes for the containers
---
## Folders with the required uid:gid need to be created

## Postgres
- `mkdir -p ./volumes/postgres`

## Loki
- `mkdir -p ./volumes/loki`
- `sudo chown 10001:10001 ./volumes/loki`

## Grafana
- `mkdir -p ./volumes/grafana`
- `sudo chown 472:472 ./volumes/grafana`

## Tempo
- `mkdir -p ./volumes/tempo`