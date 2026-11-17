# Student Record Management System (JavaFX)

A simple desktop app to manage student records.

## Features
- Login screen (demo account: `admin` / `admin123`)
- Add, update, delete students
- Search by ID, name, or course
- Click a table row to edit it
- Records are saved automatically to `students.csv`

## Project structure
    src/main/java/studentrecords/
        Main.java                 starts the app, shows login
        LoginController.java      checks credentials, opens dashboard
        DashboardController.java  add / update / delete / search
        Student.java              student model
        StudentStorage.java       reads/writes students.csv
    src/main/resources/studentrecords/
        login.fxml
        dashboard.fxml

## Run with Maven (JDK 25+)
    mvn javafx:run

## Run in IntelliJ / Eclipse
1. Open the folder as a Maven project (or add the JavaFX SDK to your project).
2. Run `studentrecords.Main`.
3. If you add the SDK manually, use these VM options:
   `--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml`

Change `javafx.version` in `pom.xml` if you use a different JavaFX version.
