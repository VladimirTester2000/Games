package com.javarush.games.racer;

import com.javarush.games.racer.road.RoadManager;

public class PlayerCar extends GameObject{
    private static int playerCarHeight=ShapeMatrix.PLAYER.length;
    public int speed=1;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private Direction direction;
    public PlayerCar() {
        super(RacerGame.WIDTH/2+2, RacerGame.HEIGHT-playerCarHeight-1, ShapeMatrix.PLAYER);
        setDirection(Direction.NONE);
    }
    public void move(){
        switch (direction){
            case LEFT : x--;
                break;
            case NONE: break;
            case RIGHT: x++;
                break;
        };
        if (x< RoadManager.LEFT_BORDER) x=RoadManager.LEFT_BORDER;
        if (x>RoadManager.RIGHT_BORDER-width) x=RoadManager.RIGHT_BORDER-width;
    }
    public void stop(){
        matrix=ShapeMatrix.PLAYER_DEAD;
    }

}
