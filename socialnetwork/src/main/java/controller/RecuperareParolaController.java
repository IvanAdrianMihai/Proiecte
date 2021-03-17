package controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import domain.Tuple;
import domain.validators.ServiceException;
import service.SuperService;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class RecuperareParolaController {
    SuperService service;
    private Tooltip forMail=new Tooltip("Introduceti mailul dumneavoastra");
    private Tooltip forMail2=new Tooltip("Introduceti mailul pe care ati primit codul");
    private Tooltip forCode=new Tooltip("Introduceti codul primit");
    private Tooltip forBack=new Tooltip("Inapoi la fereastra de logare");

    @FXML
    TextField mail;
    @FXML
    TextField mail2;
    @FXML
    TextField cod;
    @FXML
    Button back;
    @FXML
    Button trimite;
    @FXML
    Button resetare;
    @FXML
    AnchorPane content;

    public void setService(SuperService service) {
        this.service = service;
        mail.setTooltip(forMail);
        mail2.setTooltip(forMail2);
        cod.setTooltip(forCode);
        back.setTooltip(forBack);
    }


    public void trimiteCodul(MouseEvent mouseEvent) {
        Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
            @Override
            protected Tuple<Integer, String> call() throws Exception {
                try{
                    service.recuperareParola(mail.getText().trim());
                    return new Tuple<Integer, String>(0, "Cod trimis cu succes!\nVerifica-ti mailul!");
                }catch (ServiceException e){
                    return new Tuple<Integer, String>(1, e.getMessage());
                }
            }
        };
        myTask.setOnSucceeded(e -> {
            try{
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

    public void goBack(MouseEvent mouseEvent) {
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

    public void reseteazaParola(MouseEvent mouseEvent) {
        if(mail2.getText().isEmpty()||cod.getText().isEmpty())
            MessageAlert.showMessage(null, Alert.AlertType.WARNING,"Campuri","Campurile nu sunt completate");
        else
        {
            try{
                if(service.verifyCod(mail2.getText().trim(),cod.getText()))
                {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(new URL(getClass().getResource("/views/resetareParola.fxml").toString().replace("%20", " ")));
                        AnchorPane root = (AnchorPane) loader.load();
                        ResetareParolaController resetareParolaController = loader.getController();
                        resetareParolaController.setService(service,mail2.getText().trim());
                        content.getChildren().setAll(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                    MessageAlert.showMessage(null, Alert.AlertType.WARNING,"Cod incorect!","Incearca din nou!");
            }catch (ServiceException e){
                MessageAlert.showMessage(null, Alert.AlertType.WARNING,"Warning",e.getMessage());
            }
        }
    }
}
