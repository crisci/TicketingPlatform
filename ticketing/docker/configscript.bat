@echo off
mkdir ".\volumes\loki"
takeown /F ".\volumes\loki" /R
icacls ".\volumes\loki" /grant "10001:RX"
mkdir ".\volumes\grafana"
takeown /F ".\volumes\grafana" /R
icacls ".\volumes\grafana" /grant "472:RX"
mkdir ".\volumes\tempo"
mkdir ".\volumes\postgres"