import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
/**
 * This class represents an Item class
 * @author Yotam Mosinzon
 * @version 1.00
 */
public class Tower extends Item {
    /**
    * Creates a tower
    * @param dps the damage
    * @param ra the range
    * @param x the x position
    * @param y the y position
    */
    public Tower(double dps, double ra, double x, double y) {
        super(dps, ra, 999999);
        Circle eli = new Circle(x, y, 15);
        eli.setFill(Color.BLUE);
        setBody(eli);
    }
    
    public void print() {
        System.out.println("I'm a Tower!");
    }
}
