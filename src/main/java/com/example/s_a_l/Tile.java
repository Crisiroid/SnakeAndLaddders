package com.example.s_a_l;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {
    public Tile(int x, int y){
        setWidth(GameMainLoader.t_size);
        setHeight(GameMainLoader.t_size);

        setFill(Color.BLUE);
        setStroke(Color.BLACK);
    }
}
