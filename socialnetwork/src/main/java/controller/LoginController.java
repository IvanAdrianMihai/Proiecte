package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import domain.validators.ServiceException;
import service.SuperService;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LoginController {
    private SuperService service;
    private Tooltip forMail = new Tooltip("Introduceti mailul");
    private Tooltip forPassowrd = new Tooltip("Introduceti parola");
    private Tooltip forLogin = new Tooltip("Intrati in aplicatie");

    @FXML
    TextField mail;
    @FXML
    PasswordField parola;
    @FXML
    CheckBox showPassword;
    @FXML
    Button login;
    @FXML
    Label newPassword;
    @FXML
    Label newAccount;
    @FXML
    Label labelShow;
    @FXML
    AnchorPane content;
    @FXML
    ImageView image;

    public void setService(SuperService service) {
        this.service = service;
        mail.setTooltip(forMail);
        parola.setTooltip(forPassowrd);
        login.setTooltip(forLogin);
        image.setImage(new Image("file:///" + "C:\\Users\\Admin\\Desktop\\Semestrul I\\" +
                "Metode avansate de programare\\Laborator\\Proiect Extins\\proiect\\src\\main\\" +
                "resources\\images\\earth2.gif"));
        image.setPreserveRatio(true);
    }

    public void changeShow(MouseEvent mouseEvent) {
        if (showPassword.isSelected())
            labelShow.setText("Parola: " + parola.getText());
        else
            labelShow.setText("");
    }


    public void logareInAplicatie(MouseEvent mouseEvent) {
        Long asteapta = -1L;
        if (service.getStartTimeToWait() != null)
            asteapta = 30 - (System.currentTimeMillis() - service.getStartTimeToWait()) / 1000;
        if (service.getStartTimeToWait() == null || asteapta <= 0) {
            service.setStartTimeToWait(null);
            try {
                service.contLogIn(mail.getText().trim(), parola.getText());
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(new URL(getClass().getResource("/views/userInterface.fxml").toString().replace("%20", " ")));

                    AnchorPane root = (AnchorPane) loader.load();
                    UserInterfaceController userInterface1Controller = loader.getController();
                    userInterface1Controller.setService(service);
                    content.getChildren().setAll(root);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (ServiceException e) {
                MessageAlert.showMessage(null, Alert.AlertType.ERROR, "Eroare", e.getMessage());
            }
        } else {
            MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Asteptare", "Inca nu va puteti loga.\n" +
                    "Mai ai de asteptat " + asteapta + " secunde");
        }
    }

    public void windowNewPassword(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/recuperareParola.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            RecuperareParolaController recuperareParolaController = loader.getController();
            recuperareParolaController.setService(service);
            content.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void windowNewAcount(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/newAccount.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            NewAccountController newAccountController = loader.getController();
            newAccountController.setService(service);
            content.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void introducereDate(KeyEvent keyEvent) {
        if (showPassword.isSelected())
            labelShow.setText("Parola: " + parola.getText());
    }
}
