package controller;

import com.itextpdf.text.DocumentException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import domain.Cerere;
import domain.Message;
import domain.Tuple;
import domain.Utilizator;
import domain.validators.ServiceException;
import repository.page.PageCereriPrimite;
import repository.page.PageMesajePrimite;
import repository.page.PageMesajeTrimise;
import service.MesajService;
import service.SuperService;
import util.events.Event;
import util.observer.Observer;

import javax.sql.rowset.serial.SerialException;
import javax.swing.text.html.ImageView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MesajeController implements Observer<Event> {
    SuperService service;
    ObservableList<Message> mesajePrimite = FXCollections.observableArrayList();
    ObservableList<Message> mesajeTrimise = FXCollections.observableArrayList();
    ObservableList<Utilizator> persoane = FXCollections.observableArrayList();
    ObservableList<Utilizator> persoane1 = FXCollections.observableArrayList();

    @FXML
    AnchorPane content;
    @FXML
    TableView<Message> mesajePrimiteTable;
    @FXML
    TableColumn<Message, String> datePrimite;
    @FXML
    TableColumn<Message, String> fromPrimite;
    @FXML
    TableColumn<Message, String> messagePrimite;
    @FXML
    TableView<Message> mesajeTrimiseTable;
    @FXML
    TableColumn<Message, String> dateTrimise;
    @FXML
    TableColumn<Message, String> toTrimise;
    @FXML
    TableColumn<Message, String> messageTrimise;
    @FXML
    TableView<Utilizator> persoaneTable;
    @FXML
    TableColumn<Utilizator, ImageView> poza;
    @FXML
    TableColumn<Utilizator, String> nume;
    @FXML
    TableColumn<Utilizator, String> prenume;
    @FXML
    TableView<Utilizator> persoaneTable1;
    @FXML
    TableColumn<Utilizator, ImageView> poza1;
    @FXML
    TableColumn<Utilizator, String> nume1;
    @FXML
    TableColumn<Utilizator, String> prenume1;
    @FXML
    TextArea typeMessage;
    @FXML
    Button trimiteMesaj;
    @FXML
    TabPane myPane;
    @FXML
    Label s1;
    @FXML
    Label s2;
    @FXML
    TextField search;

    public TabPane getMyPane() {
        return myPane;
    }

    private void clearAll(){
        typeMessage.setText(null);
        persoane1.clear();
    }

    @FXML
    public void initialize() {
        datePrimite.setCellValueFactory(new PropertyValueFactory<Message, String>("forGuiDate"));
        fromPrimite.setCellValueFactory(new PropertyValueFactory<Message, String>("forGuiFrom"));
        messagePrimite.setCellValueFactory(new PropertyValueFactory<Message, String>("message"));
        mesajePrimiteTable.setItems(mesajePrimite);

        dateTrimise.setCellValueFactory(new PropertyValueFactory<Message, String>("forGuiDate"));
        toTrimise.setCellValueFactory(new PropertyValueFactory<Message, String>("forGuiTo"));
        messageTrimise.setCellValueFactory(new PropertyValueFactory<Message, String>("message"));
        mesajeTrimiseTable.setItems(mesajeTrimise);

        poza.setCellValueFactory(new PropertyValueFactory<Utilizator, ImageView>("image"));
        nume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        prenume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        persoaneTable.setItems(persoane);

        poza1.setCellValueFactory(new PropertyValueFactory<Utilizator, ImageView>("image"));
        nume1.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        prenume1.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        persoaneTable1.setItems(persoane1);
    }

    public void setService(SuperService service) {
        this.service = service;
        service.addObserver(this);
        initMesajePrimite();
        initMesajeTrimise();
        trimiteMesaj.disableProperty().bind(Bindings.isEmpty(typeMessage.textProperty())
                .or(Bindings.isEmpty(persoaneTable1.getItems())));
        persoaneTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        mesajeTrimiseTable.setRowFactory(tv -> {
            TableRow<Message> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(new URL(getClass().getResource("/views/mesajTrimisDetalii.fxml").toString().replace("%20", " ")));
                        AnchorPane root = (AnchorPane) loader.load();
                        MesajTrimisDetaliiController mesajTrimisDetaliiController = loader.getController();
                        mesajTrimisDetaliiController.setService(service, row.getItem());
                        content.getChildren().setAll(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        mesajePrimiteTable.setRowFactory(tv -> {
            TableRow<Message> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(new URL(getClass().getResource("/views/mesajDetalii.fxml").toString().replace("%20", " ")));
                        AnchorPane root = (AnchorPane) loader.load();
                        MesajDetaliiController mesajDetaliiController = loader.getController();
                        mesajDetaliiController.setService(service, row.getItem());
                        content.getChildren().setAll(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        persoaneTable.setRowFactory(tv -> {
            TableRow<Utilizator> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Utilizator utilizator = row.getItem();
                    boolean ok = true;
                    for (Utilizator u : persoane1)
                        if (u.getId() == row.getItem().getId())
                            ok = false;
                    if (ok) {
                        Utilizator ut = new Utilizator(utilizator.getFirstName(), utilizator.getLastName());
                        ut.setId(utilizator.getId());
                        ut.setImagineProfil(utilizator.getImagineProfil());
                        persoane1.add(ut);
                    } else
                        MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Destinatari", "Acest utilizator este deja adaugat la destinatarii acestui mesaj");
                }
            });
            return row;
        });

        persoaneTable1.setRowFactory(tv -> {
            TableRow<Utilizator> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    persoane1.remove(row.getItem());
                }
            });
            return row;
        });

        s1.setText(service.getLogat().getPageMesajePrimite().getPagina() + "");
        s2.setText(service.getLogat().getPageMesajeTrimise().getPagina() + "");
    }

    private void initMesajeTrimise() {
        Task<Integer> myTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                List<Message> list = service.getLogat().getPageMesajeTrimise().getElemente();
                mesajeTrimise.setAll(list);
                return null;
            }
        };
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void initMesajePrimite() {
        Task<Integer> myTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                List<Message> list = service.getLogat().getPageMesajePrimite().getElemente();
                mesajePrimite.setAll(list);
                return null;
            }
        };
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void trimiteMesaj(MouseEvent mouseEvent) {
        Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
            @Override
            protected Tuple<Integer, String> call() throws Exception {
                try {
                    List<Long> forTo = new ArrayList<>();
                    for (Utilizator u : persoaneTable1.getItems())
                        forTo.add(u.getId());
                    service.writeMessage(forTo, typeMessage.getText());
                    return new Tuple<Integer, String>(0, "Mesaj trimis cu succes!");
                } catch (ServiceException ex) {
                    return new Tuple<Integer, String>(1, ex.getMessage());
                }
            }
        };
        myTask.setOnSucceeded(e -> {
            try {
                if (myTask.get().getLeft() == 0){
                    clearAll();
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmare", myTask.get().getRight());
                }
                else
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

    public void backPrimite(MouseEvent mouseEvent) {
        PageMesajePrimite p = service.getLogat().getPageMesajePrimite();
        p.pageFactory(p.getPagina() - 1);
        mesajePrimite.setAll(service.getLogat().getPageMesajePrimite().getElemente());
        s1.setText(service.getLogat().getPageMesajePrimite().getPagina() + "");
    }

    public void nextPrimite(MouseEvent mouseEvent) {
        PageMesajePrimite p = service.getLogat().getPageMesajePrimite();
        p.pageFactory(p.getPagina() + 1);
        mesajePrimite.setAll(service.getLogat().getPageMesajePrimite().getElemente());
        s1.setText(service.getLogat().getPageMesajePrimite().getPagina() + "");
    }

    public void backTrimise(MouseEvent mouseEvent) {
        PageMesajeTrimise p = service.getLogat().getPageMesajeTrimise();
        p.pageFactory(p.getPagina() - 1);
        mesajeTrimise.setAll(service.getLogat().getPageMesajeTrimise().getElemente());
        s2.setText(service.getLogat().getPageMesajeTrimise().getPagina() + "");
    }

    public void nextTrimise(MouseEvent mouseEvent) {
        PageMesajeTrimise p = service.getLogat().getPageMesajeTrimise();
        p.pageFactory(p.getPagina() + 1);
        mesajeTrimise.setAll(service.getLogat().getPageMesajeTrimise().getElemente());
        s2.setText(service.getLogat().getPageMesajeTrimise().getPagina() + "");
    }

    public void typeHere(KeyEvent keyEvent) {
        String cauta = search.getText();
        String[] array = cauta.split(" ");
        String s2 = null;
        if (array.length >= 2)
            s2 = array[1];
        if (!cauta.isEmpty())
            persoane.setAll(service.otherThanMe(service.getLogat().getId(), 5, array[0], s2));
        else
            persoane.clear();
    }

    @Override
    public void update(Event event) {
        if(event==Event.DELETE_CONT||event==Event.ADD_MESAJ){
            initMesajePrimite();
            initMesajeTrimise();
            s1.setText(service.getLogat().getPageMesajePrimite().getPagina() + "");
            s2.setText(service.getLogat().getPageMesajeTrimise().getPagina() + "");
        }
    }
}
