package org.csc133.a3.main;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.gameobjects.*;
import org.csc133.a3.gameobjects.strategies.TargetPlayerStrategy;
import org.csc133.a3.gameobjects.strategies.TargetSkyScraperStrategy;
import org.csc133.a3.sound.BGSound;
import org.csc133.a3.sound.Sound;
import org.csc133.a3.views.GlassCockpit;
import org.csc133.a3.views.ITimer;
import org.csc133.a3.views.MapView;

import java.util.ArrayList;
import java.util.Random;

/**
 * The GameWorld class serves as the primary data model of the MVC architecture. Stores all necessary information about
 * the game state.
 */
public class GameWorld {
    private static final int NUMBER_OF_SKYSCRAPERS = 5;
    private static final int NUMBER_OF_BIRDS = 5;
    private static final int NUMBER_OF_REFUELING_BLIMPS = 4;
    private static final int NUMBER_OF_NPH = 3;
    private static final int NPH_RADIUS = 500;
    private static final int INITIAL_PLAYER_LIVES = 3;
    private static Location minLocation = new Location(0, 0);
    private static Location maxLocation = new Location(0, 0);
    private final MapView map;
    private final GlassCockpit hud;
    private final ITimer gameTime;
    private GameObjectCollection gameObjectCollection = new GameObjectCollection();
    private Location startLocation;
    private BGSound bgSound;

    public static Sound helicopterCollisionSound;
    public static Sound blimpCollisionSound;
    public static Sound birdCollisionSound;
    public static Sound lossOfLifeSound;

    /**
     * Instantiates a new Game world.
     *
     * @param map      the map
     * @param hud      the hud
     * @param gameTime the game time
     */
    public GameWorld(MapView map, GlassCockpit hud, ITimer gameTime) {
        this.map = map;
        this.hud = hud;
        minLocation = map.getMinLocation();
        maxLocation = map.getMaxLocation();
        this.gameTime = gameTime;
        bgSound = new BGSound("/background.wav");
    }

    /**
     * Returns a reference to the GameObjectCollection
     *
     * *Note: I would be happier if this returned a copy, but the Commands that are stored in the Game class have to
     * have references to the game objects. Without a function that returns a reference, the commands would be operating
     * on copies instead of the objects that appear in the MapView. I feel like there must be a solution, but I'm not
     * sure what it is at this time.
     *
     * @return the game object collection
     */
    public GameObjectCollection getGameObjectCollection() {
        return gameObjectCollection;
    }

    /**
     * Initializes the GameWorld.
     */
    protected void init() {
        initSkyScrapers();
        System.out.print("Skyscrapers initialized.\n");
        System.out.print("Initializing Blimps... ");
        initRefuelingBlimps();
        System.out.print("Blimps Initialized.\n");
        System.out.print("Initializing Player... ");
        initPlayer();
        System.out.print("Player Initialized.\n");
        System.out.print("Initializing Non-player Helicopters... ");
        initNonPlayerHelicopters();
        System.out.print("Non-player Helicopters Initialized.\n");
        System.out.print("Initializing Birds... ");
        initBirds();
        System.out.print("Birds Initialized.\n");
        generateCliMap();
        map.update(getGameObjectCollection());
        hud.update(getGameObjectCollection());

        bgSound.setVolume(50);
        bgSound.play();
        helicopterCollisionSound = new Sound("/crash.wav");
        blimpCollisionSound = new Sound("/charge.wav");
        birdCollisionSound = new Sound("/splat.wav");
        lossOfLifeSound = new Sound("/die.wav");
        helicopterCollisionSound.setVolume(50);
        blimpCollisionSound.setVolume(25);
        birdCollisionSound.setVolume(50);
        lossOfLifeSound.setVolume(50);
    }

    /**
     * Returns the minimum (x, y) location that fits within the bounds of the MapView of the GameWorld
     *
     * @return the min location
     */
    public static Location getMinLocation() {
        return new Location(minLocation);
    }

    /**
     * Returns the maximum (x, y) location that fits within the bounds of the MapView of the GameWorld
     *
     * @return the max location
     */
    public static Location getMaxLocation() {
        return new Location(maxLocation);
    }

    /**
     * Exit.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Tick game clock.
     *
     * @param elapsedTimeMillis the elapsed time in milliseconds since the last game clock tick.
     */
    public void tickClock(long elapsedTimeMillis) {
        PlayerHelicopter p = gameObjectCollection.findPlayer();
        Object objectToRemove = null;

        if (p.getDamageLevel() >= 100 || p.getFuelLevel() <= 0) {
            if (p.getNumberOfLives() > 1)
                loseLife();
            else
                gameOver(false);
        } else if (p.getLastSkyScraperReached() == getNumberOfSkyscrapers()) {
            gameOver(true);
        }
        for (GameObject o : gameObjectCollection) {
            if (o instanceof MovableGameObject)
                ((MovableGameObject) o).move(elapsedTimeMillis);
            if (o instanceof NonPlayerHelicopter && ((NonPlayerHelicopter) o).getDamageLevel() > 100)
                objectToRemove = o;
        }
        if (objectToRemove != null) {
            gameObjectCollection.remove(objectToRemove);
        }

        map.update(getGameObjectCollection());
        hud.update(getGameObjectCollection());

        checkForCollisions();

    }

    /**
     * Output a textual representation of the GameWorld to the CLI
     */
    public void generateCliMap() {
        System.out.printf("MapView: From: (%d, %d) to (%d, %d)\n",
                (int) map.getMinLocation().getX(), (int) map.getMinLocation().getY(),
                (int) map.getMaxLocation().getX(), (int) map.getMaxLocation().getY());
        for (GameObject object : gameObjectCollection) {
            System.out.println(object.toString());
        }
    }

    private void initBirds() {
        for (int i = 0; i < GameWorld.NUMBER_OF_BIRDS; ++i) {
            Bird birdToAdd = new Bird(null);
            Location potentialBirdLocation;
            do {
                potentialBirdLocation = Location.generateRandomLocationWithinBounds(minLocation, maxLocation);
            } while (checkCollidesWithExistingObjects(potentialBirdLocation, birdToAdd.getSize()));
            birdToAdd.place(potentialBirdLocation);
            gameObjectCollection.add(birdToAdd);
        }
    }

    private void initRefuelingBlimps() {
        for (int i = 0; i < GameWorld.NUMBER_OF_REFUELING_BLIMPS; ++i) {
            RefuelingBlimp blimpToAdd = new RefuelingBlimp(null);
            Location potentialBlimpLocation;
            do {
                potentialBlimpLocation = Location.generateRandomLocationWithinBounds(minLocation, maxLocation);
            } while (checkCollidesWithExistingObjects(potentialBlimpLocation, blimpToAdd.getSize()));
            blimpToAdd.place(potentialBlimpLocation);
            gameObjectCollection.add(blimpToAdd);
        }
    }

    private void initSkyScrapers() {
        System.out.print("Initializing Skyscrapers... (");
        for (int i = 0; i < GameWorld.NUMBER_OF_SKYSCRAPERS; ++i) {
            SkyScraper toPlace = new SkyScraper(null);
            Location potentialSkyscraperLocation;
            do {
                potentialSkyscraperLocation = Location.generateRandomLocationWithinBounds(minLocation, maxLocation);
            } while (checkCollidesWithExistingObjects(potentialSkyscraperLocation, toPlace.getSize()));
            toPlace.place(potentialSkyscraperLocation);
            gameObjectCollection.add(toPlace);
        }
    }

    private void initPlayer() {
        SkyScraper firstSkyScraper = null;
        for (GameObject potentialFirstSkyscraper : gameObjectCollection) {
            if (potentialFirstSkyscraper instanceof SkyScraper && ((SkyScraper) potentialFirstSkyscraper).getSequenceNumber() == 1) {
                firstSkyScraper = (SkyScraper) potentialFirstSkyscraper;
            }
        }
        startLocation = firstSkyScraper.getLocation();
        PlayerHelicopter playerHelicopter = PlayerHelicopter.getInstance(startLocation, INITIAL_PLAYER_LIVES);
        gameObjectCollection.add(playerHelicopter);
    }

    private void initNonPlayerHelicopters() {
        Location playerLocation = gameObjectCollection.findPlayer().getLocation();
        Location nonPlayerHelicopterLocation;
        for (int i = 0; i < NUMBER_OF_NPH; ++i) {
            NonPlayerHelicopter bogie = new NonPlayerHelicopter(null, gameObjectCollection);
            do {
                nonPlayerHelicopterLocation = Location.generateRandomLocationWithinBounds(minLocation, maxLocation, playerLocation, NPH_RADIUS);
            } while (!Location.checkWithinBounds(nonPlayerHelicopterLocation, minLocation, maxLocation)
                    && !checkCollidesWithExistingObjects(nonPlayerHelicopterLocation, bogie.getSize()));

            bogie.place(nonPlayerHelicopterLocation);
            gameObjectCollection.add(bogie);
            if (i % 2 == 0)
                bogie.setStrategy(new TargetPlayerStrategy());
            else
                bogie.setStrategy(new TargetSkyScraperStrategy());
        }
    }

    private boolean checkCollidesWithExistingObjects(Location location, int size) {
        boolean collisionExists = false;
        if (gameObjectCollection.size() == 0) {
            return false;
        }
        for (GameObject existingObject : gameObjectCollection) {
            if (existingObject.checkCollidesWith(location, size)) {
                collisionExists = true;
            }
        }
        return collisionExists;
    }

    private void loseLife() {
        GameWorld.lossOfLifeSound.play();
        PlayerHelicopter p = gameObjectCollection.findPlayer();
        p.decrementLives();
        p.setLocation(startLocation);
        p.setFuelLevel(10000);
        pause();
        Dialog.show("You have lost a life", "Press accept to continue.", new Command("Accept") {});
        unpause();
    }

    /**
     * Gets number of skyscrapers.
     *
     * @return the number of skyscrapers
     */
    public int getNumberOfSkyscrapers() {
        return NUMBER_OF_SKYSCRAPERS;
    }

    private void resetGame() {
        gameObjectCollection.clear();
        SkyScraper.resetCount();
        gameTime.resetElapsedTime();
        init();
    }

    private void gameOver(boolean winner) {
        String winTitle = "Congrats, winner!!!";
        String loseTitle = "Game Over";

        String title;
        String body = "Play Again?";

        if (winner)
            title = winTitle;
        else
            title = loseTitle;

        Dialog.show(title, body, new Command("Yes") {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        }, new Command("No") {
            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
    }

    /**
     * Pause.
     */
    public void pause() {
        gameTime.stopElapsedTime();
    }

    /**
     * Unpause.
     */
    public void unpause() {
        gameTime.startElapsedTime();
    }

    private void checkForCollisions() {
        for (int i = 0; i < gameObjectCollection.size() - 1; ++i) {
            for (int j = i + 1; j < gameObjectCollection.size(); ++j) {
                if (gameObjectCollection.get(i).checkCollidesWith(gameObjectCollection.get(j))) {
                    gameObjectCollection.get(i).handleCollision(gameObjectCollection.get(j));
                }
            }
        }
    }
}