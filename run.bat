@echo off
echo Compiling TeamFlow Lite...
javac -cp . Entity\Task.java EntityList\TaskList.java FileIO\FileIO.java GUI\*.java Main.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo Running TeamFlow Lite...
java -cp . Main

pause 