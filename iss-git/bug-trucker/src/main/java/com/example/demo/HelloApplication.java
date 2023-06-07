package com.example.demo;

import com.example.demo.controller.LoginController;
import com.example.demo.repo.bugs.BugsRepoDB;
import com.example.demo.repo.programmers.ProgrammersRepoDB;
import com.example.demo.repo.Tester.TesterRepository;
import com.example.demo.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Properties props=new Properties();
        try {
            props.load(new FileReader("C:\\Users\\TC\\Desktop\\ISS-ubb-master\\bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 475, 300);
        stage.setTitle("Hello!");
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(new Service(new BugsRepoDB(), new ProgrammersRepoDB(), new TesterRepository()));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}