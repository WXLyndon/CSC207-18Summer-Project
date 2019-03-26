package Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import javafx.stage.Stage;

import java.util.ArrayList;

public class AdminStage {


    @FXML
    private Button set;

    @FXML
    private TextField cost;

    @FXML
    private TextArea data;


    /**
     * The method links to GUI to set the operating cost.
     */
    @FXML
    private void setCost() {
        if (!cost.getText().equals("")) {
            TransInfo.getInstance().getAdminInfo().setOperatingCost(Integer.valueOf(cost.getText()));
            UserStage.showAlert("INFORMATION", "Success", "Has set operating cost to " + cost.getText());
        } else {
            UserStage.showAlert("WARNING", "Failure", "Please first type in the operating cost");
        }
    }

    /**
     * The method links to GUI to show all the useful data.
     */
    @FXML
    private void showData() {
        String info = "System's operating cost is " + TransInfo.getInstance().getAdminInfo().getOperatingCost() + "\n" +
                "System's revenue is " + TransInfo.getInstance().getAdminInfo().getRevenue() + "\n"
                + "System's income is " + TransInfo.getInstance().getAdminInfo().checkIncome() + "\n";
        data.setText(info);
    }


    /**
     * The method links to GUI to end the program by adminUser at the end of the day.
     */
    @FXML
    private void endProgram() {
        String info = "Income is " + TransInfo.getInstance().getAdminInfo().checkIncome() + "\n" +
                "Reached " + TransInfo.getInstance().getAdminInfo().getAllStationReached() + " stations";
        UserStage.showAlert("INFORMATION", "Today's summary", info);
        Stage stage = (Stage) set.getScene().getWindow();
        int firstStar = stage.getTitle().indexOf("*");
        int secondStar = stage.getTitle().lastIndexOf("*");
        String name = stage.getTitle().substring(firstStar + 1, secondStar);
        TransInfo.getInstance().getUserList().getAdmin(name).endProgram(TransInfo.getInstance().getAdminInfo());
    }

    /**
     * The method links to GUI to logout.
     */
    @FXML
    private void logOut() {
        Stage stage = (Stage) set.getScene().getWindow();
        stage.close();
        try {
            Parent newAccount = FXMLLoader.load(getClass().getResource("/Viewer/Entry.fxml"));
            Stage newUser = new Stage();
            newUser.setTitle("Log in");
            newUser.setScene(new Scene(newAccount, 600, 400));
            newUser.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The method links to GUI to set the announcement.
     */
    @FXML
    private void typeAnnouncement() {
        TransInfo.getInstance().getAdminInfo().setAnnouncement(data.getText());
        UserStage.showAlert("Information", "Success", "Set announcement successfully!");
    }

    /**
     * The method links to GUI to clear the TextArea.
     */
    @FXML
    private void clearBox() {
        data.setText("");
    }

    /**
     * The method links to GUI to show Bar Chart of monthly report. Consulted the code from the tutorials on the
     * Oracle official websites.
     */
    @FXML
    private void showBarChart() {
        Stage stage = new Stage();
        stage.setTitle("Monthly report chart");
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc =
                new BarChart<>(xAxis, yAxis);
        bc.setTitle("Monthly report chart");
        xAxis.setLabel("Date");
        yAxis.setLabel("Value");

        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Revenue");

        ArrayList<DailyReport> dailyReports = TransInfo.getInstance().getAdminInfo().getReportOf30Days();
        for (DailyReport dailyReport : dailyReports) {
            series1.getData().add(new XYChart.Data<>(dailyReport.getDate().toString(), dailyReport.getRevenue()));
        }


        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("All stations reached");
        for (DailyReport dailyReport : dailyReports) {
            series2.getData().add(new XYChart.Data<>(dailyReport.getDate().toString(), dailyReport.getStationReached()));
        }

        Scene scene = new Scene(bc, 800, 600);
        //noinspection unchecked
        bc.getData().addAll(series1, series2);
        stage.setScene(scene);
        stage.show();

    }
}
