@echo off
mkdir .\volumes\loki
mkdir .\volumes\grafana
mkdir .\volumes\tempo
mkdir .\volumes\postgres
icacls ".\volumes" /setowner "%USERNAME%" /T /C
icacls ".\volumes" /grant "Everyone:(OI)(CI)F" /T /C