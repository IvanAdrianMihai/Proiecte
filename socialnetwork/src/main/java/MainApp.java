import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import config.ApplicationContext;
import controller.LoginController;
import domain.validators.*;
import repository.database.*;
import service.*;

import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {
    SuperService service = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("My SocialNetwork");
        String username = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.username");
        String password = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.pasword");
        String URL = ApplicationContext.getPROPERTIES().getProperty("database.socialnetwork.url");

        UtilizatorRepositoryDB ur = new UtilizatorRepositoryDB(URL, username, password);
        UtilizatorService us = new UtilizatorService(ur, new UtilizatorValidator());
        PrietenieService ps = new PrietenieService(new PrietenieRepositoryDB(URL, username, password), new PrietenieValidator(), us);
        CerereService cs = new CerereService(new CerereRepositoryBD(URL, username, password, ur), new CerereValidator(), us);
        MesajService ms = new MesajService(new MesajRepositoryBD(URL, username, password, ur), new MessageValidator());
        ContService cons = new ContService(new ContRepositoryBD(URL, username, password), new ContValidator());
        EvenimentService es = new EvenimentService(new EvenimentRepositoryBD(URL, username, password, ur), new EvenimentValidator(), ur);
        UtilizatorPageService ups = new UtilizatorPageService(us, ps, ms, cs, cons, es);
        service = new SuperService(us, ps, cs, ms, cons, ups, es);

        primaryStage.getIcons().add(new Image("file:///" + "D:\\Proiecte Git\\Proiecte\\socialnetwork\\src\\main" +
                "\\resources\\images\\logo.png"));
        initView(primaryStage);
        primaryStage.show();


        Task<Integer> taskNotificari = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                try {
                    while (true){
                        System.out.println("Start sleep");
                        Thread.sleep(600000); //10 minute
                        //Thread.sleep(300000); //5 minute
                        //Thread.sleep(180000); //3 minute
                        //Thread.sleep(20000); //20 secunde
                        System.out.println("End sleep");
                        service.trimiteNotificariEvenimente();
                        System.out.println("S-au trimis notificari");
                    }
                } catch (ServiceException ex) {
                    return 0;
                }
            }
        };
        Thread thread = new Thread(taskNotificari);
        thread.setDaemon(true);
        thread.start();    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader searchLoader = new FXMLLoader();
        searchLoader.setLocation(new URL(getClass().getResource("/views/login.fxml").toString().replace("%20", " ")));
        AnchorPane searchUtilizatorLayout = searchLoader.load();
        Scene scene = new Scene(searchUtilizatorLayout);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My SocialNetwork");
        primaryStage.setResizable(false);

        LoginController loginController = searchLoader.getController();
        loginController.setService(service);
    }

    @Override
    public void stop() {
        if (service != null)
            service.deleteMesjeSystem();
    }
}
