@echo off
echo ========================================
echo   WALMART CHECKOUT SERVICE - SETUP
echo ========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java not found. Please install Java 8 or higher.
    echo Download from: https://adoptopenjdk.net/
    pause
    exit /b 1
)

echo ✓ Java found
java -version

echo.
echo ========================================
echo   STARTING APPLICATION...
echo ========================================
echo.

REM Check if Maven wrapper exists
if exist "mvnw.cmd" (
    echo Using Maven wrapper...
    call mvnw.cmd spring-boot:run
) else (
    echo Maven wrapper not found, trying system Maven...
    mvn spring-boot:run
)

echo.
echo ========================================
echo   APPLICATION READY!
echo ========================================
echo.
echo Frontend:     http://localhost:8080/checkout.html
echo API Docs:     http://localhost:8080/swagger-ui/
echo Health Check: http://localhost:8080/actuator/health
echo.
echo Press any key to continue...
pause >nul