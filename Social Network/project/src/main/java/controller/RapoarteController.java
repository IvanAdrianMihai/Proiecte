package controller;

import com.itextpdf.text.DocumentException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import domain.Cerere;
import domain.Tuple;
import domain.Utilizator;
import repository.page.PagePrieteni;
import service.SuperService;

import javax.swing.text.html.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import static domain.Constants.DATE_TIME_FORMATTER;

public class RapoarteController {
    SuperService service;
    ObservableList<Utilizator> prieteni = FXCollections.observableArrayList();
    LocalDateTime first1 = null, second1 = null, first2 = null, second2 = null;
    String path1, path2;
    Utilizator friend;

    @FXML
    DatePicker from1;
    @FXML
    DatePicker from2;
    @FXML
    DatePicker to1;
    @FXML
    DatePicker to2;
    @FXML
    TextField director1;
    @FXML
    TextField director2;
    @FXML
    Button select1;
    @FXML
    Button select2;
    @FXML
    Button genereaza1;
    @FXML
    Button genereaza2;
    @FXML
    ListView<String> view1;
    @FXML
    ListView<String> view2;
    @FXML
    TableView<Utilizator> prieteniTabel;
    @FXML
    TableColumn<Utilizator, String> prietenNume;
    @FXML
    TableColumn<Utilizator, String> prietenPrenume;
    @FXML
    Label p1;

    @FXML
    public void initialize() {
        prietenNume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        prietenPrenume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        prieteniTabel.setItems(prieteni);
    }

    public void setService(SuperService service) {
        this.service = service;
        genereaza1.setDisable(true);
        genereaza2.setDisable(true);
        BooleanBinding myBind = new BooleanBinding() {
            {
                super.bind(from1.valueProperty(),
                        to1.valueProperty(),
                        director1.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (from1.getValue() == null
                        || to1.getValue() == null
                        || director1.getText().isEmpty());
            }
        };
        director1.setEditable(false);
        from1.setEditable(false);
        to1.setEditable(false);
        select1.disableProperty().bind(myBind);

        BooleanBinding myBind1 = new BooleanBinding() {
            {
                super.bind(from2.valueProperty(),
                        to2.valueProperty(),
                        director2.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (from2.getValue() == null
                        || to2.getValue() == null
                        || director2.getText().isEmpty());
            }
        };
        director2.setEditable(false);
        from2.setEditable(false);
        to2.setEditable(false);
        select2.disableProperty().bind(myBind1.or(Bindings.isEmpty(prieteniTabel.getSelectionModel().getSelectedItems())));

        initPrieteni();
        p1.setText(service.getLogat().getPagePrieteni().getPagina()+"");
    }

    private void initPrieteni() {
        prieteni.setAll(service.getLogat().getPagePrieteni().getElemente());
    }

    public void selectDir1(MouseEvent mouseEvent) {
        director1.setDisable(true);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage dialogStage = new Stage();
        File selectedDirectory = directoryChooser.showDialog(dialogStage);
        if (selectedDirectory != null)
            director1.setText(selectedDirectory.getAbsolutePath());
        director1.setDisable(false);
    }

    public void selectDir2(MouseEvent mouseEvent) {
        director2.setDisable(true);
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage dialogStage = new Stage();
        File selectedDirectory = directoryChooser.showDialog(dialogStage);
        if (selectedDirectory != null)
            director2.setText(selectedDirectory.getAbsolutePath());
        director2.setDisable(false);
    }

    public void afisare1(MouseEvent mouseEvent) {
        genereaza1.setDisable(false);
        if (from1.getValue().compareTo(to1.getValue()) >= 0) {
            first1 = to1.getValue().atStartOfDay();
            second1 = from1.getValue().atTime(23, 59, 59);
        } else {
            first1 = from1.getValue().atStartOfDay();
            second1 = to1.getValue().atTime(23, 59, 59);
        }
        path1 = director1.getText();
        String prietenie = service.prieteniRap1(first1, second1);
        String mesaje = service.mesajeRap1(first1, second1);
        view1.getItems().clear();
        view1.getItems().addAll(first1.format(DATE_TIME_FORMATTER) + "-" + second1.format(DATE_TIME_FORMATTER),
                "Prieteni:", prietenie, "Mesaje:", mesaje);
    }

    public void genereazaPDF1(MouseEvent mouseEvent) {
        Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
            @Override
            protected Tuple<Integer, String> call() throws Exception {
                try {
                    service.makeReport1PDF(path1, first1, second1);
                    return new Tuple<Integer, String>(0, "Raportul 1 creat cu succes!");
                } catch (FileNotFoundException ex) {
                    return new Tuple<Integer, String>(1, "Eroare la crearea fisierului PDF.\nPosibil ca acesta sa fie deschis.");
                } catch (DocumentException ex) {
                    return new Tuple<Integer, String>(1, "Eroare la crearea fisierului PDF.\nPosibil ca acesta sa fie deschis.");
                }
            }
        };
        myTask.setOnSucceeded(e -> {
            try {
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
        thread.setName("Raport1");
        thread.setDaemon(true);
        thread.start();
    }

    public void afisare2(MouseEvent mouseEvent) {
        genereaza2.setDisable(false);
        if (from2.getValue().compareTo(to2.getValue()) >= 0) {
            first2 = to2.getValue().atStartOfDay();
            second2 = from2.getValue().atTime(23, 59, 59);
        } else {
            first2 = from2.getValue().atStartOfDay();
            second2 = to2.getValue().atTime(23, 59, 59);
        }
        path2 = director2.getText();
        friend = prieteniTabel.getSelectionModel().getSelectedItem();
        String mesaje = service.mesajeRap2(first2, second2, friend);
        view2.getItems().clear();
        view2.getItems().addAll(first2.format(DATE_TIME_FORMATTER) + "-" + second2.format(DATE_TIME_FORMATTER),
                "Prieten: " + friend.getFirstName() + " " + friend.getLastName(),
                "Mesaje:", mesaje);
    }

    public void genereazaPDF2(MouseEvent mouseEvent) {
        Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
            @Override
            protected Tuple<Integer, String> call() throws Exception {
                try {
                    service.makeReport2PDF(path2, first2, second2, friend);
                    return new Tuple<Integer, String>(0, "Raportul 2 creat cu succes!");
                } catch (FileNotFoundException ex) {
                    return new Tuple<Integer, String>(1, "Eroare la crearea fisierului PDF.\nPosibil ca acesta sa fie deschis.");
                } catch (DocumentException ex) {
                    return new Tuple<Integer, String>(1, "Eroare la crearea fisierului PDF.\nPosibil ca acesta sa fie deschis.");
                }
            }
        };
        myTask.setOnSucceeded(e -> {
            try {
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
        thread.setName("Raport2");
        thread.setDaemon(true);
        thread.start();
    }

    public void backPrieteni(MouseEvent mouseEvent) {
        PagePrieteni p = service.getLogat().getPagePrieteni();
        p.pageFactory(p.getPagina() - 1);
        prieteni.setAll(service.getLogat().getPagePrieteni().getElemente());
        p1.setText(p.getPagina() + "");
    }

    public void nextPrieteni(MouseEvent mouseEvent) {
        PagePrieteni p = service.getLogat().getPagePrieteni();
        p.pageFactory(p.getPagina() + 1);
        prieteni.setAll(service.getLogat().getPagePrieteni().getElemente());
        p1.setText(p.getPagina() + "");
    }
}
