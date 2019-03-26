package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Objects;

public class UserStage {
    @FXML
    private Button account, go, createNewAccount, addTen, set, tapButton;

    @FXML
    private TextField accountName, email, whichCard, startingStation, stopType, name, stationTwo, stationThree, stationFour,
            stopTypeTwo, stopTypeThree, stopTypeFour;

    @FXML
    private TextArea cards, map;

    //---------------------------------Helper Function-------------------------------------

    /**
     * This method show a new stage with title name
     *
     * @param fxmlName the name of fxml files
     * @param title    the title of this new stage
     * @param width    the width
     * @param height   the height
     */
    private void showNewStage(String fxmlName, String title, int width, int height) {
        try {
            Parent newAccount = FXMLLoader.load(getClass().getResource(fxmlName));
            Stage newUser = new Stage();
            newUser.setTitle(title);
            newUser.setScene(new Scene(newAccount, width, height));
            newUser.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method show alert
     *
     * @param title the title of alert
     * @param head  the head of alert
     * @param text  the text of this alert
     */
    public static void showAlert(String title, String head, String text) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(head);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * This method get name or email or card number base on the input symbol from the title
     *
     * @param symbol  indicate what type of info what to get: *-name, %-email,#-card number
     * @param current the button at this stage
     * @return return info
     */
    private String showNameEmail(String symbol, Button current) {
        Stage stage = (Stage) current.getScene().getWindow();

        if (stage.getTitle().contains(symbol)) {
            int firstStar = stage.getTitle().indexOf(symbol);
            int secondStar = stage.getTitle().lastIndexOf(symbol);
            return stage.getTitle().substring(firstStar + 1, secondStar);
        } else {
            return null;
        }
    }

    /**
     * This method adds balance to travel card
     *
     * @param money amount of money want to be added
     * @throws Exception may cause exception
     */
    private void addAllBalance(int money) throws Exception {
        Stage stage = (Stage) addTen.getScene().getWindow();
        stage.close();
        String name = showNameEmail("*", addTen);
        String email = showNameEmail("%", addTen);
        String cardID = showNameEmail("&", addTen);
        TransInfo.getInstance().getUserList().getHolderWQ(name, email).getCard(Integer.parseInt(Objects.requireNonNull(cardID))).addBalance(money);
    }

    //---------------------------------Change Pages Methods---------------------------------------

    /**
     * Change to UserAccount.fxml
     */
    @FXML
    private void accountInfo() {
        Stage stage = (Stage) account.getScene().getWindow();
        stage.close();
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        showNewStage("/Viewer/UserAccount.fxml", "*" + name + "* with e-mail %" + email + "%", 600, 400);
    }

    /**
     * Change to Entry.fxml from existing stage
     */
    @FXML
    private void logOut() {
        Stage stage = (Stage) account.getScene().getWindow();
        stage.close();
        showNewStage("/Viewer/Entry.fxml", "Log in", 600, 400);
    }

    /**
     * Change to Entry.fxml from existing stage
     */
    @FXML
    private void logOutForLogIn() {
        Stage stage = (Stage) go.getScene().getWindow();
        stage.close();
        showNewStage("/Viewer/Entry.fxml", "Log in", 600, 400);
    }

    /**
     * Change to UserTap.fxml from existing stage
     */
    @FXML
    private void returnPre() {
        Stage stage = (Stage) account.getScene().getWindow();
        stage.close();
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        showNewStage("/Viewer/UserTap.fxml", "*" + name + "* with" + " e-mail %" + email + "%", 600, 400);
    }
    //--------------------------------------------------------------------------------------
    //---------------------------------Entry  Methods---------------------------------------

    /**
     * This method shows a new window for type in logging information
     *
     * @param event push button action
     */
    @FXML
    private void logIn(ActionEvent event) {
        String action = ((Button) event.getSource()).getText();
        Stage stage = (Stage) createNewAccount.getScene().getWindow();
        stage.close();
        if (action.equals("Create new Account")) {
            showNewStage("/Viewer/ActionLogIn.fxml", "Creating new account", 400, 150);
        } else if (action.equals("Login existing Account")) {
            showNewStage("/Viewer/ActionLogIn.fxml", "Logging to existing account", 400, 150);
        }
    }

    /**
     * This method log in existing or create new account for user
     */
    @FXML
    private void creatingAccount() {
        Stage stage = (Stage) go.getScene().getWindow();
        String name = accountName.getText();
        String emailAdd = email.getText();
        if (stage.getTitle().equals("Creating new account")) {
            if (!TransInfo.getInstance().getUserList().hasCardholder(name, emailAdd)) {
                Cardholder newOne = new Cardholder(name, emailAdd);
                TransInfo.getInstance().getUserList().addCardholder(newOne);
                showNewStage("/Viewer/UserTap.fxml", "New user *" + name + "* with" + " e-mail %"
                        + emailAdd + "% have logged in", 600, 400);
                stage.close();
            } else {
                showAlert("WARNING", "failed", "Account already exist");
            }
        } else {
            if (TransInfo.getInstance().getUserList().hasCardholder(name, emailAdd)) {
                showNewStage("/Viewer/UserTap.fxml", "Old user *" + name +
                        "* with" + " e-mail %" + emailAdd + "% have logged in", 600, 400);
                stage.close();
            } else {
                showAlert("WARNING", "failed", "Such User doesn't exist!");
            }
        }
    }

    /**
     * This method log in existing or create new account for admin user
     */
    @FXML
    private void creatingUser() {
        Stage stage = (Stage) go.getScene().getWindow();
        String name = accountName.getText();
        String emailAdd = email.getText();
        if (stage.getTitle().equals("Creating new account")) {
            if (!TransInfo.getInstance().getUserList().hasAdmin(name, emailAdd)) {
                AdminUser newOne = new AdminUser(name, emailAdd);
                TransInfo.getInstance().getUserList().addAdmin(newOne);
                showNewStage("/Viewer/AdminStage.fxml", "New admin user *" + name + "* with" + " e-mail %"
                        + emailAdd + "% have logged in", 501, 575);
                stage.close();
            } else {
                showAlert("WARNING", "failed", "Account already exist");
            }
        } else {
            if (TransInfo.getInstance().getUserList().hasAdmin(name, emailAdd)) {
                showNewStage("/Viewer/AdminStage.fxml", "Old admin user *" + name +
                        "* with" + " e-mail %" + emailAdd + "% have logged in", 501, 575);
                stage.close();
            } else {
                showAlert("WARNING", "failed", "Such Admin User doesn't exist!");
            }
        }
    }
    //----------------------------------------------------------------------------------------
    //-------------------------------User Tap Methods------------------------------------------

    /**
     * This method show different window for tap in/out/demo based on which button being pressed
     *
     * @param event push button
     */
    @FXML
    private void tapAction(ActionEvent event) {
        Stage stage = (Stage) account.getScene().getWindow();
        String name = showNameEmail("*", account);
        String email = showNameEmail("*", account);
        String buttonN = ((Button) event.getSource()).getText();
        int firstAnd = stage.getTitle().indexOf("&");
        if (firstAnd != -1) {
            int secondAnd = stage.getTitle().lastIndexOf("&");
            String cardID = stage.getTitle().substring(firstAnd + 1, secondAnd);
            switch (buttonN) {
                case "TAP IN":
                    showNewStage("/Viewer/ActionTapIn.fxml", "*" + name + "* %" +
                            email + "% is operating on card &" + cardID + "&", 400, 420);
                    break;
                case "TAP OUT":
                    showNewStage("/Viewer/ActionTapOut.fxml", "*" + name + "* %" + email +
                            "%is operating on card &" + cardID + "&", 400, 420);
                    break;
                case "Demo":
                    showNewStage("/Viewer/ActionDemo.fxml", "*" + name + "*%" + email + "%is operating on card &"
                            + cardID + "&", 400, 500);
                    break;
            }
        } else {
            showAlert("WARNING", "WARNING", "WARNING");
        }
    }

    /**
     * This method shows map text
     *
     * @throws Exception For BufferedReader
     */
    @FXML
    private void setMap() throws Exception {
        StringBuilder routes = new StringBuilder();
        BufferedReader fileReader =
                new BufferedReader(new FileReader("PublicTransStations.txt"));
        String line = fileReader.readLine();
        while (line != null) {
            routes.append(line).append("\n");
            line = fileReader.readLine();
        }
        map.setText(routes.toString());
    }

    /**
     * This method tap in/tap out/demo trips
     *
     * @param event pressed button
     * @throws Exception some exception
     */
    @FXML
    private void getInOutDemo(ActionEvent event) throws Exception {
        Stage stage = (Stage) tapButton.getScene().getWindow();
        String name = showNameEmail("*", tapButton);
        String email = showNameEmail("%", tapButton);
        String cardID = showNameEmail("&", tapButton);
        String action = ((Button) event.getSource()).getText();
        Station stOne = PublicTrans.getStation(startingStation.getText(), stopType.getText());
        if (stOne == null) {
            showAlert("WARNING", "No such stop/station",
                    "Please type in the Bur or Subway for type and correct stop name!");
        } else {
            switch (action) {
                case "Get in":
                    TransInfo.getInstance().getUserList().getHolderWQ(name, email).
                            getCard(Integer.parseInt(Objects.requireNonNull(cardID))).startNewTrip(stOne, TransInfo.getInstance().getAdminInfo());
                    stage.close();
                    break;
                case "Get out":
                    TransInfo.getInstance().getUserList().getHolderWQ(name, email).
                            getCard(Integer.parseInt(Objects.requireNonNull(cardID))).endTheTrip(stOne, TransInfo.getInstance().getAdminInfo());
                    stage.close();
                    break;
                case "Begin Demo":
                    Station stTwo = PublicTrans.getStation(stationTwo.getText(), stopTypeTwo.getText());
                    Station stThree = PublicTrans.getStation(stationThree.getText(), stopTypeThree.getText());
                    Station stFour = PublicTrans.getStation(stationFour.getText(), stopTypeFour.getText());

                    if (stTwo == null && stThree == null && stFour == null) {
                        showAlert("WARNING", "No such stop/station",
                                "Please type in the Bur or Subway for type and correct stop name!");
                    } else {
                        TransInfo.getInstance().getUserList().getHolderWQ(name, email).getCard(Integer.parseInt(Objects.requireNonNull(cardID)))
                                .demo(stOne, stTwo, stThree, stFour);
                        stage.close();
                    }
            }
        }
    }

    //---------------------------------------------------------------------------------------------
    // -----------------------------User Account Methods---------------------------------------------------

    /**
     * This method applies a new card for cardholder
     */
    @FXML
    private void apply() {
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        TransInfo.getInstance().getUserList().getHolderWQ(name, email).addTravelCard();
        int num = TransInfo.getInstance().getUserList().getHolderWQ(name, email).getLastCard();
        showAlert("INFORMATION", "Success", "You have applied for a new card with num " + num);
    }

    /**
     * This method views recent trip on text area
     */
    @FXML
    private void viewRecent() {
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        String record = TransInfo.getInstance().getUserList().getHolderWQ(name, email).getRecentTrip();
        cards.setText(record);
    }

    /**
     * This method shows a new window for adding balance
     */
    @FXML
    private void addBalance() {
        Stage stage = (Stage) account.getScene().getWindow();
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        int firstAnd = stage.getTitle().indexOf("&");
        if (firstAnd != -1) {
            String cardID = showNameEmail("&", account);
            showNewStage("/Viewer/ActionAddMoney.fxml", "*" + name + "* with" + " e-mail %" +
                    email + "% is operating on card &" + cardID + "&", 400, 130);
        } else {
            showAlert("WARNING", "failed",
                    "Please first type in targeted card number and press Choose!");
        }
    }

    /**
     * Add 10 to travel card
     *
     * @throws Exception some exception
     */
    @FXML
    private void add10Balance() throws Exception {
        addAllBalance(10);
    }

    /**
     * Add 20 to travel card
     *
     * @throws Exception some exception
     */
    @FXML
    private void add20Balance() throws Exception {
        addAllBalance(20);
    }

    /**
     * Add 50 to travel card
     *
     * @throws Exception some exception
     */
    @FXML
    private void add50Balance() throws Exception {
        addAllBalance(50);
    }

    /**
     * This method view balance for a specific travel card
     */
    @FXML
    private void viewBalance() {
        Stage stage = (Stage) account.getScene().getWindow();
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        int firstAnd = stage.getTitle().indexOf("&");
        if (firstAnd != -1) {
            String cardID = showNameEmail("&", account);
            cards.setText(TransInfo.getInstance().getUserList().getHolderWQ(name, email)
                    .getCard(Integer.parseInt(Objects.requireNonNull(cardID))).getCardBalance());
        } else {
            showAlert("WARNING", "failed", "Please first type in " +
                    "targeted card number and press Choose!");
        }
    }

    /**
     * This method shows a window for typing new name
     */
    @FXML
    private void change() {
        Stage stage = (Stage) account.getScene().getWindow();
        stage.close();
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        showNewStage("/Viewer/ActionChangeName.fxml", "*" + name + "* with e-mail address %" +
                email + "%", 400, 150);
    }

    /**
     * This method changes name
     */
    @FXML
    private void setName() {
        Stage stage = (Stage) set.getScene().getWindow();
        String oldName = showNameEmail("*", set);
        String email = showNameEmail("%", set);
        if (name.getText() != null) {
            TransInfo.getInstance().getUserList().getHolderWQ(oldName, email).changeName(name.getText());
            stage.close();
            showNewStage("/Viewer/UserAccount.fxml", "*" + name.getText() + "* with e-mail %" + email + "%", 600, 400);
        } else {
            showAlert("WARNING", "failed", "Please type in your new name!");
        }
    }

    /**
     * This method removes a travel card
     */
    @FXML
    private void remove() {
        Stage stage = (Stage) account.getScene().getWindow();
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        int firstAnd = stage.getTitle().indexOf("&");
        if (firstAnd != -1) {
            String cardID = showNameEmail("&", account);
            TransInfo.getInstance().getUserList().getHolderWQ(name, email).removeCard(Integer.parseInt(Objects.requireNonNull(cardID)));
            showAlert("INFORMATION", "Success", "You have successfully removed card" + cardID);
            stage.setTitle("*" + name + "* with e-mail %" + email + "%");
        } else {
            showAlert("WARNING", "failed",
                    "Please first type in targeted card number and press Choose!");
        }
    }

    /**
     * This method deactivates a travel card
     */
    @FXML
    private void deactivating() {
        Stage stage = (Stage) account.getScene().getWindow();
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        int firstAnd = stage.getTitle().indexOf("&");
        if (firstAnd != -1) {
            String cardID = showNameEmail("&", account);
            TransInfo.getInstance().getUserList().getHolderWQ(name, email).getCard(Integer.parseInt(Objects.requireNonNull(cardID))).deactivate();
            showAlert("INFORMATION", "Success", "You have successfully deactivated card" + cardID);
        } else {
            showAlert("WARNING", "failed",
                    "Please first type in targeted card number and press Choose!");
        }
    }

    /**
     * This method views monthly cost
     */
    @FXML
    private void viewMonthlyCost() {
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        cards.setText(TransInfo.getInstance().getUserList().getHolderWQ(name, email).trackAverageCost());
    }

    //----------------------------------------------------------------------------------------
    //------------------------------All can use methods----------------------------------------

    /**
     * Show a Warning
     */
    @FXML
    private void warning() {
        showAlert("WARNING", "No access", "Please first sign in or create a new account!");
    }

    /**
     * Shows travel cards this card holder has
     */
    @FXML
    private void showCards() {
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        StringBuilder allCard = TransInfo.getInstance().getUserList().getHolderWQ(name, email).allCards();
        cards.setText(allCard.toString());
    }

    /**
     * Choose a specific travel card
     */
    @FXML
    private void chooseCard() {
        Stage stage = (Stage) account.getScene().getWindow();
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        if (!whichCard.getText().equals("")) {
            if (TransInfo.getInstance().getUserList().getHolderWQ(name, email)
                    .getCard(Integer.valueOf(whichCard.getText())) != null) {
                int x = Integer.valueOf(whichCard.getText());
                stage.setTitle(
                        "*" + name + "* with" + " e-mail %" + email + "% is operating on card &" + x + "&");
                showAlert("INFORMATION", "Success", "You have successfully chosen card" + x);
            } else {
                showAlert("WARNING", "No such card",
                        "Please type in targeted card number and press Choose!");
            }
        } else {
            showAlert("WARNING", "No such card",
                    "Please type in targeted card number and press Choose!");
        }
    }

    /**
     * Show announcement
     */
    @FXML
    private void showAnnouncement() {
        showAlert("Announcement", "Announcement", TransInfo.getInstance().getAdminInfo().getAnnouncement());
    }

    /**
     * Shows a pie chart containing information regarding the ration between taking bus trip and subway trip
     */
    @FXML
    private void showPieChart() {
        String name = showNameEmail("*", account);
        String email = showNameEmail("%", account);
        Stage stage = new Stage();
        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setTitle("3O Recent Recent Trips' Types");
        stage.setWidth(500);
        stage.setHeight(500);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Bus Trips", TransInfo.getInstance().getUserList().getHolderWQ(name, email).getNumBusTrips()),
                        new PieChart.Data("Subway Trips", TransInfo.getInstance().getUserList().getHolderWQ(name, email).getNumSubwayTrips()));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("3O Recent Recent Trips' Types");

        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                        double total = 0;
                        for (PieChart.Data d : chart.getData()) {
                            total += d.getPieValue();
                        }
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        String text = String.format("%.1f%%", 100 * data.getPieValue() / total);
                        caption.setText(text);
                    }
            );
        }
        root.getChildren().addAll(chart, caption);
        stage.setScene(scene);
        stage.show();
    }

}
