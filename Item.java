import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
/**
 * This class represents an Item class
 * @author Yotam Mosinzon
 * @version 1.00
 */
public abstract class Item {

    private double damage;
    private double range;
    private double hitPoints;
    private boolean alive;
    private Shape body;
    /**
    * Creates an item
    * @param dps the damage
    * @param ra the range
    * @param hp the hitPoints
    * @param b the body
    */
    public Item(double dps, double ra, double hp, Shape b) {
        damage = dps;
        range = ra;
        hitPoints = hp;
        alive = true;
        body = b;
    }
    /**
    * Creates a default item of a circle shape
    * @param dps the damage
    * @param ra the range
    * @param hp the hitPoints
    */
    public Item(double dps, double ra, double hp) {
        this(dps, ra, hp, new Circle());
    }
    /**
    * This method attacks the item specified
    * @param target the item
    */
    public void attack(Item target) {
        target.hitPoints -= this.damage;
        if (target.hitPoints <= 0) {
            target.hitPoints = 0;
            target.alive = false;
        }
    }
    /**
    * @return the damage
    */
    public double getdamage() {
        return damage;
    }
    /**
    * @return the range
    */
    public double getRange() {
        return range;
    }
    /**
    * @return true if alive
    */
    public boolean isAlive() {
        return alive;
    }
    /**
    * @return the shape
    */
    public Shape getBody() {
        return body;
    }
    /**
    * @return the x position
    */
    public double getXPosition() {
        return body.getTranslateX();
    }
    /**
    * @return the y position
    */
    public double getYPosition() {
        return body.getTranslateY();
    }
    /**
    * This method relocates the x value.
    * @param newX the new x value
    */
    public void relocateX(double newX) {
        body.setTranslateX(newX);
    }
    /**
    * This method relocates the y value.
    * @param newY the new y value
    */
    public void relocateY(double newY) {
        body.setTranslateY(newY);
    }
    /**
    * @param node the new shape
    */
    public void setBody(Shape node) {
        body = node;
    }
    /**
    * This method checks if an item is in range from this item
    * and ignores the spawning location.
    * @param item the item
    * @return true or false
    */
    public boolean inRange(Item item) {
        return !((getXPosition() == 0.0 && getYPosition() == 0.0) || (item.getXPosition() == 0.0 && item.getYPosition() == 0.0)) && 
            range >= Math.sqrt(Math.pow((getXPosition() - item.getXPosition()), 2) + Math.pow((getYPosition() - item.getYPosition()), 2));
    }
    /**
    * @param newDps the new damage
    */
    public void setDps(double newDps) {
        damage = newDps;
    }
    /**
    * @return the hit points of the item
    */
    public double getHitPoints() {
        return hitPoints;
    }
    
    public void printLocation() {
        System.out.println("X: " + this.getXPosition() + " Y: " + this.getYPosition());
    }
}
