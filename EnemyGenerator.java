import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import javafx.scene.shape.Rectangle;
import java.io.FileNotFoundException;
import javafx.scene.paint.Color;
/**
 * This class represents an Item class
 * @author Yotam Mosinzon
 * @version 1.00
 */
public class EnemyGenerator {

    private ArrayList<Enemy> wave;
    private boolean gameOn;
    private Scanner sc;
    /**
    * Creates an enemyGenerator
    * @param fileName the file name of the source file
    */
    public EnemyGenerator(String fileName) throws FileNotFoundException {
        wave = new ArrayList<Enemy>();
        sc = new Scanner(new File(fileName));
        gameOn= true;
    }
    /**
    * This method adds an enemy to the wave
    * @param e the enemy
    */
    public void addEnemey(Enemy e) {
        wave.add(e);
    }
    /**
    * @return the wave
    */
    public ArrayList<Enemy> getWave() {
        return wave;
    }
    public boolean isGameOn() {
        return gameOn;
    }
    /**
    * This method generates a new wave
    */
    public void generateWave() throws FileNotFoundException {
        int ni = sc.nextInt();
        while (ni != 0 && sc.hasNext() && gameOn) {
            if (ni == 1)
                wave.add(new Enemy(15, 30, new Rectangle(15, 15)));
            if (ni == 2)
                wave.add(new Enemy(25, 70, new Rectangle(20, 20, Color.DARKMAGENTA)));
            if (ni == 3)
                wave.add(new Enemy(30, 120, new Rectangle(20, 20, Color.CHOCOLATE)));
            if (ni == 4)
                wave.add(new Enemy(40, 200, new Rectangle(20, 20, Color.CORNFLOWERBLUE)));
            if (ni == 5)
                wave.add(new Enemy(50, 350, new Rectangle(20, 20, Color.DARKSLATEBLUE)));
            if (ni == 8)
                wave.add(new Enemy(200, 2500, new Rectangle(20, 20, Color.DARKGREY)));
            if (ni == 9)
                gameOn = false;
            ni = sc.nextInt();
        }
    }
}
