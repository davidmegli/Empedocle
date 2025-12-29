#!/bin/bash

# Directory principale del progetto
BASE_DIR="$(pwd)"

# Compilazione di stlab-modules
echo "Building stlab-modules..."
cd "$BASE_DIR/stlab-modules" || exit
mvn clean install -DskipTests

## Compilazione di stlab-observableentities
echo "Building stlab-observableentities..."
cd "$BASE_DIR/empedocle/stlab-observableentities" || exit
mvn clean install -DskipTests

# Compilazione di stlab-woodelements
echo "Building stlab-football..."
cd "$BASE_DIR/empedocle/stlab-football" || exit
mvn clean install -DskipTests

# Compilazione di empedocle
echo "Building empedocle..."
cd "$BASE_DIR/empedocle/empedocle" || exit
mvn clean install -DskipTests

echo "All builds completed!"


