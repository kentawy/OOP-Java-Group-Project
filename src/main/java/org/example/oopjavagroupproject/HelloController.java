package org.example.oopjavagroupproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.oopjavagroupproject.data.ContractDao;
import org.example.oopjavagroupproject.data.FacultyDao;
import org.example.oopjavagroupproject.data.RoomDao;
import org.example.oopjavagroupproject.data.StudentDao;
import org.example.oopjavagroupproject.model.Contract;
import org.example.oopjavagroupproject.model.Faculty;
import org.example.oopjavagroupproject.model.Room;
import org.example.oopjavagroupproject.model.Student;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The main controller for the application's user interface.
 * This class manages all UI components, handles user events, and coordinates
 * data operations by interacting with the various DAO classes.
 * It is responsible for the logic of all tabs: Students, Faculties, Rooms, and Contracts.
 *
 * @author Bohdan Dmytrenko, Bohdan Ruban, Olha Sribna
 * @version 1.0
 */
public class HelloController {

    //<editor-fold desc="DAOs">
    private final StudentDao studentDao = new StudentDao();
    private final FacultyDao facultyDao = new FacultyDao();
    private final RoomDao roomDao = new RoomDao();
    private final ContractDao contractDao = new ContractDao();
    //</editor-fold>

    //<editor-fold desc="Master Lists">
    /** Master list of all students, used as a source for tables and filters. */
    private ObservableList<Student> masterStudentList;
    /** Master list of all faculties. */
    private ObservableList<Faculty> masterFacultyList;
    /** Master list of all rooms. */
    private ObservableList<Room> masterRoomList;
    /** Master list of all contracts. */
    private ObservableList<Contract> masterContractList;
    //</editor-fold>

    //<editor-fold desc="Main FXML Fields">
    @FXML private TabPane mainTabPane;
    @FXML private Tab studentsTab;
    @FXML private Tab facultiesTab;
    @FXML private Tab roomsTab;
    @FXML private Tab contractsTab;
    @FXML private ComboBox<String> themeComboBox;
    @FXML private ComboBox<String> languageComboBox;
    //</editor-fold>

    //<editor-fold desc="Student FXML Fields">
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Integer> idColumn;
    @FXML private TableColumn<Student, String> fullNameColumn;
    @FXML private TableColumn<Student, String> genderColumn;
    @FXML private TableColumn<Student, String> phoneColumn;
    @FXML private TableColumn<Student, String> facultyColumn;
    @FXML private TextField searchNameField;
    @FXML private ComboBox<Faculty> searchFacultyComboBox;
    @FXML private TextField fullNameField;
    @FXML private ComboBox<String> genderComboBox;
    @FXML private TextField phoneField;
    @FXML private ComboBox<Faculty> facultyComboBox;
    //</editor-fold>

    //<editor-fold desc="Faculty FXML Fields">
    @FXML private TableView<Faculty> facultyTableView;
    @FXML private TableColumn<Faculty, Integer> facultyIdColumn;
    @FXML private TableColumn<Faculty, String> facultyNameColumn;
    @FXML private TextField facultyNameField;
    //</editor-fold>

    //<editor-fold desc="Room FXML Fields">
    @FXML private TableView<Room> roomTableView;
    @FXML private TableColumn<Room, Integer> roomIdColumn;
    @FXML private TableColumn<Room, String> roomNumberColumn;
    @FXML private TableColumn<Room, Integer> roomCapacityColumn;
    @FXML private TableColumn<Room, BigDecimal> roomPriceColumn;
    @FXML private TextField roomNumberField;
    @FXML private TextField roomCapacityField;
    @FXML private TextField roomPriceField;
    //</editor-fold>

    //<editor-fold desc="Contract FXML Fields">
    @FXML private TableView<Contract> contractTableView;
    @FXML private TableColumn<Contract, Integer> contractIdColumn;
    @FXML private TableColumn<Contract, String> contractStudentColumn;
    @FXML private TableColumn<Contract, String> contractRoomColumn;
    @FXML private TableColumn<Contract, LocalDate> contractStartColumn;
    @FXML private TableColumn<Contract, LocalDate> contractEndColumn;
    @FXML private TableColumn<Contract, String> contractStatusColumn;
    @FXML private ComboBox<Student> contractStudentComboBox;
    @FXML private ComboBox<Room> contractRoomComboBox;
    @FXML private DatePicker contractStartDatePicker;
    @FXML private DatePicker contractEndDatePicker;
    @FXML private ComboBox<String> contractStatusComboBox;
    //</editor-fold>

    @FXML
    private ResourceBundle resources;
    private String darkThemeUrl;
    private String lightThemeUrl;

    /**
     * Initializes the controller class. This method is automatically called
     * after the FXML file has been loaded. It sets up all the tabs with their
     * respective data and configurations.
     */
    @FXML
    public void initialize() {
        setupHomeTab();
        setupStudentTab();
        setupFacultyTab();
        setupRoomTab();
        setupContractTab();
    }

    //<editor-fold desc="Navigation Methods">
    /** Navigates to the Students tab. */
    @FXML private void goToStudentsTab() { mainTabPane.getSelectionModel().select(studentsTab); }
    /** Navigates to the Faculties tab. */
    @FXML private void goToFacultiesTab() { mainTabPane.getSelectionModel().select(facultiesTab); }
    /** Navigates to the Rooms tab. */
    @FXML private void goToRoomsTab() { mainTabPane.getSelectionModel().select(roomsTab); }
    /** Navigates to the Contracts tab. */
    @FXML private void goToContractsTab() { mainTabPane.getSelectionModel().select(contractsTab); }
    //</editor-fold>

    //<editor-fold desc="Setup Methods">
    /**
     * Sets up the Home tab with theme and language selection functionality.
     */
    private void setupHomeTab() {
        darkThemeUrl = getClass().getResource("styles.css").toExternalForm();
        lightThemeUrl = getClass().getResource("light-theme.css").toExternalForm();

        themeComboBox.setItems(FXCollections.observableArrayList(resources.getString("home.theme.dark"), resources.getString("home.theme.light")));
        themeComboBox.getSelectionModel().select(resources.getString("home.theme.dark")); // Default theme
        themeComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                mainTabPane.getScene().getStylesheets().remove(darkThemeUrl);
                mainTabPane.getScene().getStylesheets().remove(lightThemeUrl);
                if (newVal.equals(resources.getString("home.theme.dark"))) {
                    mainTabPane.getScene().getStylesheets().add(darkThemeUrl);
                } else {
                    mainTabPane.getScene().getStylesheets().add(lightThemeUrl);
                }
            }
        });

        languageComboBox.setItems(FXCollections.observableArrayList("Українська", "English"));
        if (resources.getLocale().getLanguage().equals("uk")) {
            languageComboBox.getSelectionModel().select("Українська");
        } else {
            languageComboBox.getSelectionModel().select("English");
        }
        languageComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && (oldVal == null || !oldVal.equals(newVal))) {
                Locale newLocale = newVal.equals("Українська") ? new Locale("uk", "UA") : new Locale("en", "US");
                try {
                    HelloApplication.loadScene(newLocale);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Sets up the Students tab, including table columns, combo boxes, and event listeners.
     */
    private void setupStudentTab() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        facultyColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFaculty().getName()));
        genderComboBox.setItems(FXCollections.observableArrayList(resources.getString("gender.male"), resources.getString("gender.female")));
        loadStudentData();
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) populateStudentDetails(newSelection); else clearInputFields();
        });
    }

    /**
     * Sets up the Faculties tab, including table columns and event listeners.
     */
    private void setupFacultyTab() {
        facultyIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        facultyNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        loadFacultyData();
        facultyTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) facultyNameField.setText(newSelection.getName()); else facultyNameField.clear();
        });
    }

    /**
     * Sets up the Rooms tab, including table columns and event listeners.
     */
    private void setupRoomTab() {
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        loadRoomData();
        roomTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) populateRoomDetails(newSelection); else clearRoomFields();
        });
    }

    /**
     * Sets up the Contracts tab, including table columns, combo boxes, and event listeners.
     */
    private void setupContractTab() {
        contractIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        contractStudentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent().getFullName()));
        contractRoomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomNumber()));
        contractStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        contractEndColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        contractStatusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        contractStatusComboBox.setItems(FXCollections.observableArrayList("ACTIVE", "INACTIVE", "EXPIRED"));
        loadContractData();
        contractTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) populateContractDetails(newSelection); else clearContractFields();
        });
    }
    //</editor-fold>

    //<editor-fold desc="Load Data Methods">
    /**
     * Loads or reloads student data from the database and populates the student table and combo boxes.
     */
    private void loadStudentData() {
        masterStudentList = FXCollections.observableArrayList(studentDao.getAllStudents());
        studentTable.setItems(masterStudentList);
        contractStudentComboBox.setItems(masterStudentList);
    }

    /**
     * Loads or reloads faculty data from the database and populates the faculty table and combo boxes.
     */
    private void loadFacultyData() {
        masterFacultyList = FXCollections.observableArrayList(facultyDao.getAllFaculties());
        facultyTableView.setItems(masterFacultyList);
        ObservableList<Faculty> studentFaculties = FXCollections.observableArrayList(masterFacultyList);
        facultyComboBox.setItems(studentFaculties);
        ObservableList<Faculty> searchFaculties = FXCollections.observableArrayList(masterFacultyList);
        searchFaculties.add(0, new Faculty(0, resources.getString("all.faculties")));
        searchFacultyComboBox.setItems(searchFaculties);
    }

    /**
     * Loads or reloads room data from the database and populates the room table and combo boxes.
     */
    private void loadRoomData() {
        masterRoomList = FXCollections.observableArrayList(roomDao.getAllRooms());
        roomTableView.setItems(masterRoomList);
        contractRoomComboBox.setItems(masterRoomList);
    }

    /**
     * Loads or reloads contract data from the database and populates the contract table.
     */
    private void loadContractData() {
        masterContractList = FXCollections.observableArrayList(contractDao.getAllContracts());
        contractTableView.setItems(masterContractList);
    }
    //</editor-fold>

    //<editor-fold desc="Student Logic">
    /**
     * Filters the student table based on the text in the search field and the selected faculty.
     */
    @FXML private void searchStudents() {
        String searchName = searchNameField.getText().toLowerCase();
        Faculty searchFaculty = searchFacultyComboBox.getValue();
        FilteredList<Student> filteredData = new FilteredList<>(masterStudentList, s -> true);
        filteredData.setPredicate(student -> {
            boolean nameMatch = searchName.isEmpty() || student.getFullName().toLowerCase().contains(searchName);
            boolean facultyMatch = searchFaculty == null || searchFaculty.getId() == 0 || student.getFaculty().getId() == searchFaculty.getId();
            return nameMatch && facultyMatch;
        });
        studentTable.setItems(filteredData);
    }

    /**
     * Clears the student search filters and restores the full student list.
     */
    @FXML private void clearSearch() {
        searchNameField.clear();
        searchFacultyComboBox.getSelectionModel().clearSelection();
        studentTable.setItems(masterStudentList);
    }

    /**
     * Handles the "Add Student" button action. Validates input and adds a new student to the database.
     */
    @FXML private void addStudent() {
        if (!validateStudentInput()) return;
        Student newStudent = new Student(fullNameField.getText(), genderComboBox.getValue(), phoneField.getText(), facultyComboBox.getValue());
        studentDao.addStudent(newStudent);
        loadStudentData();
        clearInputFields();
    }

    /**
     * Handles the "Update Student" button action. Validates input and updates the selected student's data.
     */
    @FXML private void updateStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) { showAlert(Alert.AlertType.WARNING, resources.getString("alert.warning.title"), resources.getString("alert.student.not_selected_update")); return; }
        if (!validateStudentInput()) return;
        selectedStudent.setFullName(fullNameField.getText());
        selectedStudent.setGender(genderComboBox.getValue());
        selectedStudent.setPhone(phoneField.getText());
        selectedStudent.setFaculty(facultyComboBox.getValue());
        studentDao.updateStudent(selectedStudent);
        int selectedIndex = studentTable.getSelectionModel().getSelectedIndex();
        loadStudentData();
        studentTable.getSelectionModel().select(selectedIndex);
    }

    /**
     * Handles the "Delete Student" button action. Confirms with the user before deleting the selected student.
     * Prevents deletion if the student is part of an active contract.
     */
    @FXML private void deleteStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) { showAlert(Alert.AlertType.WARNING, resources.getString("alert.warning.title"), resources.getString("alert.student.not_selected_delete")); return; }
        boolean inUse = masterContractList.stream().anyMatch(c -> c.getStudentId() == selectedStudent.getId());
        if (inUse) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.student.in_use")); return; }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resources.getString("alert.confirm.title"));
        alert.setHeaderText(String.format(resources.getString("alert.student.delete.header"), selectedStudent.getFullName()));
        alert.setContentText(resources.getString("alert.student.delete.content"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            studentDao.deleteStudent(selectedStudent.getId());
            loadStudentData();
            clearInputFields();
        }
    }

    /**
     * Validates the input fields for adding or updating a student.
     * @return true if the input is valid, false otherwise.
     */
    private boolean validateStudentInput() {
        if (fullNameField.getText() == null || fullNameField.getText().trim().isEmpty()) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.name.empty")); return false; }
        if (genderComboBox.getValue() == null) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.gender.empty")); return false; }
        if (facultyComboBox.getValue() == null || facultyComboBox.getValue().getId() == 0) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.faculty.empty")); return false; }
        String phone = phoneField.getText();
        if (phone != null && !phone.trim().isEmpty() && !phone.matches("[\\d+()-]*")) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.phone.invalid")); return false; }
        return true;
    }

    /**
     * Populates the input fields with the details of the selected student.
     * @param student The student whose details are to be displayed.
     */
    private void populateStudentDetails(Student student) {
        fullNameField.setText(student.getFullName());
        genderComboBox.setValue(student.getGender());
        phoneField.setText(student.getPhone());
        facultyComboBox.getItems().stream().filter(f -> f.getId() == student.getFacultyId()).findFirst().ifPresent(facultyComboBox::setValue);
    }

    /**
     * Clears all input fields in the student form.
     */
    @FXML private void clearInputFields() {
        fullNameField.clear();
        genderComboBox.getSelectionModel().clearSelection();
        phoneField.clear();
        facultyComboBox.getSelectionModel().clearSelection();
        studentTable.getSelectionModel().clearSelection();
    }
    //</editor-fold>

    //<editor-fold desc="Faculty Logic">
    /**
     * Handles the "Add Faculty" button action. Adds a new faculty to the database.
     */
    @FXML private void addFaculty() {
        String name = facultyNameField.getText();
        if (name == null || name.trim().isEmpty()) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.faculty.name.empty")); return; }
        facultyDao.addFaculty(new Faculty(0, name));
        loadFacultyData();
        facultyNameField.clear();
    }

    /**
     * Handles the "Update Faculty" button action. Updates the selected faculty's name.
     */
    @FXML private void updateFaculty() {
        Faculty selectedFaculty = facultyTableView.getSelectionModel().getSelectedItem();
        if (selectedFaculty == null) { showAlert(Alert.AlertType.WARNING, resources.getString("alert.warning.title"), resources.getString("alert.faculty.not_selected_update")); return; }
        String newName = facultyNameField.getText();
        if (newName == null || newName.trim().isEmpty()) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.faculty.name.empty")); return; }
        selectedFaculty.setName(newName);
        facultyDao.updateFaculty(selectedFaculty);
        loadFacultyData();
    }

    /**
     * Handles the "Delete Faculty" button action. Prevents deletion if the faculty is in use by students.
     */
    @FXML private void deleteFaculty() {
        Faculty selectedFaculty = facultyTableView.getSelectionModel().getSelectedItem();
        if (selectedFaculty == null) { showAlert(Alert.AlertType.WARNING, resources.getString("alert.warning.title"), resources.getString("alert.faculty.not_selected_delete")); return; }
        boolean inUse = masterStudentList.stream().anyMatch(s -> s.getFacultyId() == selectedFaculty.getId());
        if (inUse) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.faculty.in_use")); return; }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resources.getString("alert.confirm.title"));
        alert.setHeaderText(String.format(resources.getString("alert.faculty.delete.header"), selectedFaculty.getName()));
        alert.setContentText(resources.getString("alert.faculty.delete.content"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            facultyDao.deleteFaculty(selectedFaculty.getId());
            loadFacultyData();
        }
    }

    /**
     * Clears the faculty name input field.
     */
    @FXML private void clearFacultyField() {
        facultyNameField.clear();
        facultyTableView.getSelectionModel().clearSelection();
    }
    //</editor-fold>

    //<editor-fold desc="Room Logic">
    /**
     * Handles the "Add Room" button action. Adds a new room to the database.
     */
    @FXML private void addRoom() {
        if (!validateRoomInput()) return;
        Room newRoom = new Room(0, roomNumberField.getText(), Integer.parseInt(roomCapacityField.getText()), new BigDecimal(roomPriceField.getText()));
        roomDao.addRoom(newRoom);
        loadRoomData();
        clearRoomFields();
    }

    /**
     * Handles the "Update Room" button action. Updates the selected room's details.
     */
    @FXML private void updateRoom() {
        Room selectedRoom = roomTableView.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) { showAlert(Alert.AlertType.WARNING, resources.getString("alert.warning.title"), resources.getString("alert.room.not_selected_update")); return; }
        if (!validateRoomInput()) return;
        selectedRoom.setRoomNumber(roomNumberField.getText());
        selectedRoom.setCapacity(Integer.parseInt(roomCapacityField.getText()));
        selectedRoom.setPrice(new BigDecimal(roomPriceField.getText()));
        roomDao.updateRoom(selectedRoom);
        loadRoomData();
    }

    /**
     * Handles the "Delete Room" button action. Prevents deletion if the room is part of an active contract.
     */
    @FXML private void deleteRoom() {
        Room selectedRoom = roomTableView.getSelectionModel().getSelectedItem();
        if (selectedRoom == null) { showAlert(Alert.AlertType.WARNING, resources.getString("alert.warning.title"), resources.getString("alert.room.not_selected_delete")); return; }
        boolean inUse = masterContractList.stream().anyMatch(c -> c.getRoomId() == selectedRoom.getId());
        if (inUse) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.room.in_use")); return; }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resources.getString("alert.confirm.title"));
        alert.setHeaderText(String.format(resources.getString("alert.room.delete.header"), selectedRoom.getRoomNumber()));
        alert.setContentText(resources.getString("alert.room.delete.content"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            roomDao.deleteRoom(selectedRoom.getId());
            loadRoomData();
        }
    }

    /**
     * Clears all input fields in the room form.
     */
    @FXML private void clearRoomFields() {
        roomNumberField.clear();
        roomCapacityField.clear();
        roomPriceField.clear();
        roomTableView.getSelectionModel().clearSelection();
    }

    /**
     * Validates the input fields for adding or updating a room.
     * @return true if the input is valid, false otherwise.
     */
    private boolean validateRoomInput() {
        String number = roomNumberField.getText();
        String capacity = roomCapacityField.getText();
        String price = roomPriceField.getText();
        if (number == null || number.trim().isEmpty()) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.room.number.empty")); return false; }
        try {
            int cap = Integer.parseInt(capacity);
            if (cap <= 0) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.capacity.invalid")); return false; }
        } catch (NumberFormatException e) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.capacity.invalid")); return false; }
        try {
            BigDecimal pr = new BigDecimal(price);
            if (pr.compareTo(BigDecimal.ZERO) < 0) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.price.invalid")); return false; }
        } catch (NumberFormatException e) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.price.invalid")); return false; }
        return true;
    }

    /**
     * Populates the input fields with the details of the selected room.
     * @param room The room whose details are to be displayed.
     */
    private void populateRoomDetails(Room room) {
        roomNumberField.setText(room.getRoomNumber());
        roomCapacityField.setText(String.valueOf(room.getCapacity()));
        roomPriceField.setText(room.getPrice().toPlainString());
    }
    //</editor-fold>

    //<editor-fold desc="Contract Logic">
    /**
     * Handles the "Add Contract" button action. Adds a new contract to the database.
     */
    @FXML private void addContract() {
        if (!validateContractInput()) return;
        Contract newContract = new Contract(contractStudentComboBox.getValue(), contractRoomComboBox.getValue(), contractStartDatePicker.getValue(), contractEndDatePicker.getValue(), contractStatusComboBox.getValue());
        contractDao.addContract(newContract);
        loadContractData();
        clearContractFields();
    }

    /**
     * Handles the "Update Contract" button action. Updates the selected contract's details.
     */
    @FXML private void updateContract() {
        Contract selectedContract = contractTableView.getSelectionModel().getSelectedItem();
        if (selectedContract == null) { showAlert(Alert.AlertType.WARNING, resources.getString("alert.warning.title"), resources.getString("alert.contract.not_selected_update")); return; }
        if (!validateContractInput()) return;
        selectedContract.setStudent(contractStudentComboBox.getValue());
        selectedContract.setRoom(contractRoomComboBox.getValue());
        selectedContract.setStartDate(contractStartDatePicker.getValue());
        selectedContract.setEndDate(contractEndDatePicker.getValue());
        selectedContract.setStatus(contractStatusComboBox.getValue());
        contractDao.updateContract(selectedContract);
        loadContractData();
    }

    /**
     * Handles the "Delete Contract" button action. Confirms with the user before deleting.
     */
    @FXML private void deleteContract() {
        Contract selectedContract = contractTableView.getSelectionModel().getSelectedItem();
        if (selectedContract == null) { showAlert(Alert.AlertType.WARNING, resources.getString("alert.warning.title"), resources.getString("alert.contract.not_selected_delete")); return; }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(resources.getString("alert.confirm.title"));
        alert.setHeaderText(String.format(resources.getString("alert.contract.delete.header"), selectedContract.getStudent().getFullName()));
        alert.setContentText(resources.getString("alert.contract.delete.content"));
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            contractDao.deleteContract(selectedContract.getId());
            loadContractData();
        }
    }

    /**
     * Clears all input fields in the contract form.
     */
    @FXML private void clearContractFields() {
        contractStudentComboBox.getSelectionModel().clearSelection();
        contractRoomComboBox.getSelectionModel().clearSelection();
        contractStartDatePicker.setValue(null);
        contractEndDatePicker.setValue(null);
        contractStatusComboBox.getSelectionModel().clearSelection();
        contractTableView.getSelectionModel().clearSelection();
    }

    /**
     * Validates the input fields for adding or updating a contract.
     * @return true if the input is valid, false otherwise.
     */
    private boolean validateContractInput() {
        if (contractStudentComboBox.getValue() == null) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.student.not_selected")); return false; }
        if (contractRoomComboBox.getValue() == null) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.room.not_selected")); return false; }
        if (contractStartDatePicker.getValue() == null) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.date.start.empty")); return false; }
        if (contractEndDatePicker.getValue() == null) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.date.end.empty")); return false; }
        if (contractEndDatePicker.getValue().isBefore(contractStartDatePicker.getValue())) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.date.invalid")); return false; }
        if (contractStatusComboBox.getValue() == null) { showAlert(Alert.AlertType.ERROR, resources.getString("alert.error.title"), resources.getString("alert.status.empty")); return false; }
        return true;
    }

    /**
     * Populates the input fields with the details of the selected contract.
     * @param contract The contract whose details are to be displayed.
     */
    private void populateContractDetails(Contract contract) {
        contractStudentComboBox.getItems().stream().filter(s -> s.getId() == contract.getStudentId()).findFirst().ifPresent(contractStudentComboBox::setValue);
        contractRoomComboBox.getItems().stream().filter(r -> r.getId() == contract.getRoomId()).findFirst().ifPresent(contractRoomComboBox::setValue);
        contractStartDatePicker.setValue(contract.getStartDate());
        contractEndDatePicker.setValue(contract.getEndDate());
        contractStatusComboBox.setValue(contract.getStatus());
    }
    //</editor-fold>

    /**
     * A utility method to show an alert dialog to the user.
     * @param type    The type of alert (e.g., ERROR, WARNING, CONFIRMATION).
     * @param title   The title of the alert window.
     * @param message The message to display in the alert.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
