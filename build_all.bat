@echo off
setlocal
set "BASE_DIR=%cd%"

echo Building stlab-modules...
pushd "%BASE_DIR%\stlab-modules"
call mvn clean install -DskipTests
popd

echo Building empedocle-viewers...
pushd "%BASE_DIR%\empedocle\empedocle-viewers"
call mvn clean install -DskipTests
popd

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