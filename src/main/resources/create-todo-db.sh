#!/bin/bash

# Set the MySQL root password (replace "password" with your own password)
MYSQL_ROOT_PASSWORD="password"

# Create the database
mysql -uroot -p${MYSQL_ROOT_PASSWORD} -e "CREATE DATABASE todolist;"

# Create the tasks table
mysql -uroot -p${MYSQL_ROOT_PASSWORD} -e "USE todolist; CREATE TABLE tasks (id INT NOT NULL AUTO_INCREMENT, description VARCHAR(255), PRIMARY KEY (id));"
