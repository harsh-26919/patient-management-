# ---------- Build Stage ----------
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy only pom.xml first for efficient caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# ---------- Run Stage ----------
FROM openjdk:21-jdk AS runner

WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder ./app/target/analytics-service-0.0.1-SNAPSHOT.jar ./app.jar

# JVM memory options (can be overridden with env vars)
ENV JAVA_TOOL_OPTIONS="-Xms512m -Xmx2g"

# Expose your app port
EXPOSE 4002

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]


