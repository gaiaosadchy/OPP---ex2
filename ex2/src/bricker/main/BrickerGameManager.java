package bricker.main;
import bricker.brick_strategies.BasicCollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.Color;
import java.util.Random;
import danogl.gui.SoundReader;

//TODO: start at part 1.7 - adding bricks

public class BrickerGameManager extends GameManager {

    private static final float BALL_SPEED = 200f;

    public BrickerGameManager(String windowTitle, Vector2 windowDim) {
        super(windowTitle, windowDim);
    }

    private void createBackground(ImageReader imageReader, Vector2 windowDim) {
        Renderable backgroundImage = imageReader.readImage("assets/DARK_BG2_small.jpeg", false);
        GameObject background = new GameObject(Vector2.ZERO, windowDim, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBorders(Vector2 windowDim) {
        Color pink = new Color(255, 182, 193);
        float borderThickness = 10;

        // Top-left corner for each border
        Vector2[] positions = {
                Vector2.ZERO,  // Top border
                new Vector2(0, windowDim.y() - borderThickness), // Bottom border
                Vector2.ZERO, // Left border
                new Vector2(windowDim.x() - borderThickness, 0) // Right border
        };

        Vector2[] sizes = {
                new Vector2(windowDim.x(), borderThickness), // Top border
                new Vector2(windowDim.x(), borderThickness), // Bottom border
                new Vector2(borderThickness, windowDim.y()), // Left border
                new Vector2(borderThickness, windowDim.y())  // Right border
        };

        RectangleRenderable borderImage = new RectangleRenderable(pink);
        for (int i = 0; i < positions.length; i++) {
            GameObject border = new GameObject(positions[i], sizes[i], borderImage);
            gameObjects().addGameObject(border, Layer.STATIC_OBJECTS);
        }
    }

    private void createBalls(ImageReader imageReader, Vector2 windowDim, SoundReader soundReader) {
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(50, 50), ballImage, collisionSound);

        ball.setCenter(windowDim.mult(0.5f));
        gameObjects().addGameObject(ball);

        float ballVelx = BALL_SPEED;
        float ballVely = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelx *= -1;
        }
        if (rand.nextBoolean()) {
            ballVely *= -1;
        }
        ball.setVelocity(new Vector2(ballVelx, ballVely));

    }

    private void createPaddles(ImageReader imageReader, Vector2 windowDim, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);

        GameObject userPaddle = new Paddle(Vector2.ZERO, new Vector2(100, 15), paddleImage, inputListener, windowDim);
        userPaddle.setCenter(new Vector2(windowDim.x() / 2, (int)windowDim.y()-30));
        gameObjects().addGameObject(userPaddle);
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Vector2 windowDim = windowController.getWindowDimensions();

        //creating background
        createBackground(imageReader, windowDim);

        //creating borders
        createBorders(windowDim);

        //creating ball
        createBalls(imageReader, windowDim, soundReader);

        //creating paddle
        createPaddles(imageReader, windowDim, inputListener);

        //creating big brick
        Renderable bigBrickImage = imageReader.readImage("assets/brick.png", false);

        //TODO: fix collision between borders and bricks and remove big brick
        GameObject bigBrick = new Brick(new Vector2(10, 10), new Vector2(windowDim.x() - 20, 15),
                bigBrickImage, new BasicCollisionStrategy(gameObjects()));

        gameObjects().addGameObject(bigBrick);

    }



    public static void main(String[] args) {
        new BrickerGameManager("Bouncing Ball", new Vector2(700, 500)).run();
    }
}
