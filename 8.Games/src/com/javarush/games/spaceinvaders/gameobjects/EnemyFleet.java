package com.javarush.games.spaceinvaders.gameobjects;

import com.javarush.engine.cell.Game;
import com.javarush.games.spaceinvaders.Direction;
import com.javarush.games.spaceinvaders.ShapeMatrix;
import com.javarush.games.spaceinvaders.SpaceInvadersGame;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EnemyFleet {
    //кол-во рядов кораблей
    private static final int ROWS_COUNT = 3;
    //кол-во кораблей в ряду
    private static final int COLUMNS_COUNT = 10;
    //расстояние между левыми верхними углами соседних кораблей
    private static final int STEP = ShapeMatrix.ENEMY.length+1;
    //список кораблей
    private List<EnemyShip> ships;
    private Direction direction = Direction.RIGHT;


    public EnemyFleet(){
        createShips();
    }
    //метод для создания списка кораблей
    private void createShips(){
        ships= new ArrayList<>(ROWS_COUNT*COLUMNS_COUNT);
        for (int y=0; y<ROWS_COUNT;y++)
            for (int x=0; x<COLUMNS_COUNT; x++){
                //12 - начальный отступ от поля первой линии флота
                // в данной реализации корабли будут создаваться верно пока матрица корабля квадратная,
                // либо высота больше ширины, иначе они будут наезжать друг на друге
                //также не учитывается сколько кораблей может вместиться в ряд
                EnemyShip enemyShip = new EnemyShip(x*STEP,y*STEP+12);
                ships.add(enemyShip);
            }
    }
    public void draw(Game game){
        ships.forEach(enemyShip -> enemyShip.draw(game));
    }

    //координата самого "левого" корабля
    private double getLeftBorder(){
        return ships.stream().min(Comparator.comparingDouble(ship -> ship.x)).get().x;
    }
    //x+width самого правого корабля
    private double getRightBorder(){
        EnemyShip rightShip = ships.stream().max(Comparator.comparingDouble(ship -> ship.x + ship.width)).get();
        return rightShip.x+(double)rightShip.width;
    }
    //идея в том чтобы скорость движения увеличивалась при уменьшении количества врагов
    private double getSpeed(){
        double result = ships.size()==0? 1:ships.size();
        return Double.min(2.0,3.0/ships.size());
    }

    public void move(){
//        если кораблей нет ничего не делаем
        if (ships.size()==0) return;
//        если достигли края арены, меняем направление и спускаемся вниз
        double speed = getSpeed();
        boolean flageDown = false;
        if (direction==Direction.LEFT && getLeftBorder()<0){
            direction=Direction.RIGHT;
            flageDown=true;
        }
        else {
            if (direction == Direction.RIGHT && getRightBorder() > SpaceInvadersGame.WIDTH) {
                direction = Direction.LEFT;
                flageDown = true;
            }
        }
        if (flageDown) ships.forEach(ship->ship.move(Direction.DOWN,speed));
        else ships.forEach(ship->ship.move(direction,speed));
    }
}
