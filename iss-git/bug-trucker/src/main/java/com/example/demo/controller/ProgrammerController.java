package com.example.demo.controller;

import com.example.demo.model.Bug;
import com.example.demo.model.BugStatus;
import com.example.demo.model.Programmer;
import com.example.demo.service.Service;
import com.example.demo.utils.event.BugEntityChangeEvent;
import com.example.demo.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ProgrammerController implements Observer<BugEntityChangeEvent> {
    @FXML
    private TableView<Bug> tableViewBugsProg;
    @FXML
    private TableColumn<Bug, Integer> idColumnProg;
    @FXML
    private TableColumn<Bug, String> nameColumnProg;
    @FXML
    private TableColumn<Bug, String> descColumnProg;
    @FXML
    private TableColumn<Bug, BugStatus> statusColumnProg;
    private Service service;
    private Programmer loggedInProgrammer;
    private final ObservableList<Bug> bugsModel = FXCollections.observableArrayList();
    public void setLoggedInProgrammer(Programmer loggedInProgrammer) {
        this.loggedInProgrammer = loggedInProgrammer;
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }

    public void onClickSolveBug(ActionEvent actionEvent) {
        Bug selectedBug = tableViewBugsProg.getSelectionModel().getSelectedItem();
        if (selectedBug.getStatus() == BugStatus.SOLVED) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error!");
            errorAlert.setContentText("The bug has already been solved");
            errorAlert.showAndWait();
        } else {
            service.solveBug(selectedBug.getId());
        }
    }

    @Override
    public void update(BugEntityChangeEvent bugEntityChangeEvent) {
        initModel();
    }

    @FXML
    public void initialize() {
        idColumnProg.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnProg.setCellValueFactory(new PropertyValueFactory<>("name"));
        descColumnProg.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusColumnProg.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableViewBugsProg.setItems(bugsModel);
    }

    public void initModel() {
        bugsModel.setAll(service.getAllBugs());
    }
}
