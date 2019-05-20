package swarm_wars_library.graphics;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
import swarm_wars_library.comms.CommsChannel;
import swarm_wars_library.comms.CommsGlobal;
import swarm_wars_library.entities.STATE;
import swarm_wars_library.map.Map;
import swarm_wars_library.swarm_algorithms.SWARMALGORITHM;

public class RenderCurrentAlgorithm {


    private PApplet sketch;
    private float dimension;
    private float topLeftLocationX;
    private float topLeftLocationY;
    private PImage currentAlgorithm;
    private Images images = Images.getInstance();

    public RenderCurrentAlgorithm(PApplet sketch, int dimension, int topLeftLocationX, int topLeftLocationY){
        this.sketch = sketch;
        this.dimension = (float) dimension;
        this.topLeftLocationX = (float) topLeftLocationX;
        this.topLeftLocationY = (float) topLeftLocationY;
    }

    public void update(){
        this.updateCurrentAlgorithm();

        this.sketch.rectMode(PConstants.CORNER);
        this.sketch.stroke(225, 225, 225);
        this.sketch.fill(0, 0, 0);
        this.sketch.rect(
                this.topLeftLocationX,
                this.topLeftLocationY,
                this.dimension,
                this.dimension);

        this.sketch.imageMode(PConstants.CORNER);
        this.sketch.image(currentAlgorithm,
                this.topLeftLocationX,
                this.topLeftLocationY,
                this.dimension,
                this.dimension
        );

    }

    public void updateCurrentAlgorithm() {

        String commsChannel = "PLAYER" + Map.getInstance().getPlayerId() + "_BOT";
        CommsChannel cc = CommsGlobal.get(commsChannel);
        int i = 0;
        while(cc.getPacket(i).getState().equals(STATE.DEAD) && i < cc.getNumberOfReceivers()){
            i++;
        }

        SWARMALGORITHM swarmAlgorithm = cc.getPacket(i).getSwarmAlgorithm();
        switch(swarmAlgorithm){
            case SPECIALSUICIDE:
                currentAlgorithm = images.getSpecialSuicide();
                break;
            case SPECIALGHOST:
                currentAlgorithm = images.getSpecialGhost();
                break;
            case SPECIALSTAR:
                currentAlgorithm = images.getSpecialStar();
                break;
            case SPECIALSACRIFICE:
                currentAlgorithm = images.getSpecialSacrifice();
                break;
            case DEFENDSHELL:
                currentAlgorithm = images.getDefendShell();
                break;
            case DEFENDFLOCK:
                currentAlgorithm = images.getDefendFlock();
                break;
            case DEFENDINVINCIBLE:
                currentAlgorithm = images.getDefendInvincible();
                break;
            case DEFENDHIBERNATE:
                currentAlgorithm = images.getDefendHibernate();
                break;
            case SCOUTRANDOM:
                currentAlgorithm = images.getScoutRandom();
                break;
            case SCOUTBEE:
                currentAlgorithm = images.getScoutBee();
                break;
            case SCOUTANT:
                currentAlgorithm = images.getScoutAnt();
                break;
            case SCOUTPSO:
                currentAlgorithm = images.getScoutPSO();
                break;
            default:
                currentAlgorithm = images.getDefaultImage();

        }
    }
}
