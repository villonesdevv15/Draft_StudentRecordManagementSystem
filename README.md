# Student Record Management System (JavaFX)

A simple desktop app to manage student records.

## Features
- Has a login screen (admin account: `admin` / `admin123`)  <!--dagdagan niyo if possible like student>
- Can add, update, delete students
- Has Search bar for ID, name, or course
- Click a table row to edit it
- Records are saved automatically to `students.csv` <!--file handling mostly .csv ginagamit rather than .txt>

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

## FOR VS CODE 
1. Download JavaFX 27 `https://openjfx.io/`
2. Download Extension Pack for Java
3. Open `AnimoRecords` folder and locate Main.java on `src/main/java/studentrecords/Main.java
4. `RUNNING THE PROGRAM` to manually run it press `Ctrl + Shift + B`
