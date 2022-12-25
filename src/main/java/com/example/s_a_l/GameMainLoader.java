package com.example.s_a_l;

import javafx.animation.TranslateTransition;
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
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    public int positionCirOne = 1;
    public int positionCirTwo = 1;

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
                        getRandomNumberForDice();
                        randInt.setText(String.valueOf(rand));
                        movePlayerOne();
                        translatePlayer(playerOnexPos, playerOneyPos, playerOne);
                        playerOneTurn = false;
                        playerTwoTurn = true;
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
                        getRandomNumberForDice();
                        randInt.setText(String.valueOf(rand));
                        movePlayerTwo();
                        translatePlayer(playerTwoxPos, playerTwoyPos, playerTwo);
                        playerTwoTurn = false;
                        playerOneTurn = true;
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
                gameStart = true;
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

        tileGroup.getChildren().addAll(playerOne, playerTwo, btn, btnTwo, gameStartBtn, randInt);
        return root;
    }

    //main function; nothing special
    public static void main(String[] args) {
        launch(args);
    }

    //creating dice functionality
    private void getRandomNumberForDice(){
        rand = (int)(Math.random()*6+1);
    }
    //rules for moving player one
    private void movePlayerOne(){
        for(int i = 0; i < rand; i++){
            if(positionCirOne %2 == 1){
                playerOnexPos += 80;
            }
            if(positionCirOne%2 == 0){
                playerOnexPos -=80;
            }
            if(playerOnexPos > 760){
                playerOneyPos -=80;
                playerOnexPos -=80;
                positionCirOne ++;
            }
            if(playerOnexPos < 40){
                playerOneyPos -= 80;
                playerOnexPos += 80;
                positionCirOne ++;
            }

            if(playerOnexPos < 40 || playerOneyPos < 40){
                playerOnexPos = 40;
                playerOneyPos = 40;
                randInt.setText("player One Won!");
                gameStartBtn.setText("Start Again!");
            }
        }
    }
    //rules for moving player two
    private void movePlayerTwo(){
        for(int i = 0; i < rand; i++){
            if(positionCirTwo %2 == 1){
                playerTwoxPos += 80;
            }
            if(positionCirTwo%2 == 0){
                playerTwoxPos -=80;
            }
            if(playerTwoxPos > 760){
                playerTwoyPos -=80;
                playerTwoxPos -=80;
                positionCirTwo ++;
            }
            if(playerTwoxPos < 40){
                playerTwoyPos -= 80;
                playerTwoxPos += 80;
                positionCirTwo ++;
            }

            if(playerTwoxPos < 40 || playerTwoyPos < 40){
                playerTwoxPos = 40;
                playerTwoyPos = 40;
                randInt.setText("player Two Won!");
                gameStartBtn.setText("Start Again!");
            }
        }
    }
    //creating main animation for moving players
    private void translatePlayer(int x, int y, Circle b){
        TranslateTransition animate = new TranslateTransition(Duration.millis(1000), b);
        animate.setToX(x);
        animate.setToY(y);
        animate.setAutoReverse(false);
        animate.play();
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