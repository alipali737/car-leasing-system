# Java Object Orientated Software Development Summative Project - Car Leasing System
This repository contains the source code & database sql dump for the Java Object Oriented Software Development Summative Project. This system is a Car Leasing System that loans out vehicles to customers.

### Usage
The following steps can be followed to deploy the system on a local machine:

#### Database Setup:
1. Create a new MySQL database with a username & password
2. Create a new database scheme called `main`
3. Run the `.sql` setup script located in `/data` to build and populate the database with the correct tables and pre-packaged records
4. Ensure that the `/src/main/resources/hibernate.cfg.xml` file has the correct connection information to the database

#### Building locally
1. Running `gradle build` will generate a new build of the source

#### Running the application
1. Running `gradle run` will run the source

### Pre-defined Login Details for Testing:
<details>
<summary>Reveal login credentials</summary>

| Username  |   Password    |
|:---------:|:-------------:|
|   admin   | adminpassword |
| user1 |  password1    |

</details>

