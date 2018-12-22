![The Green Machine Logo](http://edinarobotics.com/sites/all/themes/greenmachine/assets/images/Logo.gif)

# Zeta

Zeta is FRC Team 1816's robot for the 2019 FRC season *Destination: Deep Space*. The software uses Java and the [WPILib](https://github.com/wpilibsuite/allwpilib) library.

## Prerequisites
1. You must have JDK 8 installed on your system. You can [download it for free from Oracle here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
2. You must have either [Visual Studio Code](https://code.visualstudio.com/) or [IntelliJ IDEA](https://www.jetbrains.com/idea/) installed.
3. You must have [Git](https://git-scm.com/) installed.
4. Select `Import project from external model` and then select 'Gradle'.

## Cloning

Open a new Bash shell and clone:
```bash
$ git clone https://github.com/TheGreenMachine/Zeta.git
```
## Importing

### Importing into IntelliJ IDEA

1. Clone the project into your desired folder.
2. Open IntelliJ IDEA to the welcome screen.
3. **Do NOT select 'Open Project'**. Instead, select 'Import Project'.
4. Select `Import project from external model`. Then select 'Gradle'.
5. Configure your Gradle options:
    * Make sure to select the "Use gradle 'wrapper' task configuration' option.
    * Verify that the Gradle JVM is set to 'Use Project JDK' (which should be Java 8).
    * Keep other options as their defaults.
6. Select 'Finish'. Your project should load in and be set up without issues.

### Importing into Visual Studio Code

<!-- TODO: Complete section -->

## Build and Deploy
Build the project by running the build task through the Gradle wrapper. Just issue the following command in a Bash shell:
```bash
$ ./gradlew build
```
This command isn't always necessary as it is often run by your IDE.

Deploy the project to the robot by first connecting to the robot's wi-fi network and then running the following command:
```bash
$ ./gradlew deploy
```
The deploy task will call the build task automatically.

To clear previous built binaries and minimize the possibility of bugs, it is recommended to run the clean task before deploying:
```bash
$ ./gradlew clean deploy
```
---
2018 - FRC Team 1816 The Green Machine
