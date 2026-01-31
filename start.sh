#!/bin/bash

echo "========================================"
echo "  WALMART CHECKOUT SERVICE - SETUP"
echo "========================================"
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "ERROR: Java not found. Please install Java 11 or higher."
    echo "Download from: https://adoptopenjdk.net/"
    exit 1
fi

echo "✓ Java found"
java -version

echo
echo "========================================"
echo "  STARTING APPLICATION..."
echo "========================================"
echo

# Check if Maven wrapper exists
if [ -f "./mvnw" ]; then
    echo "Using Maven wrapper..."
    chmod +x ./mvnw
    ./mvnw spring-boot:run
else
    echo "Maven wrapper not found, trying system Maven..."
    mvn spring-boot:run
fi

echo
echo "========================================"
echo "  APPLICATION READY!"
echo "========================================"
echo
echo "Frontend:     http://localhost:8080/checkout.html"
echo "API Docs:     http://localhost:8080/swagger-ui.html"
echo "Health Check: http://localhost:8080/actuator/health"
echo