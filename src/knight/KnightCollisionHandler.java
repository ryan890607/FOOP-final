package knight;

import magic.Magic;
import model.CollisionHandler;
import model.Dangerous;
import model.Direction;
import model.Sprite;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class KnightCollisionHandler implements CollisionHandler {
    @Override
    public void handle(Point originalLocation, Sprite from, Sprite to) {
        if (from instanceof Knight && to instanceof Knight) {
            Rectangle body = from.getBody();
            int offsetLeft = to.getX() - body.x;
            int offsetRight = body.x + body.width - to.getX();
            if (offsetLeft < 0) {
                to.setLocation(new Point(to.getX() - (to.getRange().width + offsetLeft) / 3, to.getY()));
            } else {
                to.setLocation(new Point(to.getX() + offsetRight / 3, to.getY()));
            }
        } else if (from instanceof Dangerous && to instanceof Knight){
            if (((Knight) to).isBeingDamaged()) { return; }
            if (from instanceof Magic) {
                ((Magic) from).inform();
            }
            if (from.getX() < to.getX())
                to.setResponseDirection(Direction.RIGHT);
            else
                to.setResponseDirection(Direction.LEFT);
            to.onDamaged(from, from.getRange(), ((Dangerous) from).getDamage());
        } else if (from instanceof Knight && to instanceof Dangerous){
            if (((Knight) from).isBeingDamaged()) { return; }
            if (to instanceof Magic){
                ((Magic) to).inform();
            }
            if (to.getX() < from.getX())
                from.setResponseDirection(Direction.RIGHT);
            else
                from.setResponseDirection(Direction.LEFT);
            from.onDamaged(to, to.getRange(), ((Dangerous) to).getDamage());
        }
    }
}
