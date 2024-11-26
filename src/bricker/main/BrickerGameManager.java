package bricker.main;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class BrickerGameManager extends GameManager {

    public BrickerGameManager(String windowTitle, Vector2 windowDim) {
        super(windowTitle, windowDim);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        GameObject ball = new GameObject(Vector2.ZERO, new Vector2(50, 50), ballImage);

        gameObjects().addGameObject(ball);
    }

    public static void main(String[] args) {
        new BrickerGameManager("Bouncing Ball", new Vector2(700, 500)).run();
    }
}
