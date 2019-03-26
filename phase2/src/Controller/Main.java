package Controller;

import Model.Station;
import Model.PublicTrans;
import Model.TransInfo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

    /**
     * Launch the program and enter the initial stage of the program
     *
     * @param primaryStage Stage: the initial stage of the program
     * @throws Exception Exception will be thrown if any exception happens. Normally there is none.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Viewer/Entry.fxml"));
        primaryStage.setTitle("Transportation System");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
        TransInfo.readSerializableObject();
    }


    public static void main(String[] args) {
        ArrayList<Station> publicTrans = new ArrayList<>();

        //load public trans system.
        try (BufferedReader fileReader =
                     new BufferedReader(new FileReader("PublicTransStations.txt"))) {
            String line = fileReader.readLine();
            while (line != null) {
                String stationType = line.split(" ")[0];
                String names = line.split(" ")[1];
                String[] stationNames = names.split("->");
                ArrayList<Station> allStation = new ArrayList<>();
                for (String s : stationNames) {
                    Station newStation;
                    if (stationType.equals("Bus:")) {
                        newStation = new Station(s, "Bus");
                    } else {
                        newStation = new Station(s, "Subway");
                    }
                    if (!publicTrans.contains(newStation)) {
                        allStation.add(newStation);
                    } else {
                        int index = publicTrans.indexOf(newStation);
                        allStation.add(publicTrans.get(index));
                    }
                }

                for (int i = 0; i < allStation.size() - 1; i++) {
                    allStation.get(i).addRoute(allStation.get(i + 1));
                    allStation.get(i + 1).addRoute(allStation.get(i));
                }

                for (Station s : allStation) {
                    if (!publicTrans.contains(s)) {
                        publicTrans.add(s);
                    }
                }
                line = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        PublicTrans.allStations.addAll(publicTrans);
        launch(args);
        TransInfo.writeSerializableObject();
    }
}
