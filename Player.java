/**
 * This class represents an Item class
 * @author Yotam Mosinzon
 * @version 1.00
 */
public class Player {

    private int money;
    /**
    * Creates a player
    */
    public Player() {
        money = 100;
    }
    /**
    * This method pays an amount of money
    * @param amount the amount
    */
    public void pay(int amount) {
        money -= amount;
    }
    /**
    * This method buys a tower
    */
    public void buyTower() {
        pay(40);
    }
    /**
    * This method indicates that an enemy was slain
    */
    public void enemyKilled() {
        money += 5;
    }
    /**
    * @return the current amount of money
    */
    public int getMoney() {
        return money;
    }
}
