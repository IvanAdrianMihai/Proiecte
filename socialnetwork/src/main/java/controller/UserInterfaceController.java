package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import service.SuperService;
import javafx.scene.control.*;
import util.events.Event;
import util.observer.Observer;

import java.io.*;
import java.net.URL;

public class UserInterfaceController implements Observer<Event> {
    SuperService service;

    @FXML
    ImageView image;
    @FXML
    Button logOut;
    @FXML
    Label name;
    @FXML
    AnchorPane content;
    @FXML
    AnchorPane content1;

    public void setService(SuperService service) {
        this.service = service;
        service.addObserver(this);
        Image img = new Image(new ByteArrayInputStream(service.getLogat().getImagineProfil()));
        image.setImage(img);
        image.setPreserveRatio(true);
        name.setText(service.getLogat().getLastName() + " " + service.getLogat().getFirstName());
    }

    public void logoutOfThis(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/login.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            LoginController loginController = loader.getController();
            loginController.setService(service);
            content.getChildren().setAll(root);
            service.removeObserver(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void profilShow(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/profile.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            ProfileController profileController = loader.getController();
            profileController.setService(service, content);
            content1.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToPrieteni(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/prieteni.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            PrieteniController prieteniController = loader.getController();
            prieteniController.setService(service);
            content1.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openMesaje(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/mesaje.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            MesajeController mesajeController = loader.getController();
            mesajeController.setService(service);
            content1.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openRapoarte(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/rapoarte.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            RapoarteController rapoarteController = loader.getController();
            rapoarteController.setService(service);
            content1.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToEvenimente(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/evenimente.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            EvenimenteController evenimenteController = loader.getController();
            evenimenteController.setService(service);
            content1.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Event event) {
        if(event==Event.UPDATE_CONT){
            service.updateUtilizator();
            Image img = new Image(new ByteArrayInputStream(service.getLogat().getImagineProfil()));
            image.setImage(img);
            image.setPreserveRatio(true);
            name.setText(service.getLogat().getLastName() + " " + service.getLogat().getFirstName());
        }
    }
}
