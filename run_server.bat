@echo off
TITLE Cricket Scorecard Engine Server
COLOR 0A

echo ===================================================
echo   Cricket Scorecard Engine - Spring Boot Server
echo ===================================================
echo.
echo Starting application on http://localhost:8080 ...
echo Press Ctrl + C in this window to stop the server.
echo.

:: Run Spring Boot application via Maven
call mvn spring-boot:run

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Failed to start Spring Boot application.
    echo Please ensure Java 21 and Maven are installed and MongoDB is running on port 27017.
    echo.
    pause
)
