import javafx.scene.shape.Ellipse;
import javafx.scene.paint.Color;
/**
 * This class represents an Item class
 * @author Yotam Mosinzon
 * @version 1.00
 */
public class Fort extends Item {
    /**
    * Creates a fort
    * @param d the damage
    * @param ra the range
    * @param hp the hitPoints
    * @param x the x position
    * @param y the y position
    */
    public Fort(double d, double ra, double hp, Ellipse e) {
        super(d, ra, hp, e);
    }
}
