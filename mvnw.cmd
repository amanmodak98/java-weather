@ECHO OFF
SETLOCAL
SET MVNW_HOME=%~dp0
SET WRAPPER_JAR=%MVNW_HOME%\.mvn\wrapper\maven-wrapper.jar
SET WRAPPER_PROPS=%MVNW_HOME%\.mvn\wrapper\maven-wrapper.properties
IF NOT EXIST "%WRAPPER_JAR%" (
  ECHO Downloading Maven Wrapper...
  IF NOT EXIST "%MVNW_HOME%\.mvn\wrapper" mkdir "%MVNW_HOME%\.mvn\wrapper"
  FOR /F "tokens=2 delims==" %%A IN ('findstr /R "^wrapperUrl=" "%WRAPPER_PROPS%"') DO set WRAPURL=%%A
  powershell -Command "Invoke-WebRequest -Uri %WRAPURL% -OutFile '%WRAPPER_JAR%'"
)
FOR /F "tokens=2 delims==" %%A IN ('findstr /R "^distributionUrl=" "%WRAPPER_PROPS%"') DO set DISTURL=%%A
SET JAVA_EXE=java
"%JAVA_EXE%" -Dmaven.multiModuleProjectDirectory=%MVNW_HOME% -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
