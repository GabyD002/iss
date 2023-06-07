package com.example.demo.controller;

import com.example.demo.model.Programmer;
import com.example.demo.model.Tester;
import com.example.demo.service.Service;
import com.example.demo.utils.Response;
import com.example.demo.utils.LoginType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField usernameTF;
    @FXML
    private PasswordField pwdTF;

    private Service service;

    public void setService(Service service){
        this.service = service;
    }

    @FXML
    private void onClickLoginBtn(ActionEvent actionEvent) throws IOException {
        Response response = service.login(usernameTF.getText(), pwdTF.getText());
        System.out.println(response.getLoginType());
        if(response.getLoginType() == LoginType.ERROR){
            errorLabel.setText("Invalid credentials!");
        } else if(response.getLoginType() == LoginType.TESTER){
            Tester loggedInTester = response.getTester();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/tester-view.fxml"));
            Parent root = loader.load();
            TesterController testerController = loader.getController();
            testerController.setLoggedInTester(loggedInTester);
            testerController.setService(service);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.setTitle("Hello!");
            stage.show();

        } else if(response.getLoginType() == LoginType.PROGRAMMER){
            Programmer loggedInProgrammer = response.getProg();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/views/experiment.fxml"));
            Parent root = loader.load();
            ProgrammerController programmerController = loader.getController();
            programmerController.setLoggedInProgrammer(loggedInProgrammer);
            programmerController.setService(service);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 600, 400));
            stage.setTitle("Hello!");
            stage.show();
            closeWindow();
        }
    }

    private void closeWindow(){
        Stage thisStage = (Stage) loginButton.getScene().getWindow();
        thisStage.close();
    }
}