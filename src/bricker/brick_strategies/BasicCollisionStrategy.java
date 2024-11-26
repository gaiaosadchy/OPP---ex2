package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

public class BasicCollisionStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjectCollection;

    public BasicCollisionStrategy(GameObjectCollection gameObjectCollection) {
        super();
        this.gameObjectCollection = gameObjectCollection;
    }

    @Override
    public void onCollision(GameObject collidedObject, GameObject collidingObject) {
        System.out.println("collision with brick detected");
        this.gameObjectCollection.removeGameObject(collidedObject);
    }
}
