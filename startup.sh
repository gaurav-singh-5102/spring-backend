#!/bin/bash

# Check if the profile arg is provided
if [ $# -ne 1 ]; then
    echo "Usage: $0 <profile>"
    exit 1
fi

# Profile argument
PROFILE=$1

# Application Directories
APP_DIRS=("Commentservice" "Gateway" "Notificationservice" "auth" "chat" "eurekaserver" "postservice" "userservice")

# Iterate through each directory
for APP_DIR in "${APP_DIRS[@]}"; do
    # Naviagte to application directory
    cd "$APP_DIR" || exit

    # Build the application
    echo "Building $APP_DIR..."
    mvn clean package

    # Check if the maven build was successful
    if [ $? -eq 0 ]; then
        # Get JAR/WAR
        JAR_FILE=$(find target/ -name "*.jar" -o -name "*.war" | head -1)

        # Check if jar exists
        if [ -n "$JAR_FILE" ]; then
            # Get File name
            FILENAME=$(basename "$JAR_FILE")

            # Start application with profile
            if [[ "$APP_DIR" == "auth" || "$APP_DIR" == "Commentservice" ]]; then
                echo "Starting $APP_DIR with profile $PROFILE..."
                nohup java -jar "$JAR_FILE" --spring.profile.active=$PROFILE >/dev/null 2>&1 &
            else
                echo "Starting $APP_DIR..."
                nohup java -jar "$JAR_FILE" >/dev/null 2>&1 &
            fi
            echo "$APP_DIR started successfully." 
        else
            echo "Error : JAR/WAR file not found for $APP_DIR!"
        fi 
    else
        echo "Error : Maven Build failed for $APP_DIR!"
    fi 

    cd ../ || exit
done
