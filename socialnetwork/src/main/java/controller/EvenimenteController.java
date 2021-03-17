package controller;

import javafx.beans.binding.Bindings;
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
import domain.Tuple;
import domain.validators.ServiceException;
import repository.page.PageEvenimenteCreate;
import repository.page.PageEvenimenteParticipare;
import repository.page.PageEvenimentePublice;
import service.SuperService;
import util.events.Event;
import util.observer.Observer;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class EvenimenteController implements Observer<Event> {
    SuperService service;
    ObservableList<Eveniment> evenimentePublice = FXCollections.observableArrayList();
    ObservableList<Eveniment> evenimenteleMele= FXCollections.observableArrayList();
    ObservableList<Eveniment> evenimenteParticipare = FXCollections.observableArrayList();

    @FXML
    AnchorPane content;
    @FXML
    TableView<Eveniment> publiceTable;
    @FXML
    TableColumn<Eveniment, String> gazdaPublic;
    @FXML
    TableColumn<Eveniment, String> datePublic;
    @FXML
    TableColumn<Eveniment, String> denumirePublic;
    @FXML
    TableView<Eveniment> participTable;
    @FXML
    TableColumn<Eveniment, String> gazdaParticip;
    @FXML
    TableColumn<Eveniment, String> dateParticip;
    @FXML
    TableColumn<Eveniment, String> denumireParticip;
    @FXML
    TableColumn<Eveniment, String> statusParticip;
    @FXML
    TableView<Eveniment> creatTable;
    @FXML
    TableColumn<Eveniment, String> descriereCreate;
    @FXML
    TableColumn<Eveniment, String> dateCreate;
    @FXML
    TableColumn<Eveniment, String> denumireCreate;
    @FXML
    TableColumn<Eveniment, String> statusCreate;
    @FXML
    TabPane myPane;
    @FXML
    DatePicker dataEveniment;
    @FXML
    TextField denumire;
    @FXML
    TextArea descriere;
    @FXML
    Button creareEveniment;
    @FXML
    Label p1;
    @FXML
    Label p2;
    @FXML
    Label p3;
    @FXML
    CheckBox seeAll;
    @FXML
    CheckBox seeAllPaticipants;

    public TabPane getMyPane() {
        return myPane;
    }

    @FXML
    public void initialize() {
        gazdaPublic.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("gazdaForGui"));
        datePublic.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("evenimentDateForGui"));
        denumirePublic.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("denumire"));
        publiceTable.setItems(evenimentePublice);

        gazdaParticip.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("gazdaForGui"));
        dateParticip.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("evenimentDateForGui"));
        denumireParticip.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("denumire"));
        statusParticip.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("statusForGui"));
        participTable.setItems(evenimenteParticipare);

        descriereCreate.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("descriere"));
        dateCreate.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("evenimentDateForGui"));
        denumireCreate.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("denumire"));
        statusCreate.setCellValueFactory(new PropertyValueFactory<Eveniment, String>("statusForGui"));
        creatTable.setItems(evenimenteleMele);
    }

    public void setService(SuperService service) {
        this.service = service;
        initPublice();
        initParticip();
        initCreate();
        seeAll.setSelected(service.getLogat().getPageEvenimenteCreate().isAll());
        service.addObserver(this);
        dataEveniment.setEditable(false);
        creareEveniment.disableProperty().bind(Bindings.isEmpty(denumire.textProperty())
                .or(Bindings.isEmpty(descriere.textProperty())).or(dataEveniment.valueProperty().isNull()));

        publiceTable.setRowFactory( tv -> {
            TableRow<Eveniment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(new URL(getClass().getResource("/views/evenimentPublic.fxml").toString().replace("%20", " ")));
                        AnchorPane root = (AnchorPane) loader.load();
                        EvenimentPublicController evenimentPublicController = loader.getController();
                        evenimentPublicController.setService(service,row.getItem());
                        content.getChildren().setAll(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });

        participTable.setRowFactory( tv -> {
            TableRow<Eveniment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(new URL(getClass().getResource("/views/evenimentParticipare.fxml").toString().replace("%20", " ")));
                        AnchorPane root = (AnchorPane) loader.load();
                        EvenimenParticipareController evenimenParticipareController = loader.getController();
                        evenimenParticipareController.setService(service,row.getItem());
                        content.getChildren().setAll(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });

        creatTable.setRowFactory( tv -> {
            TableRow<Eveniment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(new URL(getClass().getResource("/views/evenimentulMeu.fxml").toString().replace("%20", " ")));
                        AnchorPane root = (AnchorPane) loader.load();
                        EvenimentulMeuController evenimentulMeuController = loader.getController();
                        evenimentulMeuController.setService(service,row.getItem());
                        content.getChildren().setAll(root);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });

        p1.setText(service.getLogat().getPageEvenimentePublice().getPagina()+"");
        p2.setText(service.getLogat().getPageEvenimenteParticipare().getPagina()+"");
        p3.setText(service.getLogat().getPageEvenimenteCreate().getPagina()+"");
    }

    private void initCreate() {
        Task<Integer> myTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                evenimenteleMele.setAll(service.getLogat().getPageEvenimenteCreate().getElemente());
                return 1;
            }
        };
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void initParticip() {
        Task<Integer> myTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                evenimenteParticipare.setAll(service.getLogat().getPageEvenimenteParticipare().getElemente());
                return 1;
            }
        };
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }

    private void initPublice() {
        Task<Integer> myTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                evenimentePublice.setAll(service.getLogat().getPageEvenimentePublice().getElemente());
                return 1;
            }
        };
        Thread thread = new Thread(myTask);
        thread.setDaemon(true);
        thread.start();
    }

    public void createIt(MouseEvent mouseEvent) {
        Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
            @Override
            protected Tuple<Integer, String> call() throws Exception {
                try {
                    service.adaugareEveniment(dataEveniment.getValue().atTime(23,59,59),denumire.getText(),descriere.getText());
                    return new Tuple<Integer, String>(0, "Eveniment creat cu succes!");
                } catch (ServiceException ex) {
                    return new Tuple<Integer, String>(1, ex.getMessage());
                }
            }
        };
        myTask.setOnSucceeded(e -> {
            try {
                if (myTask.get().getLeft() == 0){
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Confirmare", myTask.get().getRight());
                    dataEveniment.setValue(null);
                    denumire.clear();
                    descriere.clear();
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

    public void backPublic(MouseEvent mouseEvent) {
        PageEvenimentePublice p = service.getLogat().getPageEvenimentePublice();
        p.pageFactory(p.getPagina() - 1);
        evenimentePublice.setAll(service.getLogat().getPageEvenimentePublice().getElemente());
        p1.setText(service.getLogat().getPageEvenimentePublice().getPagina() + "");
    }

    public void nextPublic(MouseEvent mouseEvent) {
        PageEvenimentePublice p = service.getLogat().getPageEvenimentePublice();
        p.pageFactory(p.getPagina() + 1);
        evenimentePublice.setAll(service.getLogat().getPageEvenimentePublice().getElemente());
        p1.setText(service.getLogat().getPageEvenimentePublice().getPagina() + "");
    }

    public void backParticipare(MouseEvent mouseEvent) {
        PageEvenimenteParticipare p = service.getLogat().getPageEvenimenteParticipare();
        p.pageFactory(p.getPagina() - 1);
        evenimenteParticipare.setAll(service.getLogat().getPageEvenimenteParticipare().getElemente());
        p2.setText(service.getLogat().getPageEvenimenteParticipare().getPagina() + "");
    }

    public void nextParticipare(MouseEvent mouseEvent) {
        PageEvenimenteParticipare p = service.getLogat().getPageEvenimenteParticipare();
        p.pageFactory(p.getPagina() + 1);
        evenimenteParticipare.setAll(service.getLogat().getPageEvenimenteParticipare().getElemente());
        p2.setText(service.getLogat().getPageEvenimenteParticipare().getPagina() + "");
    }

    public void backCreate(MouseEvent mouseEvent) {
        PageEvenimenteCreate p = service.getLogat().getPageEvenimenteCreate();
        p.pageFactory(p.getPagina() - 1);
        evenimenteleMele.setAll(service.getLogat().getPageEvenimenteCreate().getElemente());
        p3.setText(service.getLogat().getPageEvenimenteCreate().getPagina() + "");
    }

    public void nextCreate(MouseEvent mouseEvent) {
        PageEvenimenteCreate p = service.getLogat().getPageEvenimenteCreate();
        p.pageFactory(p.getPagina() + 1);
        evenimenteleMele.setAll(service.getLogat().getPageEvenimenteCreate().getElemente());
        p3.setText(service.getLogat().getPageEvenimenteCreate().getPagina() + "");
    }

    @Override
    public void update(Event event) {
        if(event==Event.ADD_EVENIMENT||event==Event.DELETE_EVENIMENT||event==Event.ADD_PARTICIPANT||
                event== Event.DELETE_PARTICIPANT){
            initCreate();
            initParticip();
            initPublice();
            p1.setText(service.getLogat().getPageEvenimentePublice().getPagina()+"");
            p2.setText(service.getLogat().getPageEvenimenteParticipare().getPagina()+"");
            p3.setText(service.getLogat().getPageEvenimenteCreate().getPagina()+"");
        }
    }

    public void clickIt(MouseEvent mouseEvent) {
        if(seeAll.isSelected())
            service.getLogat().getPageEvenimenteCreate().setAll(true);
        else
            service.getLogat().getPageEvenimenteCreate().setAll(false);
        service.getLogat().getPageEvenimenteCreate().refreshPage();
        evenimenteleMele.setAll(service.getLogat().getPageEvenimenteCreate().getElemente());
        p3.setText(service.getLogat().getPageEvenimenteCreate().getPagina() + "");
    }

    public void clickOnIt(MouseEvent mouseEvent) {
    }
}
