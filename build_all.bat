@echo off
setlocal
set "BASE_DIR=%cd%"

REM echo Building stlab-modules...
REM pushd "%BASE_DIR%\stlab-modules"
REM call mvn clean install -DskipTests
REM popd

REM echo Building empedocle-viewers...
REM pushd "%BASE_DIR%\empedocle\empedocle-viewers"
REM call mvn clean install -DskipTests
REM popd

echo Building stlab-observableentities...
pushd "%BASE_DIR%\empedocle\stlab-observableentities"
call mvn clean install -DskipTests
popd

echo Building stlab-woodelements...
pushd "%BASE_DIR%\empedocle\stlab-woodelements"
call mvn clean install -DskipTests
popd

echo Building empedocle...
pushd "%BASE_DIR%\empedocle\empedocle"
call mvn clean install -DskipTests
popd

echo All builds completed!