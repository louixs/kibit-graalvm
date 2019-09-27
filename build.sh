#!/bin/bash

COMMAND_NAME=kibit-graalvm

echo "Switching to GraalVM (java 1.8)"

# GraalVM is installed as Java 8, even if you have Java 8 (default for Mac)
# Once you installed GraalVM it seems to override the default Java8
export JAVA_8_HOME=`/usr/libexec/java_home -v 1.8`
export JAVA_HOME="$JAVA_8_HOME"

echo "Java version:"
java -version

# Compile jar
echo "Compiling jar first..."
lein do clean, uberjar

path_to_jar=target/"$COMMAND_NAME"-0.1.0-standalone.jar

echo "Path to the standalone jar file: $path_to_jar"
echo "Compiling native image..."

native-image \
  --report-unsupported-elements-at-runtime \
  --no-fallback \
  --initialize-at-build-time \
  --no-server \
  -Dclojure.compiler.direct-linking=true \
  -jar "$path_to_jar" \
  -H:Name=./"$COMMAND_NAME"

echo "Done"
