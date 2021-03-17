package controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import domain.Tuple;
import domain.validators.ServiceException;
import service.SuperService;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class NewAccountController {
    private SuperService service;
    private Tooltip forNume=new Tooltip("Introduceti numele");
    private Tooltip forPrenume=new Tooltip("Introduceti prenumele");
    private Tooltip forMail=new Tooltip("Setati mailul pentru contul dumneavoastra");
    private Tooltip forParola=new Tooltip("Introduceti parola contului \n Poate contine litere mici/mari/cifre");
    private Tooltip forCParola=new Tooltip("Introduceti din nou parola setata");
    private Tooltip forBack=new Tooltip("Inapoi la fereastra de logare");
    private Tooltip forCreate=new Tooltip("Creati noul cont");

    @FXML
    TextField nume;
    @FXML
    TextField prenume;
    @FXML
    TextField mail;
    @FXML
    PasswordField parola;
    @FXML
    PasswordField cparola;
    @FXML
    CheckBox check1;
    @FXML
    CheckBox check2;
    @FXML
    Label show1;
    @FXML
    Label show2;
    @FXML
    Label complexitate;
    @FXML
    Rectangle sh1;
    @FXML
    Rectangle sh2;
    @FXML
    Rectangle sh3;
    @FXML
    Button creare;
    @FXML
    Button back;
    @FXML
    AnchorPane content;

    public void setService(SuperService service) {
        this.service = service;
        nume.setTooltip(forNume);
        prenume.setTooltip(forPrenume);
        mail.setTooltip(forMail);
        parola.setTooltip(forParola);
        cparola.setTooltip(forCParola);
        back.setTooltip(forBack);
        creare.setTooltip(forCreate);
    }

    private void setEmptyFields() {
        nume.setText("");
        prenume.setText("");
        mail.setText("");
        parola.setText("");
        cparola.setText("");
        check1.setSelected(false);
        check2.setSelected(false);
        complexitate.setText("Complexitate parola");
        sh1.setFill(Paint.valueOf("#dadada"));
        sh2.setFill(Paint.valueOf("#dadada"));
        sh3.setFill(Paint.valueOf("#dadada"));
    }

    public void typedSomething1(KeyEvent keyEvent) {
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

    public void typedSomething2(KeyEvent keyEvent) {
        if (check2.isSelected())
            show2.setText("Parola: " + cparola.getText());
    }

    public void showPassword2(MouseEvent mouseEvent) {
        if (check2.isSelected())
            show2.setText("Parola: " + cparola.getText());
        else
            show2.setText("");
    }

    public void showPassword1(MouseEvent mouseEvent) {
        if (check1.isSelected())
            show1.setText("Parola: " + parola.getText());
        else
            show1.setText("");
    }

    public void createIt(MouseEvent mouseEvent) {
        if (nume.getText().isEmpty() || prenume.getText().isEmpty() || mail.getText().isEmpty() ||
                parola.getText().isEmpty() || cparola.getText().isEmpty())
            MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Campuri", "Nu sunt toate campurile completate");
        else if (cparola.getText().compareTo(parola.getText()) != 0)
            MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Parola", "Parola pentru confirmare nu este aceeasi!");
        else {
            Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
                @Override
                protected Tuple<Integer, String> call() throws Exception {
                    try {
                        service.addCont(mail.getText().trim(), parola.getText(), prenume.getText().trim(), nume.getText().trim());
                        return new Tuple<Integer, String>(0, "Cont adaugat cu succes!\n Bine ai venit!");
                    } catch (ServiceException e) {
                        return new Tuple<Integer, String>(1, e.getMessage());
                    }
                }
            };
            myTask.setOnSucceeded(e -> {
                try {
                    setEmptyFields();
                    if (myTask.get().getLeft() == 0)
                        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmare", myTask.get().getRight());
                    else
                        MessageAlert.showErrorMessage(null, myTask.get().getRight());
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                } catch (ExecutionException executionException) {
                    executionException.printStackTrace();
                }
            });
            Thread thread = new Thread(myTask);
            thread.start();
        }
    }

    public void backToLogin(MouseEvent mouseEvent) {
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
    }
}
