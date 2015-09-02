import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.geometry.Insets;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.PathTransition;
import javafx.animation.KeyFrame;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import java.util.ArrayList;
import java.io.FileNotFoundException;
/**
 * This class represents an Item class
 * @author Yotam Mosinzon
 * @version 1.00
 */
public class TowerDefense extends Application {

    private static double deltat = 100;
    private static double totalTime = 30000;

    private static double sceneWidth = 1200;
    private static double sceneHeight = 800;

    private Fort fort;
    private ArrayList<Tower> towers;
    private ArrayList<Enemy> currentWave;
    private ArrayList<Enemy> currentEnemies;

    private Timeline timeline;
    private EnemyGenerator enemyGenerator;
    private Path path;
    private Tower towerToPlace;
    private Player player;
    private TextField moneyDisplay;
    private TextField fortLife;

    private Stage stage;
    private Group grp;
    private BorderPane bp;
    /**
    * This method presents that the game has ended
    * @param stage the stage of the game
    */
    public void gameOver(Stage stage) {
        timeline.stop();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("You have lost!");
        alert.showAndWait();
        System.exit(0);
    }
    /**
    * This method presents that the game has won
    * @param stage the stage of the game
    */
    public void gameWon(Stage stage) {
        timeline.stop();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Victory");
        alert.setHeaderText(null);
        alert.setContentText("You have Won!");
        alert.showAndWait();
        System.exit(0);
    }
    /**
    * This method checks the hits of the towers every deltat
    */
    public void checkHits() {
        if (!currentEnemies.isEmpty()) {
            ArrayList<Enemy> deadEnemies = new ArrayList<>();
            for (Enemy e: currentEnemies) {
                for (Tower t: towers) {
                    if (t.inRange(e))
                        t.attack(e);
                }
                if (fort.inRange(e))
                    fort.attack(e);
                if (e.inRange(fort))
                    e.attack(fort);
                if (!e.isAlive())
                    deadEnemies.add(e);
            }
            for (Enemy e: deadEnemies) {
                player.enemyKilled();
                removeEnemyFromGame(e);
            }
        }
        if (!fort.isAlive())
            gameOver(stage);
    }
    /**
    * This method adds an enemy to the wave
    * @param e the enemy
    * @param diff the delay between the enemies as they come
    */
    private void addEnemyToGame(Enemy e, double diff) {
        currentEnemies.add(e);

        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(totalTime));
        pt.setDelay(Duration.millis(diff));
        pt.setPath(path);
        pt.setNode(e.getBody());
        grp.getChildren().add(e.getBody());

        pt.play();
    }
    /**
    * This method removes an enemy from the game
    * @param e the enemy
    */
    private void removeEnemyFromGame(Enemy e) {
        currentEnemies.remove(e);
        grp.getChildren().remove(e.getBody());
    }
    /**
    * This method starts the motion of the game and regulates it
    * @param stage the stage of the game
    */
    @Override public void start(Stage stage) {
        this.player = new Player();
        this.towers = new ArrayList<Tower>();
        this.currentWave = new ArrayList<Enemy>();
        Ellipse eli = new Ellipse(500, 400, 30, 20);
        eli.setFill(Color.BROWN);
        this.fort = new Fort(1, 50, 40000, new Ellipse(500, 400, 30, 20));
        fort.relocateX(500);
        fort.relocateY(400);
        this.currentEnemies = new ArrayList<Enemy>();
        this.grp = new Group();
        Rectangle background = new Rectangle(sceneWidth - 200, sceneHeight);
        background.setFill(Color.WHITE);
        grp.getChildren().add(background);

        Button sendWave = new Button("Send Wave");
        Button buildTower = new Button("Build Tower");
        fortLife = new TextField("Fort life: " + fort.getHitPoints());
        moneyDisplay = new TextField("Current money: "
            + player.getMoney());

        try {
            enemyGenerator = new EnemyGenerator("source.txt");
            sendWave.setOnAction((event) -> {
                    try {
                        enemyGenerator.generateWave();
                        currentWave = enemyGenerator.getWave();
                    } catch (FileNotFoundException e) {
                        System.out.println("Wrong file!");
                        System.exit(0);
                    }
                });
        } catch (FileNotFoundException e) {
            System.out.println("Wrong file!");
            System.exit(0);
        }

        buildTower.setOnAction((event) -> {
                if (player.getMoney() >= 40) {
                    player.buyTower();
                    Tower tower = new Tower(1, 150, 0, 0);
                    this.grp.getChildren().add(tower.getBody());
                    grp.setOnMouseMoved(e -> {
                            tower.relocateX(e.getX());
                            tower.relocateY(e.getY());
                        });
                    towerToPlace = tower;
                }
            });

        grp.setOnMouseClicked(e -> {
                if (towerToPlace != null) {
                    grp.setOnMouseMoved(event -> doNothing());
                    this.towers.add(towerToPlace);
                    towerToPlace = null;
                }
            });

        VBox menuPane = new VBox(40.0);
        menuPane.setPadding(new Insets(50, 0, 0, 30));
        menuPane.getChildren().addAll(sendWave, buildTower, moneyDisplay, fortLife);

        bp = new BorderPane();
        bp.setCenter(grp);
        bp.setLeft(menuPane);
        stage.setScene(new Scene(bp, sceneWidth, sceneHeight));

        setUpPath();

        timeline = new Timeline(new KeyFrame(Duration.millis(deltat),
            ae -> {
                checkHits();
                if (!currentWave.isEmpty()) {
                    double diff = 0.0;
                    for (Enemy e: currentWave) {
                        addEnemyToGame(e, diff);
                        diff += 200.0;
                    }
                    currentWave.clear();
                }
                moneyDisplay.setText("Current money: " + player.getMoney());
                fortLife.setText("Fort life: " + fort.getHitPoints());
                if (!enemyGenerator.isGameOn()
                    && currentEnemies.isEmpty()) {
                    gameWon(stage);
                }
            }));

        timeline.setCycleCount(Animation.INDEFINITE);

        grp.getChildren().addAll(path, eli);

        stage.show();
        timeline.play();
    }
    /**
    * This method sets a path for the enemies
    */
    private void setUpPath() {
        path = new Path();
        path.setStrokeWidth(3);
        path.setStroke(Color.YELLOW);
        path.getElements().add(new MoveTo(100, 100));
        path.getElements().add(new LineTo(900, 100));
        path.getElements().add(new LineTo(900, 700));
        path.getElements().add(new LineTo(100, 700));
        path.getElements().add(new LineTo(100, 250));
        path.getElements().add(new LineTo(750, 250));
        path.getElements().add(new LineTo(750, 500));
        path.getElements().add(new LineTo(250, 500));
        path.getElements().add(new LineTo(250, 375));
        path.getElements().add(new LineTo(500, 375));
    }
    /**
    * This method does nothing
    */
    public void doNothing() {
        int a = 0;
    }
}
