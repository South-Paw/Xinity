```
University of Canterbury
SENG302 Group Project
29th February - 29th September 2016
```

![Xinity Logo](https://raw.githubusercontent.com/South-Paw/Xinity/master/doc/logo_xinity.png)

# Xinity
A web based musical application by EchoTech (team 4)

## What is it?
Xinity is a web based musical application for assisting with learning and improvement of various
musical skills and knowledge.

## Running Xinity
1. Package Xinity by navigating to the root folder and running `mvn clean site package`
2. Navigate to the `/target` folder and run `java -jar Xinity-X.X.X.jar`
    * Replace X's with the corresponding version number of `.jar` file.
    * This will start the application with the GUI.
3. You can also execute the application with the following additional parameters;
    * `-server` Start's the application in server mode.
    * `-server -withtestdata` Start's the application in server mode and generates databases
    populated with test data (Caution: Will overwrite any existing database contents!)

## Test Data
The `-withtestdata` flag will generate a user with test data (user details, 2 schedules and a few
schedule attempts).

* Username = `test`
* Password = `apples`

## Repo Contents
* `/.idea` IntelliJ workspace & run configurations.
* `/doc` A copy of the applications user manual and a copy of the product backlog we worked from. Also has some example runnable files and run scripts.
* `/src` Project source code.

## Sprint Releases
Please check out the sprint [release tags](https://github.com/South-Paw/Xinity/releases).

Each has an attached jars of the sprint's release. They're a pretty good indication of how far we came.

## Project Contributors
* [Adam Hunt](https://github.com/KiwiPolarBear)
* [Alex Gabites](https://github.com/South-Paw)
* Jono Smythe
* Liam McKee
* Matthew Jensen
* Peter Roger
* [William Scott](https://github.com/babycat)

![Echotech Logo](https://raw.githubusercontent.com/South-Paw/Xinity/master/doc/logo_echotech.png)
