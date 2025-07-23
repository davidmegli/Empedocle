#!/bin/bash

# Directory principale del progetto
BASE_DIR="$(pwd)"

## Compilazione di stlab-modules
#echo "Building stlab-modules..."
#cd "$BASE_DIR/stlab-modules" || exit
#mvn clean install -DskipTests
#
## Compilazione di empedocle-viewers
#echo "Building empedocle-viewers..."
#cd "$BASE_DIR/empedocle/empedocle-viewers" || exit
#mvn clean install -DskipTests

## Compilazione di stlab-observableentities
#echo "Building stlab-observableentities..."
#cd "$BASE_DIR/empedocle/stlab-observableentities" || exit
#mvn clean install -DskipTests

# Compilazione di stlab-woodelements
echo "Building stlab-woodelements..."
cd "$BASE_DIR/empedocle/stlab-woodelements" || exit
mvn clean install -DskipTests

# Compilazione di empedocle
echo "Building empedocle..."
cd "$BASE_DIR/empedocle/empedocle" || exit
mvn clean install -DskipTests

echo "All builds completed!"

