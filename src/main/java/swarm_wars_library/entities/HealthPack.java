package swarm_wars_library.entities;

import swarm_wars_library.SwarmWars;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.map.Map;
import swarm_wars_library.map.RandomGen;
import swarm_wars_library.physics.Vector2D;
import swarm_wars_library.sound.SoundMixer;

public class HealthPack extends AbstractEntity implements ISound {

    private int addHealth = 20;

    private int healthPackId;

    private int healthPackVersion;

    private boolean playNetworkGame;

    public HealthPack(ENTITY tag, int healthPackId, boolean playNetworkGame) {
        super(tag, Map.getInstance().getTurretScale());
        this.playNetworkGame = playNetworkGame;
        this.setLocation(new Vector2D(RandomGen.getRand() * Map.getInstance().getMapWidth(),
                RandomGen.getRand() * Map.getInstance().getMapHeight()));
        this.updateCommsPacket();
        this.sendCommsPacket();
        this.setState(STATE.DEAD);
        this.healthPackId = healthPackId;
    }

    @Override
    public void update() {
        if (this.isState(STATE.DEAD)) {
            if (SwarmWars.playNetworkGame) {
                //???network ;
            } else {
                this.setState(STATE.ALIVE);
                this.setLocation(new Vector2D(RandomGen.getRand() * Map.getInstance().getMapWidth(),
                        RandomGen.getRand() * Map.getInstance().getMapHeight()));
            }
        }
        // Comms & explode last
        this.updateCommsPacket();
        this.sendCommsPacket();
        this.updateExplode2Dead();
    }
    public void setLocation(int healthPackVersion, Vector2D location){
        if(healthPackVersion >= this.healthPackVersion){
            this.setLocation(location);
            this.setState(STATE.ALIVE);
        }
    }
    //=========================================================================//
    // Comms method                                                            //
    //=========================================================================//
    @Override
    public void updateCommsPacket(){
        this.commsPacket.setLocation(this.getLocation());
        this.commsPacket.setState(this.getState());
        this.commsPacket.setVelocity(this.getVelocity());
    }
    //=========================================================================//
    // Collision method                                                        //
    //=========================================================================//
    @Override
    public void collidedWith(ENTITY tag){
        this.setState(STATE.EXPLODE);
        SoundMixer.playTurretExplosion();
        if(tag.equals(ENTITY.PLAYER1)){
            CommsGlobal.get("PLAYER1").getPacket(0).setHealth(CommsGlobal.get("PLAYER1")
                    .getPacket(0).getHealth()+addHealth);
            System.out.println("add health");
        }
        if(tag.equals(ENTITY.PLAYER2)){
            CommsGlobal.get("PLAYER2").getPacket(0).setHealth(CommsGlobal.get("PLAYER2")
                    .getPacket(0).getHealth()+addHealth);
        }
        /*if (playNetworkGame) {
            java.util.Map<String, Object> m = new java.util.HashMap<>();
            m.put(Headers.TYPE, Constants.UPDATE_TURRET);
            m.put(Headers.TURRET_ID, this.healthPackId);
            m.put(Headers.TURRET_VERSION, this.healthPackVersion);
            MessageHandlerMulti.putPackage(m);
        }*/
        this.healthPackVersion++;
    }
    @Override
    public void updateSounds(){
        // TODO add conditional sound
    }
}
