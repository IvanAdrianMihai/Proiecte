package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import domain.validators.ServiceException;
import service.SuperService;

import java.io.IOException;
import java.net.URL;

public class ResetareParolaController {
    private SuperService service;
    private String mail;
    private Tooltip forParola=new Tooltip("Introduceti noua parola");
    private Tooltip forParola2=new Tooltip("Reintroduceti noua parola");
    private Tooltip forResetare=new Tooltip("Confirmati update-ul parolei");

    @FXML
    PasswordField parola;
    @FXML
    PasswordField cparola;
    @FXML
    CheckBox check1;
    @FXML
    CheckBox check2;
    @FXML
    Button resetare;
    @FXML
    Label show1;
    @FXML
    Label show2;
    @FXML
    AnchorPane content;
    @FXML
    Rectangle sh1;
    @FXML
    Rectangle sh2;
    @FXML
    Rectangle sh3;
    @FXML
    Label complexitate;

    public void setService(SuperService service, String mail) {
        this.service = service;
        this.mail = mail;
        parola.setTooltip(forParola);
        cparola.setTooltip(forParola2);
        resetare.setTooltip(forResetare);
    }

    public void resetareParola(MouseEvent mouseEvent) {
        if (parola.getText().isEmpty() || cparola.getText().isEmpty())
            MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Campuri", "Campurile nu sunt completate!");
        else {
            if (parola.getText().compareTo(cparola.getText()) == 0) {
                try {
                    service.updateCont(mail, parola.getText());
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Succes!", "Resetarea parolei cu succes!");
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(new URL(getClass().getResource("/views/login.fxml").toString().replace("%20", " ")));
                        AnchorPane root = (AnchorPane) loader.load();
                        LoginController loginController = loader.getController();
                        loginController.setService(service);
                        content.getChildren().setAll(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (ServiceException e) {
                    MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Warning", e.getMessage());
                }
            } else
                MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Incompatibilitate", "Parola pentru confirmare nu este aceeasi!");
        }
    }

    public void afisareParola1(MouseEvent mouseEvent) {
        if (check1.isSelected())
            show1.setText("Parola: " + parola.getText());
        else
            show1.setText("");
    }

    public void afisareParola2(MouseEvent mouseEvent) {
        if (check2.isSelected())
            show2.setText("Parola: " + cparola.getText());
        else
            show2.setText("");
    }

    public void typeSomething1(KeyEvent keyEvent) {
        if (check1.isSelected())
            show1.setText("Parola: " + parola.getText());
        sh1.setFill(javafx.scene.paint.Color.valueOf("#ff7777"));

        if (parola.getText().isEmpty()) {
            complexitate.setText("Complexitate parola");
            sh1.setFill(Paint.valueOf("#dadada"));
            sh2.setFill(Paint.valueOf("#dadada"));
            sh3.setFill(Paint.valueOf("#dadada"));
        } else {
            char[] pass = parola.getText().toCharArray();
            int comp = 0;
            boolean mici = false, mari = false, cifre = false;
            for (int i = 0; i < pass.length; i++)
                if (pass[i] >= 'a' && pass[i] <= 'z')
                    mici = true;
                else if (pass[i] >= 'A' && pass[i] <= 'Z')
                    mari = true;
                else if (pass[i] >= '0' && pass[i] <= '9')
                    cifre = true;

            if (!parola.getText().isEmpty())
                comp = 1;
            if ((mici || mari || cifre) && parola.getText().length() > 4)
                comp = 2;
            if (mici && mari && cifre && parola.getText().length() >= 8)
                comp = 3;
            if (comp == 1) {
                complexitate.setText("Parola SLABA");
                sh1.setFill(Paint.valueOf("#ff7777"));
                sh2.setFill(Paint.valueOf("#dadada"));
                sh3.setFill(Paint.valueOf("#dadada"));
            } else if (comp == 2) {
                complexitate.setText("Parola MEDIE");
                sh1.setFill(Paint.valueOf("#f2ff78"));
                sh2.setFill(Paint.valueOf("#f2ff78"));
                sh3.setFill(Paint.valueOf("#dadada"));
            } else {
                complexitate.setText("Parola PUTERNICA");
                sh1.setFill(Paint.valueOf("#8eff78"));
                sh2.setFill(Paint.valueOf("#8eff78"));
                sh3.setFill(Paint.valueOf("#8eff78"));
            }
        }
    }

    public void typeSomething2(KeyEvent keyEvent) {
        if (check2.isSelected())
            show2.setText("Parola: " + cparola.getText());
    }
}
