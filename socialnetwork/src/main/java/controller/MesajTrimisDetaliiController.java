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
import domain.Message;
import domain.ReplyMessage;
import domain.Utilizator;
import service.SuperService;
import util.events.Event;
import util.observer.Observer;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class MesajTrimisDetaliiController{
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
    Label date;
    @FXML
    ListView<String> listConversatie;
    @FXML
    Label convLabel;

    @FXML
    public void initialize() {
        nume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        prenume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        persoaneTable.setItems(persoane);
    }

    public void setService(SuperService service, Message mesajul) {
        this.service = service;
        this.mesajul = mesajul;
        initPersoane();
        date.setText("Data trimiterii: "+mesajul.getForGuiDate());
        initConversation();
    }

    private void initConversation() {
        Task<Integer> myTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                convLabel.setText("Incarcare conversatie ...");
                List<String> conv=service.conversationOfOneMessage(mesajul);
                listConversatie.getItems().addAll(conv);
                return 1;
            }
        };
        myTask.setOnSucceeded(e -> {
            convLabel.setText("Conversatie:");
        });
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void initPersoane() {
        persoane.setAll(mesajul.getTo().stream()
            .filter(x->x.getId()!=0)
            .collect(Collectors.toList()));
    }

    public void backToMain(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(new URL(getClass().getResource("/views/mesaje.fxml").toString().replace("%20", " ")));
            AnchorPane root = (AnchorPane) loader.load();
            MesajeController mesajeController = loader.getController();
            mesajeController.setService(service);
            mesajeController.getMyPane().getSelectionModel().select(1);
            content.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
