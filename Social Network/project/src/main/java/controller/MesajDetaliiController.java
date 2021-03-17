package controller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import domain.Message;
import domain.Tuple;
import domain.Utilizator;
import domain.validators.ServiceException;
import service.SuperService;
import util.events.Event;
import util.observer.Observer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class MesajDetaliiController {
    SuperService service;
    Message mesajul;
    ObservableList<Utilizator> persoane = FXCollections.observableArrayList();

    @FXML
    AnchorPane content;
    @FXML
    TableView<Utilizator> persoaneTable;
    @FXML
    TableColumn<Utilizator, String> nume;
    @FXML
    TableColumn<Utilizator, String> prenume;
    @FXML
    TextArea typeHere;
    @FXML
    Button toOne;
    @FXML
    Button toAll;
    @FXML
    ListView<String> conversationView;
    @FXML
    Label fromLabel;
    @FXML
    Label dateLabel;
    @FXML
    ImageView fromImage;
    @FXML
    Label convLabel;

    private void clearAll() {
        typeHere.setText(null);
        persoaneTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void initialize() {
        nume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        prenume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        persoaneTable.setItems(persoane);
    }

    public void setService(SuperService service, Message mesajul) {
        this.service = service;
        this.mesajul = mesajul;
        toAll.disableProperty().bind(Bindings.isEmpty(typeHere.textProperty())
                .or(Bindings.isEmpty(persoaneTable.getItems())));
        toOne.disableProperty().bind(Bindings.isEmpty(typeHere.textProperty())
                .or(Bindings.isEmpty(persoaneTable.getSelectionModel().getSelectedItems()))
                .or(Bindings.isEmpty(persoaneTable.getItems())));
        fromLabel.setText("From: " + mesajul.getFrom().getLastName() + " " + mesajul.getFrom().getFirstName());
        dateLabel.setText("Date: " + mesajul.getForGuiDate());
        fromImage.setImage(mesajul.getFrom().getImage().getImage());
        initPersoane();
        initConversation();
    }

    private void initConversation() {
        Task<Integer> myTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                convLabel.setText("Incarcare conversatie ...");
                List<String> conv = service.conversationOfOneMessage(mesajul);
                conversationView.getItems().addAll(conv);
                return 1;
            }
        };
        myTask.setOnSucceeded(e -> {
            convLabel.setText("Conversatie");
        });
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void initPersoane() {
        List<Utilizator> list = mesajul.getTo().stream()
                .filter(x -> {
                    if (x.getId() != service.getLogat().getId() && x.getId() != 0)
                        return true;
                    return false;
                })
                .collect(Collectors.toList());
        if(mesajul.getFrom().getId()!=0)
            list.add(mesajul.getFrom());
        persoane.setAll(list);

    }

    public void goToMain(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/mesaje.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            MesajeController mesajeController = loader.getController();
            mesajeController.setService(service);
            mesajeController.getMyPane().getSelectionModel().select(0);
            content.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replayToOne(MouseEvent mouseEvent) {
        Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
            @Override
            protected Tuple<Integer, String> call() throws Exception {
                try {
                    service.replayToOne(persoaneTable.getSelectionModel().getSelectedItem().getId(),
                            mesajul.getId(), typeHere.getText());
                    return new Tuple<Integer, String>(0, "Mesaj catre o persoana trimis cu succes!");
                } catch (ServiceException ex) {
                    return new Tuple<Integer, String>(1, ex.getMessage());
                }
            }
        };
        myTask.setOnSucceeded(e -> {
            try {
                if (myTask.get().getLeft() == 0) {
                    clearAll();
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmare", myTask.get().getRight());
                } else
                    MessageAlert.showErrorMessage(null, myTask.get().getRight());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (ExecutionException executionException) {
                executionException.printStackTrace();
            }
        });
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void replayToAll(MouseEvent mouseEvent) {
        Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
            @Override
            protected Tuple<Integer, String> call() throws Exception {
                try {
                    service.replayToAll(mesajul.getId(), typeHere.getText());
                    return new Tuple<Integer, String>(0, "Mesaj trimis cu succes!");
                } catch (ServiceException ex) {
                    return new Tuple<Integer, String>(1, ex.getMessage());
                }
            }
        };
        myTask.setOnSucceeded(e -> {
            try {
                if (myTask.get().getLeft() == 0) {
                    clearAll();
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmare", myTask.get().getRight());
                } else
                    MessageAlert.showErrorMessage(null, myTask.get().getRight());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (ExecutionException executionException) {
                executionException.printStackTrace();
            }
        });
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }
}
