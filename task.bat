@echo off
set arg1=%1

javac .\src\*.java -d .\bin
java -cp .\bin Main