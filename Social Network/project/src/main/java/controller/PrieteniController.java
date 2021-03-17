package controller;

import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
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
import domain.*;
import domain.validators.ServiceException;
import repository.page.PageCereriPrimite;
import repository.page.PageCereriTrimise;
import repository.page.PagePrieteni;
import service.SuperService;
import util.events.Event;
import util.observer.Observer;

import javax.swing.text.html.ImageView;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class PrieteniController implements Observer<Event> {
    SuperService service;
    ObservableList<Utilizator> prieteni = FXCollections.observableArrayList();
    ObservableList<Utilizator> prieteniNoi = FXCollections.observableArrayList();
    ObservableList<Cerere> cereriPrimite = FXCollections.observableArrayList();
    ObservableList<Cerere> cereriTrimise = FXCollections.observableArrayList();

    @FXML
    TableView<Utilizator> prieteniTabel;
    @FXML
    TableColumn<Utilizator, ImageView> prieteniImagine;
    @FXML
    TableColumn<Utilizator, String> preiteniNume;
    @FXML
    TableColumn<Utilizator, String> prieteniPrenume;
    @FXML
    TableView<Utilizator> prieteniNoiTable;
    @FXML
    TableColumn<Utilizator, ImageView> prieteniNoiImagine;
    @FXML
    TableColumn<Utilizator, String> preiteniNoiNume;
    @FXML
    TableColumn<Utilizator, String> prieteniNoiPrenume;
    @FXML
    TableView<Cerere> cereriPrimiteTable;
    @FXML
    TableColumn<Cerere, ImageView> imageCerere;
    @FXML
    TableColumn<Cerere, String> numeCerere;
    @FXML
    TableColumn<Cerere, String> dataCerere;
    @FXML
    TableColumn<Cerere, String> statutCerere;

    @FXML
    Label p1;
    @FXML
    Label p2;
    @FXML
    TableView<Cerere> cereriTrimiseTable;
    @FXML
    TableColumn<Cerere, ImageView> imageCerereT;
    @FXML
    TableColumn<Cerere, String> numeCerereT;
    @FXML
    TableColumn<Cerere, String> dataCerereT;
    @FXML
    TableColumn<Cerere, String> statutCerereT;
    @FXML
    TextField cautaNume;
    @FXML
    TextField search;
    @FXML
    Button deleteFriend;
    @FXML
    Button trimiteCerere;
    @FXML
    Button approve;
    @FXML
    Button reject;
    @FXML
    Button deleteCerere;
    @FXML
    Label p3;


    @FXML
    public void initialize() {
        preiteniNume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        prieteniPrenume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        prieteniImagine.setCellValueFactory(new PropertyValueFactory<Utilizator, ImageView>("image"));
        prieteniTabel.setItems(prieteni);

        preiteniNoiNume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("lastName"));
        prieteniNoiPrenume.setCellValueFactory(new PropertyValueFactory<Utilizator, String>("firstName"));
        prieteniNoiImagine.setCellValueFactory(new PropertyValueFactory<Utilizator, ImageView>("image"));
        prieteniNoiTable.setItems(prieteniNoi);

        numeCerere.setCellValueFactory(new PropertyValueFactory<Cerere, String>("forGuiFrom"));
        dataCerere.setCellValueFactory(new PropertyValueFactory<Cerere, String>("forGuiDate"));
        statutCerere.setCellValueFactory(new PropertyValueFactory<Cerere, String>("type"));
        imageCerere.setCellValueFactory(new PropertyValueFactory<Cerere, ImageView>("imageFrom"));
        cereriPrimiteTable.setItems(cereriPrimite);

        numeCerereT.setCellValueFactory(new PropertyValueFactory<Cerere, String>("forGuiTo"));
        dataCerereT.setCellValueFactory(new PropertyValueFactory<Cerere, String>("forGuiDate"));
        statutCerereT.setCellValueFactory(new PropertyValueFactory<Cerere, String>("type"));
        imageCerereT.setCellValueFactory(new PropertyValueFactory<Cerere, ImageView>("imageTo"));
        cereriTrimiseTable.setItems(cereriTrimise);
    }

    public void setService(SuperService service) {
        this.service = service;
        service.addObserver(this);
        deleteFriend.disableProperty().bind(Bindings.isEmpty(prieteniTabel.getSelectionModel().getSelectedItems()));
        trimiteCerere.disableProperty().bind(Bindings.isEmpty(prieteniNoiTable.getSelectionModel().getSelectedItems()));
        approve.setDisable(true);
        reject.setDisable(true);
        deleteCerere.disableProperty().bind(Bindings.isEmpty(cereriTrimiseTable.getSelectionModel().getSelectedItems()));

        cereriPrimiteTable.setRowFactory(tv -> {
            TableRow<Cerere> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (!row.isEmpty()))
                    if (cereriPrimiteTable.getSelectionModel().getSelectedItem().getType() == CerereType.PENDING) {
                        approve.setDisable(false);
                        reject.setDisable(false);
                    } else {
                        approve.setDisable(true);
                        reject.setDisable(true);
                    }
            });
            return row;
        });

        initPrieteni();
        initCerereiPrimite();
        initCerereiTrimise();
        p1.setText(service.getLogat().getPageCereriPrimite().getPagina() + "");
        p2.setText(service.getLogat().getPageCereriTrimise().getPagina() + "");
        p3.setText(service.getLogat().getPagePrieteni().getPagina() + "");
    }

    private void initCerereiTrimise() {
        cereriTrimise.setAll(service.getLogat().getPageCereriTrimise().getElemente());
    }

    private void initCerereiPrimite() {
        cereriPrimite.setAll(service.getLogat().getPageCereriPrimite().getElemente());
    }

    public void initPrieteni() {
        prieteni.setAll(service.getLogat().getPagePrieteni().getElemente());
    }

    public void typeSomething(KeyEvent keyEvent) {
        String[] v = cautaNume.getText().split(" ");
        String nume = null, prenume = null;
        if (v.length == 1)
            nume = v[0].trim();
        else if (v.length >= 2) {
            nume = v[0].trim();
            prenume = v[1].trim();
        }
        service.getLogat().getPagePrieteni().setS1(nume);
        service.getLogat().getPagePrieteni().setS2(prenume);
        service.getLogat().getPagePrieteni().refreshPage();
        prieteni.setAll(service.getLogat().getPagePrieteni().getElemente());
        p3.setText(service.getLogat().getPagePrieteni().getPagina() + "");
    }

    public void wantToDelete(MouseEvent mouseEvent) {
        if (MessageAlert.yesNoCancelMessage("Sterge prieten", "Esti sigur ca doresti sa iti scoti prietenul din lista de prieteni?") == 1)
            try {
                service.deletePrietenieCerere(prieteniTabel.getSelectionModel().getSelectedItem().getId());
            } catch (ServiceException s) {
                MessageAlert.showErrorMessage(null, s.getMessage());
            }
    }

    public void searchNewFriend(KeyEvent keyEvent) {
        String cauta = search.getText();
        String[] array = cauta.split(" ");
        String s2 = null;
        if (array.length >= 2)
            s2 = array[1];
        if (!cauta.isEmpty())
            prieteniNoi.setAll(service.notYetFriends(service.getLogat().getId(), 5, array[0], s2));
        else
            prieteniNoi.clear();
    }

    public void sendCerere(MouseEvent mouseEvent) {
        Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
            @Override
            protected Tuple<Integer, String> call() throws Exception {
                try {
                    service.addCerere(prieteniNoiTable.getSelectionModel().getSelectedItem().getId());
                    return new Tuple<Integer, String>(0, "Cerere trimisa cu succes");
                } catch (ServiceException ex) {
                    return new Tuple<Integer, String>(1, ex.getMessage());
                }
            }
        };
        myTask.setOnSucceeded(e -> {
            try {
                if (myTask.get().getLeft() == 0)
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Succes", myTask.get().getRight());
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

    public void approveIt(MouseEvent mouseEvent) {
        Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
            @Override
            protected Tuple<Integer, String> call() throws Exception {
                try {
                    service.approveCerere(cereriPrimiteTable.getSelectionModel().getSelectedItem().getFrom().getId());
                    return new Tuple<Integer, String>(0, "Cererea a fost acceptata");
                } catch (ServiceException ex) {
                    return new Tuple<Integer, String>(1, ex.getMessage());
                }
            }
        };
        myTask.setOnSucceeded(e -> {
            try {
                if (myTask.get().getLeft() == 0) {
                    approve.setDisable(true);
                    reject.setDisable(true);
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Succes", myTask.get().getRight());
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

    public void rejectIt(MouseEvent mouseEvent) {
        Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
            @Override
            protected Tuple<Integer, String> call() throws Exception {
                try {
                    service.rejectCerere(cereriPrimiteTable.getSelectionModel().getSelectedItem().getFrom().getId());
                    return new Tuple<Integer, String>(0, "Cererea a fost respinsa");
                } catch (ServiceException ex) {
                    return new Tuple<Integer, String>(1, ex.getMessage());
                }
            }
        };
        myTask.setOnSucceeded(e -> {
            try {
                if (myTask.get().getLeft() == 0) {
                    approve.setDisable(true);
                    reject.setDisable(true);
                    MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Succes", myTask.get().getRight());
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

    public void deleteCerere(MouseEvent mouseEvent) {
        if (MessageAlert.yesNoCancelMessage("Atentie!", "Sunteti sigur ca doriti sa stergeti aceasta cerere?") == 1) {
            Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
                @Override
                protected Tuple<Integer, String> call() throws Exception {
                    try {
                        service.deleteCerere(cereriTrimiseTable.getSelectionModel().getSelectedItem().getTo().getId());
                        return new Tuple<Integer, String>(0, "Cererea a fost stearsa");
                    } catch (ServiceException ex) {
                        return new Tuple<Integer, String>(1, ex.getMessage());
                    }
                }
            };
            myTask.setOnSucceeded(e -> {
                try {
                    if (myTask.get().getLeft() == 0)
                        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Succes", myTask.get().getRight());
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
    }

    public void backPrimite(MouseEvent mouseEvent) {
        PageCereriPrimite p = service.getLogat().getPageCereriPrimite();
        p.pageFactory(p.getPagina() - 1);
        cereriPrimite.setAll(service.getLogat().getPageCereriPrimite().getElemente());
        p1.setText(p.getPagina() + "");
    }

    public void nextPrimite(MouseEvent mouseEvent) {
        PageCereriPrimite p = service.getLogat().getPageCereriPrimite();
        p.pageFactory(p.getPagina() + 1);
        cereriPrimite.setAll(service.getLogat().getPageCereriPrimite().getElemente());
        p1.setText(p.getPagina() + "");
    }

    public void nextTrimise(MouseEvent mouseEvent) {
        PageCereriTrimise p = service.getLogat().getPageCereriTrimise();
        p.pageFactory(p.getPagina() + 1);
        cereriTrimise.setAll(service.getLogat().getPageCereriTrimise().getElemente());
        p2.setText(p.getPagina() + "");
    }

    public void backTrimise(MouseEvent mouseEvent) {
        PageCereriTrimise p = service.getLogat().getPageCereriTrimise();
        p.pageFactory(p.getPagina() - 1);
        cereriTrimise.setAll(service.getLogat().getPageCereriTrimise().getElemente());
        p2.setText(p.getPagina() + "");
    }

    public void backPrieteni(MouseEvent mouseEvent) {
        PagePrieteni p = service.getLogat().getPagePrieteni();
        p.pageFactory(p.getPagina() - 1);
        prieteni.setAll(service.getLogat().getPagePrieteni().getElemente());
        p3.setText(p.getPagina() + "");
    }

    public void nextPrieteni(MouseEvent mouseEvent) {
        PagePrieteni p = service.getLogat().getPagePrieteni();
        p.pageFactory(p.getPagina() + 1);
        prieteni.setAll(service.getLogat().getPagePrieteni().getElemente());
        p3.setText(p.getPagina() + "");
    }

    @Override
    public void update(Event event) {
        if (event == Event.APPROVE_CERERE) {
            initPrieteni();
            initCerereiPrimite();
            initCerereiTrimise();
            p1.setText(service.getLogat().getPageCereriPrimite().getPagina() + "");
            p2.setText(service.getLogat().getPageCereriTrimise().getPagina() + "");
            p3.setText(service.getLogat().getPagePrieteni().getPagina() + "");
        } else if (event == Event.DELETE_PRIETEN) {
            initPrieteni();
            p3.setText(service.getLogat().getPagePrieteni().getPagina() + "");
        } else if (event == Event.ADD_CERERE || event == Event.REJECT_CERERE || event == Event.DELETE_CERERE) {
            initCerereiPrimite();
            initCerereiTrimise();
            p1.setText(service.getLogat().getPageCereriPrimite().getPagina() + "");
            p2.setText(service.getLogat().getPageCereriTrimise().getPagina() + "");
        }
    }
}
