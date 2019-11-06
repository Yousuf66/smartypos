#! /bin/bash
export JAVA_HOME=$(/usr/libexec/java_home)
./mvnw clean
./mvnw clean package
cd oweb
sudo ng build —prod
cd ..
rm -rf build
mkdir build
mkdir build/oweb
mkdir build/owas
rm -rf build/oweb/*
rm -rf build/owas/*
cp target/*.war build/owas/
cp -r oweb/dist/* build/oweb/