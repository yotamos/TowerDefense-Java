import javafx.scene.shape.Rectangle;
/**
 * This class represents an Item class
 * @author Yotam Mosinzon
 * @version 1.00
 */
public class Enemy extends Item {
    /**
    * Creates a tower
    * @param dps the damage
    * @param hp the hitPoints
    * @param r the body
    */
    public Enemy(double dps, double hp, Rectangle r) {
        super(dps, 200, hp, r);
    }
    
    public void print() {
        System.out.println("I'm an Enemy!");
    }
}
