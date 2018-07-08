import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
      String fileName = "PublicTransStations.txt";
      String userList = "users";
      ArrayList<Station> publicTrans = new ArrayList<>();
      try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
          String line = fileReader.readLine();
          while (line != null) {
              String stationType = line.split(" ")[0];
              String[] stationNames = line.split("->");
              ArrayList<Station> allStation = new ArrayList<>();
              for (String s: stationNames){
                  Station newStation;
                  if (stationType.equals("Bus")){
                      newStation = new BusStop(s);
                  }else{
                      newStation = new SubwayStation(s);
                  }
                  if (!publicTrans.contains(newStation)) {
                          allStation.add(newStation);
                  }
                  else{
                      int index = publicTrans.indexOf(newStation);
                      allStation.add(publicTrans.get(index));
                  }
              }

              for (int i=0; i<allStation.size()-2; i++){
                  allStation.get(i).addRoute(allStation.get(i+1));
                  allStation.get(i+1).addRoute(allStation.get(i));
              }

              for (Station s: allStation){
                  if (!publicTrans.contains(s)){
                      publicTrans.add(s);
                  }
              }

              line = fileReader.readLine();
          }
      } catch (IOException e) {
          e.printStackTrace();
      }


      PublicTrans.allStations.addAll(publicTrans);


      try (BufferedReader fileReader = new BufferedReader(new FileReader(userList))) {
          String line = fileReader.readLine();
          while (line != null) {
              String[] userInfo = line.split(",");
              if (userInfo[0].equals("AdminUser")){
                  AdminUser newAdminUser = new AdminUser(userInfo[1], userInfo[2]);
                  User.userList.add(newAdminUser);
              }
              else{
                  Cardholder newCardholder = new Cardholder(userInfo[1], userInfo[2]);
                  User.userList.add(newCardholder);
              }
              line = fileReader.readLine();
          }
      } catch (IOException e) {
          e.printStackTrace();
      }


      //events.txt is handled here!!!
  }
}
