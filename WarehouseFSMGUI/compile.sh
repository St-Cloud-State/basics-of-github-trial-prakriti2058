#!/bin/bash
# Compile script for Warehouse Management System GUI

echo "Compiling Warehouse Management System GUI..."
javac *.java

if [ $? -eq 0 ]; then
    echo "Compilation successful!"
    echo "Run 'java WarehouseGUI' to start the application"
else
    echo "Compilation failed!"
    exit 1
fi