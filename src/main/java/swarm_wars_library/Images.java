package swarm_wars_library;

import processing.core.PApplet;
import processing.core.PImage;

public class Images {

    private PApplet sketch;
    private static Images instance = new Images();

    private PImage defaultImage;
    private PImage specialSuicide;
    private PImage specialGhost;
    private PImage specialStar;
    private PImage specialSacrifice;
    private PImage defendShell;
    private PImage defendFlock;
    private PImage defendInvincible;
    private PImage defendHibernate;
    private PImage scoutRandom;
    private PImage scoutBee;
    private PImage scoutAnt;
    private PImage scoutPSO;
    private PImage background;
    private PImage gameOverLogo;
    private PImage brokenShipLogo;
    private PImage flames;
    private PImage splashScreen;
    private PImage bulletSprite;
    private PImage gameMap;
    private PImage shipSingle;
    private PImage shipThrustSprite;
    private PImage droneSinglePlayer2;
    private PImage turretSprite;
    private PImage shipLogo;
    private PImage battlestarLogo;
    private PImage droneSingle;
    private PImage shipSinglePlayer2;
    private PImage missileSprite;
    private PImage bullet2Sprite;
    private PImage turretBulletSprite;
    private PImage missile2Sprite;

    private Images() { }

    public static Images getInstance() {
        return instance;
    }

    public void setSketch(PApplet sketch) {
        this.sketch = sketch;
    }

    public void loadImages(){
        this.defaultImage = sketch.loadImage("resources/images/default.png");
        this.specialSuicide = sketch.loadImage("resources/images/specialSuicide.png");
        this.specialGhost = sketch.loadImage("resources/images/specialGhost.png");
        this.specialStar = sketch.loadImage("resources/images/specialStar.png");
        this.specialSacrifice = sketch.loadImage("resources/images/specialSacrifice.png");
        this.defendShell = sketch.loadImage("resources/images/defendShell.png");
        this.defendFlock = sketch.loadImage("resources/images/defendFlock.png");
        this.defendInvincible = sketch.loadImage("resources/images/defendInvincible.png");
        this.defendHibernate = sketch.loadImage("resources/images/defendHibernate.png");
        this.scoutRandom = sketch.loadImage("resources/images/scoutRandom.png");
        this.scoutBee = sketch.loadImage("resources/images/scoutBee.png");
        this.scoutAnt = sketch.loadImage("resources/images/scoutAnt.png");
        this.scoutPSO = sketch.loadImage("resources/images/scoutPSO.png");
        this.background = sketch.loadImage("resources/images/background.png");
        this.brokenShipLogo = sketch.loadImage("resources/images/brokenShipLogo.png");
        this.gameOverLogo = sketch.loadImage("resources/images/gameoverLogo.png");
        this.flames = sketch.loadImage("resources/images/gameOverFlameSingle.png");
        this.splashScreen = sketch.loadImage("resources/images/splashScreen.png");
        this.bulletSprite = this.sketch.loadImage("resources/images/bulletSprite.png");
        this.gameMap = sketch.loadImage("resources/images/gameMap.png");
        this.shipSingle = this.sketch.loadImage("resources/images/shipSingle.png");
        this.shipThrustSprite = this.sketch.loadImage("resources/images/shipThrustSprite.png");
        this.droneSinglePlayer2 = this.sketch.loadImage("resources/images/droneSinglePlayer2.png");
        this.turretSprite = this.sketch.loadImage("resources/images/turretSprite.png");
        this.shipLogo = this.sketch.loadImage("resources/images/shipLogo.png");
        this.battlestarLogo = this.sketch.loadImage("resources/images/battlestarLogo.png");
        this.droneSingle = this.sketch.loadImage("resources/images/droneSingle.png");
        this.shipSinglePlayer2 = this.sketch.loadImage("resources/images/shipSinglePlayer2.png");
        this.missileSprite = this.sketch.loadImage("resources/images/missileSprite.png");
        this.bullet2Sprite = this.sketch.loadImage("resources/images/bullet2Sprite.png");
        this.turretBulletSprite = this.sketch.loadImage("resources/images/turretBulletSprite.png");
        this.missile2Sprite = this.sketch.loadImage("resources/images/missile2Sprite.png");
    }

    public PImage getDefaultImage() {
        return defaultImage;
    }

    public PImage getSpecialSuicide() {
        return specialSuicide;
    }

    public PImage getSpecialGhost() {
        return specialGhost;
    }

    public PImage getSpecialStar() {
        return specialStar;
    }

    public PImage getSpecialSacrifice() {
        return specialSacrifice;
    }

    public PImage getDefendShell() {
        return defendShell;
    }

    public PImage getDefendFlock() {
        return defendFlock;
    }

    public PImage getDefendInvincible() {
        return defendInvincible;
    }

    public PImage getDefendHibernate() {
        return defendHibernate;
    }

    public PImage getScoutRandom() {
        return scoutRandom;
    }

    public PImage getScoutBee() {
        return scoutBee;
    }

    public PImage getScoutAnt() {
        return scoutAnt;
    }

    public PImage getScoutPSO() {
        return scoutPSO;
    }

    public PImage getBackground() {
        return background;
    }

    public PImage getGameOverLogo() {
        return gameOverLogo;
    }

    public PImage getBrokenShipLogo() {
        return brokenShipLogo;
    }

    public PImage getFlames() {
        return flames;
    }

    public PImage getSplashScreen() {
        return splashScreen;
    }

    public PImage getBulletSprite() {
        return bulletSprite;
    }

    public PImage getGameMap() {
        return gameMap;
    }

    public PImage getShipSingle() {
        return shipSingle;
    }

    public PImage getShipThrustSprite() {
        return shipThrustSprite;
    }

    public PImage getDroneSinglePlayer2() {
        return droneSinglePlayer2;
    }

    public PImage getTurretSprite() {
        return turretSprite;
    }

    public PImage getShipLogo() {
        return shipLogo;
    }

    public PImage getBattlestarLogo() {
        return battlestarLogo;
    }

    public PImage getDroneSingle() {
        return droneSingle;
    }

    public PImage getShipSinglePlayer2() {
        return shipSinglePlayer2;
    }

    public PImage getMissileSprite() {
        return missileSprite;
    }

    public PImage getBullet2Sprite() {
        return bullet2Sprite;
    }

    public PImage getTurretBulletSprite() {
        return turretBulletSprite;
    }

    public PImage getMissile2Sprite() {
        return missile2Sprite;
    }
}
