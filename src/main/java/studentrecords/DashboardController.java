package studentrecords;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DashboardController {

    // these are connected to the fx:id in dashboard.fxml
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField courseField;
    @FXML private ComboBox<String> yearCombo;
    @FXML private TextField emailField;
    @FXML private TextField searchField;
    @FXML private Label countLabel;

    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> idCol;
    @FXML private TableColumn<Student, String> nameCol;
    @FXML private TableColumn<Student, String> courseCol;
    @FXML private TableColumn<Student, String> yearCol;
    @FXML private TableColumn<Student, String> emailCol;

    // list of all students
    private ObservableList<Student> students = FXCollections.observableArrayList();
    // this one is the list after searching
    private FilteredList<Student> filtered;

    // runs automatically when the dashboard opens
    @FXML
    public void initialize() throws Exception {
        yearCombo.getItems().addAll("1", "2", "3", "4", "5");

        // tells each column which data to show
        idCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getId()));
        nameCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getName()));
        courseCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getCourse()));
        yearCol.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getYearLevel())));
        emailCol.setCellValueFactory(d -> new SimpleStringProperty(d.getValue().getEmail()));

        students.addAll(StudentStorage.load());

        // filtered list for the search, sorted list so clicking the column header still sorts
        filtered = new FilteredList<>(students, s -> true);
        SortedList<Student> sorted = new SortedList<>(filtered);
        sorted.comparatorProperty().bind(studentTable.comparatorProperty());
        studentTable.setItems(sorted);

        // search while typing (id, name or course)
        searchField.textProperty().addListener((obs, oldText, newText) -> {
            String q = newText.toLowerCase().trim();
            filtered.setPredicate(s -> q.isEmpty()
                    || s.getId().toLowerCase().contains(q)
                    || s.getName().toLowerCase().contains(q)
                    || s.getCourse().toLowerCase().contains(q));
            updateCount();
        });

        // when a row is clicked, put its data in the form
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldS, s) -> {
            if (s != null) {
                fillForm(s);
            }
        });

        updateCount();
    }

    @FXML
    private void handleAdd() throws Exception {
        Student s = readForm();
        if (s == null) return;

        // no duplicate ids
        for (Student existing : students) {
            if (existing.getId().equalsIgnoreCase(s.getId())) {
                showAlert(Alert.AlertType.WARNING, "A student with ID " + s.getId() + " already exists.");
                return;
            }
        }

        students.add(s);
        StudentStorage.save(students);
        clearForm();
        updateCount();
    }

    @FXML
    private void handleUpdate() throws Exception {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.INFORMATION, "Select a student in the table FIRST!!.");
            return;
        }

        Student updated = readForm();
        if (updated == null) return;

        // replace the old student with the new one
        students.set(students.indexOf(selected), updated);
        StudentStorage.save(students);
        clearForm();
        updateCount();
    }

    @FXML
    private void handleDelete() throws Exception {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert(Alert.AlertType.INFORMATION, "Select a student in the table first.");
            return;
        }

        // ask first before deleting
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Delete " + selected.getName() + " (" + selected.getId() + ")?",
                ButtonType.YES, ButtonType.NO);
        confirm.setHeaderText(null);

        if (confirm.showAndWait().get() == ButtonType.YES) {
            students.remove(selected);
            StudentStorage.save(students);
            clearForm();
            updateCount();
        }
    }

    @FXML
    private void handleClear() {
        clearForm();
    }

    @FXML
    private void handleLogout() throws Exception {
        // go back to the login screen
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Stage stage = (Stage) studentTable.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.centerOnScreen();
    }

    // gets the data from the form, returns null if something is wrong
    private Student readForm() {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        String course = courseField.getText().trim();
        String year = yearCombo.getValue();
        String email = emailField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || course.isEmpty() || year == null || email.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please fill in all fields.");
            return null;
        }

        // simple email check
        if (!email.contains("@") || !email.contains(".")) {
            showAlert(Alert.AlertType.WARNING, "Please enter a valid email address.");
            return null;
        }

        return new Student(id, name, course, Integer.parseInt(year), email);
    }

    // puts the student's data in the form
    private void fillForm(Student s) {
        idField.setText(s.getId());
        nameField.setText(s.getName());
        courseField.setText(s.getCourse());
        yearCombo.setValue(String.valueOf(s.getYearLevel()));
        emailField.setText(s.getEmail());
    }

    // empties the form
    private void clearForm() {
        idField.clear();
        nameField.clear();
        courseField.clear();
        yearCombo.setValue(null);
        emailField.clear();
        studentTable.getSelectionModel().clearSelection();
    }

    // updates the "Showing x of y students" label
    private void updateCount() {
        countLabel.setText("Showing " + filtered.size() + " of " + students.size() + " students");
    }

    // shows a popup message
    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type, msg);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}