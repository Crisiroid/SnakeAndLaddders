package com.example.s_a_l;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GameMainLoader extends Application {
    //Declaring required variables
    public int rand;
    public Label randInt;

    public int cPos[][] = new int [10][10];
    public int ladderSnakePosition [][] = new int[6][3];

    public static final int t_size = 80;
    public static final int width = 10;
    public static final int height = 10;

    public Circle playerOne;
    public Circle playerTwo;

    public int playerOnePosition = 1;
    public int playerTwoPosition = 1;

    public boolean playerOneTurn = true;
    public boolean playerTwoTurn = true;

    public static int playerOnexPos = 40;
    public static int playerOneyPos = 760;
    public static int playerTwoxPos = 40;
    public static int playerTwoyPos = 760;

    public boolean gameStart = false;
    public Button gameStartBtn;

    /*
    tileGroup is a list of objects that are displayed on application
    Players, tiles, snakes and ladders are some objects that are available here
     */
    private Group tileGroup = new Group();
    //A function to Create scene and its content
    private Parent createContent(){
        Pane root = new Pane();
        /*
        this part is pretty simple:
            we create a page with required height and width
            if we need more width or height, we can change it dynamically
            +80 for height is for required buttons and things like that.
         */
        root.setPrefSize(width*t_size, (height * t_size) + 80);
        root.getChildren().addAll(tileGroup);

        /*
        creating tiles based on :
            Tile.java file.
            the number of tiles is based on height and width
         */
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Tile tile = new Tile(t_size, t_size);
                tile.setTranslateX(j * t_size);
                tile.setTranslateY(i * t_size);
                tileGroup.getChildren().add(tile);
            }
        }
        /*
        Creating PLayer One and styling it:
            did a lot of research on how to use Style.css
            player one is a simple circle with some color
            the following code is setting its position and creating it.
         */
        playerOne = new Circle(40);
        playerOne.setId("playerOne");
        playerOne.getStyleClass().add("style.css");
        playerOne.setTranslateX(playerOnexPos);
        playerOne.setTranslateY(playerOneyPos);

        playerTwo = new Circle(40);
        playerTwo.setId("playerTwo");
        playerTwo.getStyleClass().add("style.css");
        playerTwo.setTranslateX(playerTwoxPos);
        playerTwo.setTranslateY(playerTwoyPos);

        /*
        Creating two button for rolling dice
        each button will be used for one player
         */
        Button btn = new Button("Player One Start");
        btn.setTranslateX(620);
        btn.setTranslateY(820);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart){
                    if(playerOneTurn){

                    }
                }
            }
        });
        Button btnTwo = new Button("Player Two Start");
        btnTwo.setTranslateX(80);
        btnTwo.setTranslateY(820);
        btnTwo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart){
                    if(playerTwoTurn){

                    }
                }
            }
        });
        gameStartBtn = new Button("Start the game");
        gameStartBtn.setTranslateX(380);
        gameStartBtn.setTranslateY(820);
        gameStartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStartBtn.setText("Game is Started");
                playerOnexPos = 40;
                playerOneyPos = 760;

                playerTwoxPos = 40;
                playerTwoyPos = 760;

                playerOne.setTranslateX(playerOnexPos);
                playerOne.setTranslateY(playerOneyPos);
                playerTwo.setTranslateX(playerTwoxPos);
                playerTwo.setTranslateY(playerTwoyPos);

            }
        });

        randInt = new Label("0");
        randInt.setTranslateX(300);
        randInt.setTranslateY(820);

        /*
        creating a snake image and showing it:
            first we set the location.
            then we use Imageview to show it
         */
        Image img = new Image("");
        ImageView bgImage = new ImageView();
        bgImage.setImage(img);
        bgImage.setFitHeight(800);
        bgImage.setFitHeight(800);

        tileGroup.getChildren().addAll(playerOne, playerTwo, bgImage, btn, btnTwo, gameStartBtn);
        return root;
    }

    //main function; nothing special
    public static void main(String[] args) {
        launch(args);
    }
    //required start function. it's used to start the game
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake and Ladder");
        stage.setScene(scene);
        stage.show();
    }
}