#!/bin/sh
set -euo pipefail

MVNW_HOME="$(cd "$(dirname "$0")" && pwd)"
WRAPPER_JAR="$MVNW_HOME/.mvn/wrapper/maven-wrapper.jar"
WRAPPER_PROPS="$MVNW_HOME/.mvn/wrapper/maven-wrapper.properties"

if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Downloading Maven Wrapper..." >&2
  mkdir -p "$MVNW_HOME/.mvn/wrapper"
  curl -fsSL $(grep wrapperUrl "$WRAPPER_PROPS" | cut -d= -f2) -o "$WRAPPER_JAR"
fi

DIST_URL=$(grep distributionUrl "$WRAPPER_PROPS" | cut -d= -f2)
JAVA_EXE="java"

exec "$JAVA_EXE" -Dmaven.multiModuleProjectDirectory="$MVNW_HOME" -cp "$WRAPPER_JAR" org.apache.maven.wrapper.MavenWrapperMain "$@"
