package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import domain.Eveniment;
import domain.UtilizatorEveniment;
import domain.validators.ServiceException;
import service.SuperService;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;

import static domain.NotificationType.ON;

public class EvenimenParticipareController {
    SuperService service;
    Eveniment eveniment;
    ObservableList<UtilizatorEveniment> participanti = FXCollections.observableArrayList();

    @FXML
    AnchorPane content;
    @FXML
    Label dataEveniment;
    @FXML
    Label gazdaEveniment;
    @FXML
    Label denumireEveniment;
    @FXML
    Label labelinfo;
    @FXML
    TextArea descriereEveniment;
    @FXML
    CheckBox notificariOnOff;
    @FXML
    Button butonNuParticipa;

    @FXML
    TableView<UtilizatorEveniment> participantiTable;
    @FXML
    TableColumn<UtilizatorEveniment, ImageView> imageColumn;
    @FXML
    TableColumn<UtilizatorEveniment, String> numeColumn;
    @FXML
    TableColumn<UtilizatorEveniment, String> prenumeColumn;

    @FXML
    public void initialize() {
        imageColumn.setCellValueFactory(new PropertyValueFactory<UtilizatorEveniment, ImageView>("image"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<UtilizatorEveniment, String>("lastName"));
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<UtilizatorEveniment, String>("firstName"));
        participantiTable.setItems(participanti);
    }

    public void setService(SuperService service, Eveniment eveniment) {
        this.service = service;
        this.eveniment = eveniment;
        gazdaEveniment.setText("Gazda: " + eveniment.getGazdaForGui());
        dataEveniment.setText("Data: " + eveniment.getEvenimentDateForGui());
        denumireEveniment.setText(eveniment.getDenumire());
        descriereEveniment.setText(eveniment.getDescriere());
        descriereEveniment.setEditable(false);

        for(UtilizatorEveniment ue:eveniment.getParticipanti())
            if(ue.getId()==service.getLogat().getId()){
                if(ue.getNotificari()==ON)
                    notificariOnOff.setSelected(true);
                break;
            }
        if(LocalDateTime.now().isAfter(eveniment.getEvenimentDate())){
            notificariOnOff.setDisable(true);
            butonNuParticipa.setDisable(true);
            labelinfo.setVisible(false);
        }
        initParticipanti();
    }

    private void initParticipanti() {
        Task<Integer> myTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                participanti.setAll(eveniment.getParticipanti());
                return 1;
            }
        };
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void goBack(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/evenimente.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            EvenimenteController evenimenteController = loader.getController();
            evenimenteController.setService(service);
            evenimenteController.getMyPane().getSelectionModel().select(1);
            content.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void nuMaiParticipa(MouseEvent mouseEvent) {
        if(MessageAlert.yesNoCancelMessage("Dezabonare eveniment","Esti sigur ca vrei sa te dezabonezi de la eveniment?")==1)
        {
            Task<Integer> myTask = new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    try {
                        service.dezabonareEveniment(eveniment.getId());
                        return 0;
                    } catch (ServiceException ex) {
                        return 1;
                    }
                }
            };
            Thread thread = new Thread(myTask);
            thread.setDaemon(true);
            thread.start();

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(new URL(getClass().getResource("/views/evenimente.fxml").toString().replace("%20", " ")));
                AnchorPane root = (AnchorPane) loader.load();
                EvenimenteController evenimenteController = loader.getController();
                evenimenteController.setService(service);
                evenimenteController.getMyPane().getSelectionModel().select(1);
                content.getChildren().setAll(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setNotification(MouseEvent mouseEvent) {
        Task<Integer> myTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                try {
                    if(notificariOnOff.isSelected())
                        service.notificatiOn(eveniment.getId());
                    else
                        service.notificatiOff(eveniment.getId());
                    return 0;
                } catch (ServiceException ex) {
                    return 1;
                }
            }
        };
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }
}
