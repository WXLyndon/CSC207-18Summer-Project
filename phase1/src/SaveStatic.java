import java.io.Serializable;
import java.util.ArrayList;

class SaveStatic implements Serializable {

  private static final long serialVersionUID = 1L;
  private double revenue;
  private double operatingCost;
  private ArrayList<Cardholder> cardholderList;
  private ArrayList<AdminUser> adminList;
  private int numCard;

  SaveStatic(
      int numCard,
      double revenue,
      double operatingCost,
      ArrayList<Cardholder> cardholderList,
      ArrayList<AdminUser> adminList) {
    this.numCard = numCard;
    this.revenue = revenue;
    this.operatingCost = operatingCost;
    this.cardholderList = cardholderList;
    this.adminList = adminList;
  }

  int getNumCard() {
    return numCard;
  }

  ArrayList<Cardholder> getCardholderList() {
    return cardholderList;
  }

  ArrayList<AdminUser> getAdminList() {
    return adminList;
  }

  double getRevenue() {
    return revenue;
  }

  double getOperatingCost() {
    return operatingCost;
  }


}
