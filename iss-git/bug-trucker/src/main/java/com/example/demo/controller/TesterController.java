package com.example.demo.controller;

import com.example.demo.model.Bug;
import com.example.demo.model.BugStatus;
import com.example.demo.model.Tester;
import com.example.demo.service.Service;
import com.example.demo.utils.event.BugEntityChangeEvent;
import com.example.demo.utils.observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;

public class TesterController implements Observer<BugEntityChangeEvent> {
    @FXML
    private TableView<Bug> tableViewBugs;
    @FXML
    private TableColumn<Bug, Integer> idColumnTester;
    @FXML
    private TableColumn<Bug, String> nameColumnTester;
    @FXML
    private TableColumn<Bug, String> descColumnTester;
    @FXML
    private TableColumn<Bug, BugStatus> statusColumnTester;
    @FXML
    private TextField nameTFTester;
    @FXML
    private TextField descTFTester;
    private Service service;
    private Tester loggedInTester;
    private final ObservableList<Bug> bugsModel = FXCollections.observableArrayList();

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initModel();
    }
    public void setLoggedInTester(Tester tester) {this.loggedInTester = tester;}
    @FXML
    public void initialize() {
        idColumnTester.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumnTester.setCellValueFactory(new PropertyValueFactory<>("name"));
        descColumnTester.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusColumnTester.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableViewBugs.setItems(bugsModel);
    }

    public void initModel() {
        bugsModel.setAll(service.getAllBugs());
    }

    @FXML
    private void onClickRecordBug(ActionEvent actionEvent) throws IOException {
        String bugName = nameTFTester.getText();
        String bugDescription = descTFTester.getText();
        service.save(bugName, bugDescription);
    }

    @Override
    public void update(BugEntityChangeEvent bugEntityChangeEvent) {
        initModel();
    }
}
