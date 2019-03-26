package swarm_wars_library.engine;
import processing.core.PApplet;

/* A class to handle building specific entities, and assigning proper components */
public class EntityBuilder {

    private PApplet sketch;

    public EntityBuilder(PApplet sketch){
        this.sketch = sketch;
    }

    public Entity newPlayer(){
        Entity p = new Entity(sketch, Tag.PLAYER, 30, true, true, true, true, true, true, false);
        return p;
    }

    public Entity newBot(){
        Entity b = new Entity(sketch, Tag.P_BOT, 5, true, false, false, true, true, true, false);
        return b;
    }

    public Entity newTurret(){
        Entity t = new Entity(sketch, Tag.ENEMY, 40, true, false, true, true, true, true, true);
        return t;
    }

    public Entity newBullet(Tag bulletTag){
        Entity b = new Entity(sketch, bulletTag, 5, true, false, false, false, false, true, false);
        return b;
    }


}