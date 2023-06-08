@echo off
mkdir ".\volumes\loki"
takeown /F ".\volumes\loki" /R /D Y
icacls ".\volumes\loki" /grant "10001:RX"
mkdir ".\volumes\grafana"
takeown /F ".\volumes\grafana" /R /D Y
icacls ".\volumes\grafana" /grant "472:RX"
mkdir ".\volumes\tempo"
mkdir ".\volumes\postgres"