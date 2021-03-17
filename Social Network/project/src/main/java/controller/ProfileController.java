package controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import domain.Tuple;
import domain.validators.ServiceException;
import service.SuperService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ProfileController {
    SuperService service;
    AnchorPane paneOfMainWindow;
    private String pathToImage;
    boolean isOkToSave=false;

    @FXML
    ImageView imagine;
    @FXML
    TextField nume;
    @FXML
    TextField prenume;
    @FXML
    TextField mail;
    @FXML
    PasswordField veche;
    @FXML
    PasswordField noua;
    @FXML
    PasswordField confirma;
    @FXML
    Button incarca;
    @FXML
    Button editare;
    @FXML
    Label sterge;
    @FXML
    Label show1;
    @FXML
    Label show2;
    @FXML
    Label show3;
    @FXML
    Label complexitate;
    @FXML
    Label pconfirma;
    @FXML
    Label pnoua;
    @FXML
    Label pveche;
    @FXML
    Rectangle sh1;
    @FXML
    Rectangle sh2;
    @FXML
    Rectangle sh3;
    @FXML
    CheckBox afisare;
    @FXML
    CheckBox check1;
    @FXML
    CheckBox check2;
    @FXML
    CheckBox check3;

    private void initOrigin(){
        nume.setText(service.getLogat().getLastName());
        prenume.setText(service.getLogat().getFirstName());
        mail.setText(service.getLogat().getMail());
        afisare.setSelected(false);
        showForPassword(false);
    }

    public void showForPassword(boolean visible) {
        pveche.setVisible(visible);
        pnoua.setVisible(visible);
        pconfirma.setVisible(visible);
        veche.setVisible(visible);
        noua.setVisible(visible);
        confirma.setVisible(visible);
        check1.setVisible(visible);
        check2.setVisible(visible);
        check3.setVisible(visible);
        complexitate.setVisible(visible);
        sh1.setVisible(visible);
        sh2.setVisible(visible);
        sh3.setVisible(visible);
        complexitate.setText("Complexitate parola");
        sh1.setFill(Paint.valueOf("#dadada"));
        sh2.setFill(Paint.valueOf("#dadada"));
        sh3.setFill(Paint.valueOf("#dadada"));
    }

    public void initAll() {
        Image img = new Image(new ByteArrayInputStream(service.getLogat().getImagineProfil()));
        imagine.setImage(img);
        imagine.setPreserveRatio(true);

        nume.setText(service.getLogat().getLastName());
        prenume.setText(service.getLogat().getFirstName());
        mail.setText(service.getLogat().getMail());
        afisare.setSelected(false);
        showForPassword(false);
        editare.setDisable(true);
    }

    public void setService(SuperService service, AnchorPane paneOfMainWindow) {
        this.service = service;
        this.paneOfMainWindow = paneOfMainWindow;
        editare.setDisable(true);
        initAll();
    }

    public void showIt(MouseEvent mouseEvent) {
        if (afisare.isSelected()) {
            showForPassword(true);
            veche.setText("");
            noua.setText("");
            confirma.setText("");
            check1.setSelected(false);
            check2.setSelected(false);
            check3.setSelected(false);
            editare.setDisable(false);
        } else {
            showForPassword(false);
            check1.setSelected(false);
            check2.setSelected(false);
            check3.setSelected(false);
            if(isOkToSave)
                editare.setDisable(false);
            else
                editare.setDisable(true);
        }
    }

    public void showPassword1(MouseEvent mouseEvent) {
        if (check1.isSelected())
            show1.setText("Parola: " + veche.getText());
        else
            show1.setText("");
    }

    public void showPassword2(MouseEvent mouseEvent) {
        if (check2.isSelected())
            show2.setText("Parola: " + noua.getText());
        else
            show2.setText("");
    }

    public void showPassword3(MouseEvent mouseEvent) {
        if (check3.isSelected())
            show3.setText("Parola: " + confirma.getText());
        else
            show3.setText("");
    }

    public void scrie1(KeyEvent keyEvent) {
        if (check1.isSelected())
            show1.setText("Parola: " + veche.getText());
    }

    public void scrie2(KeyEvent keyEvent) {
        if (check2.isSelected())
            show2.setText("Parola: " + noua.getText());
        sh1.setFill(javafx.scene.paint.Color.valueOf("#ff7777"));

        if (noua.getText().isEmpty()) {
            complexitate.setText("Complexitate parola");
            sh1.setFill(Paint.valueOf("#dadada"));
            sh2.setFill(Paint.valueOf("#dadada"));
            sh3.setFill(Paint.valueOf("#dadada"));
        } else {
            char[] pass = noua.getText().toCharArray();
            int comp = 0;
            boolean mici = false, mari = false, cifre = false;
            for (int i = 0; i < pass.length; i++)
                if (pass[i] >= 'a' && pass[i] <= 'z')
                    mici = true;
                else if (pass[i] >= 'A' && pass[i] <= 'Z')
                    mari = true;
                else if (pass[i] >= '0' && pass[i] <= '9')
                    cifre = true;

            if (!noua.getText().isEmpty())
                comp = 1;
            if ((mici || mari || cifre) && noua.getText().length() > 4)
                comp = 2;
            if (mici && mari && cifre && noua.getText().length() >= 8)
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

    public void scrie3(KeyEvent keyEvent) {
        if (check3.isSelected())
            show3.setText("Parola: " + confirma.getText());
    }

    public void salveazaModificarile(MouseEvent mouseEvent) {
        if (afisare.isSelected()) {
            if (nume.getText().isEmpty() || prenume.getText().isEmpty() || mail.getText().isEmpty() ||
                    veche.getText().isEmpty() || noua.getText().isEmpty() || confirma.getText().isEmpty())
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Campuri", "Nu sunt toate campurile completate");
            else {
                if (service.getLogat().getPassword().equals(veche.getText()))
                    if (noua.getText().equals(confirma.getText())) {
                        DataBufferByte data = null;
                        if (pathToImage != null) {
                            try {
                                File imgPath = new File(pathToImage);
                                BufferedImage bufferedImage = ImageIO.read(imgPath);
                                WritableRaster raster = bufferedImage.getRaster();
                                data = (DataBufferByte) raster.getDataBuffer();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                service.updateProfile(mail.getText().trim(), noua.getText(),
                                        prenume.getText().trim(), nume.getText().trim(), data.getData(), pathToImage);
                                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Succesc", "Editare cu succes!");
                                initAll();
                            } catch (ServiceException s) {
                                MessageAlert.showErrorMessage(null, s.getMessage());
                            }
                        } else
                            try {
                                service.updateProfile(mail.getText().trim(), noua.getText(),
                                        prenume.getText().trim(), nume.getText().trim(), null, null);
                                MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Succesc", "Editare cu succes!");
                                initAll();
                            } catch (ServiceException s) {
                                MessageAlert.showErrorMessage(null, s.getMessage());
                            }
                    } else
                        MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Nu corespunde", "Confirmarea parolei nu este valida");
                else
                    MessageAlert.showMessage(null, Alert.AlertType.WARNING, "Parola veche", "Parola veche nu este corecte");

            }
        } else {
            if (nume.getText().isEmpty() || prenume.getText().isEmpty() || mail.getText().isEmpty())
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Campuri", "Nu sunt toate campurile completate");
            else {
                DataBufferByte data = null;
                if (pathToImage != null) {
                    try {
                        File imgPath = new File(pathToImage);
                        BufferedImage bufferedImage = ImageIO.read(imgPath);
                        WritableRaster raster = bufferedImage.getRaster();
                        data = (DataBufferByte) raster.getDataBuffer();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        service.updateProfile(mail.getText().trim(), service.getLogat().getPassword(),
                                prenume.getText().trim(), nume.getText().trim(), data.getData(), pathToImage);
                        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Succesc", "Editare cu succes!");
                        initAll();
                    } catch (ServiceException s) {
                        initAll();
                        MessageAlert.showErrorMessage(null, s.getMessage());
                    }
                } else
                    try {
                        service.updateProfile(mail.getText().trim(), service.getLogat().getPassword(),
                                prenume.getText().trim(), nume.getText().trim(), null, null);
                        MessageAlert.showMessage(null, Alert.AlertType.CONFIRMATION, "Succesc", "Editare cu succes!");
                        initAll();
                    } catch (ServiceException s) {
                        initAll();
                        MessageAlert.showErrorMessage(null, s.getMessage());
                    }
            }
        }
    }

    public void stergeCont(MouseEvent mouseEvent) {
        int val = MessageAlert.yesNoCancelMessage("Raspuns", "Esti sigur ca doresti sa stergi contul?\nNota: Datele vor fi sterse permanent");
        if (val == 1) {
            Task<Tuple<Integer, String>> myTask = new Task<Tuple<Integer, String>>() {
                @Override
                protected Tuple<Integer, String> call() throws Exception {
                    try {
                        service.deleteCont();
                        return new Tuple<Integer, String>(0, "Cont sters cu succes!");
                    } catch (ServiceException ex) {
                        return new Tuple<Integer, String>(1, ex.getMessage());
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
            thread.setDaemon(true);
            thread.start();
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(new URL(getClass().getResource("/views/login.fxml").toString().replace("%20", " ")));
                AnchorPane root = (AnchorPane) loader.load();
                LoginController loginController = loader.getController();
                loginController.setService(service);
                paneOfMainWindow.getChildren().setAll(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void incarcaPoza(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage dialogStage = new Stage();
        File file = fileChooser.showOpenDialog(dialogStage);
        if (file != null) {
            pathToImage = file.getAbsolutePath();
            Image img = new Image("file:///" + file.getAbsolutePath());
            imagine.setImage(img);
            editare.setDisable(false);
        }
    }

    public void typed1(KeyEvent keyEvent) {
        if(!afisare.isSelected()) {
            editare.setDisable(false);
            isOkToSave = true;
        }
    }
}
