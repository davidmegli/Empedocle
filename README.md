# Empedocle
## Overview
This repository provides the code and detailed instructions on how to build it and deploy it on Wildfly. Specifically, detailed instructions will be given on how to compile the project directly from the command line along with a properly configured Wildfly server. This scenario is useful for developers who prefer a more granular and personalized management of the build and deploy process.

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [License](#license)

## Installation

### Prerequisites
#### Directory Structure
Module Structure
Ensure that you have a module structure as follows:
```
sourceCode/
├── stlab-modules/
└── empedocle/
   ├── empedocle/
   ├── stlab-observableentities/
   └── stlab-woodelements/
```
### Installing
#### Building Modules with Maven
On Windows, run the ```build_all.bat```
on Linux, run the ```build_all.sh```

### Database Setup
Use the empedocle_initialized.sql script provided at this [link](https://drive.google.com/file/d/16_Q-XjCycEcMkoMV9e7F5WZMR5gGZ_LC/view?usp=drive_link) to create the database schema. Ensure you have an empty database called emp_db on your system and run the script inside it to create tables and populate initial data.

Pre-set data:
* Username: administrator
* Password: adminpass

### Deploying the .war File with Wildfly
Once the previous steps are completed, a .war file will be created in the `sourceCode/empedocle/empedocle/target` directory.

Copy the `empedocle-1.0.0.war` file, generated in the `sourceCode/empedocle/empedocle/target` directory, into the `wildfly-16.0.0.Final/standalone/deployments` folder.

Navigate to the `wildfly-16.0.0.Final/bin` folder.

Execute the command: 
```
./standalone.sh
```
Once Wildfly is started correctly, the `empedocle.war` file will be automatically deployed from the deployments folder.

After a successful deployment, you can access the application at the following URL: http://localhost:9990

## Usage
### To deploy another domain
If you want to deploy another domain, you don't need to use the stlab-woodelements package, but you can use it as an example.

### Accessing the Endpoints
Several REST endpoints have been developed to perform CRUD operations on the main entities.  
They can be accessed at:
http://localhost:8080/empedocle-1.0.0/
For more details, refer to the `endpoints.pdf` document.
