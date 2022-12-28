package com.example.s_a_l;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Tile extends Rectangle {
    public StackPane newPane = new StackPane();
    public static Text tileText = new Text("22");
    public Tile(int x, int y, int i, int j) {
        setWidth(GameMainLoader.t_size);
        setHeight(GameMainLoader.t_size);
        if((j == 1 || j % 2 != 0) && ((i * j) % 2 == 0)) {
            setFill(Color.ROSYBROWN);
        }else if((j == 2 || j % 2 == 0) && (i == 1 || i % 2 !=0)){
            setFill(Color.ROSYBROWN);
        }else{
            setFill(Color.WHITE);
        setStroke(Color.BLACK);
    }
    }

}
