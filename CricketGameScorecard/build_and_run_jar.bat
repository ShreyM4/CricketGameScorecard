@echo off
TITLE Cricket Scorecard Engine - Build & Run Production JAR
COLOR 0B

echo ===================================================
echo   Cricket Scorecard Engine - Production JAR Runner
echo ===================================================
echo.
echo Step 1: Packaging application into executable JAR...
echo.

call mvn clean package -DskipTests

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Build failed! Check Maven output above.
    pause
    exit /b %ERRORLEVEL%
)

echo.
echo Step 2: Launching Production JAR file...
echo Web Dashboard available at: http://localhost:8080
echo.

java -jar target\scorecard-0.0.1-SNAPSHOT.jar

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Application execution stopped.
    pause
)
