@echo off
javac -cp "postgresql-42.7.3.jar;bcrypt-0.10.2.jar;bytes-1.0.0.jar;." Main.java app\*.java dao\*.java model\*.java util\*.java controller\*.java
java -cp "postgresql-42.7.3.jar;bcrypt-0.10.2.jar;bytes-1.0.0.jar;." Main
