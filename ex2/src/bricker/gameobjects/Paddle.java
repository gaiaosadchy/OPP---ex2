package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 300f;
    private UserInputListener inputListener;
    private final Vector2 windowDim;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener,
                  Vector2 windowDim) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDim = windowDim;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));

        //check validity
        //left side
        if (getTopLeftCorner().x() < 0) {
            setTopLeftCorner(new Vector2(0, getTopLeftCorner().y()));
        }

        //right side
        if (getTopLeftCorner().x() + 100 > this.windowDim.x()) { //TODO: change 100
            setTopLeftCorner(new Vector2(this.windowDim.x() - 100, getTopLeftCorner().y()));
        }
    }
}
