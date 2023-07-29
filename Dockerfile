# Use the official OpenJDK image as the base image
FROM openjdk:11

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled Java application JAR file to the container
COPY out/artifacts/java_task_jar/java_task.jar /app/java_task.jar
# Expose the port on which your API will listen
EXPOSE 8080
# Set the command to run your Java application
CMD ["java", "-jar", "java_task.jar"]