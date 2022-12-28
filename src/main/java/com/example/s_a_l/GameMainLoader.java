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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameMainLoader extends Application {
    //Declaring required variables
    public int rand;
    public Label randInt;
    public Label whoTurn;

    public int cPos[][] = new int [10][10];
    public int ladderSnakePosition [][] = new int[6][3];

    public static final int t_size = 80;
    public static final int width = 20;
    public static final int height = 12;

    public Circle playerOne;
    public Circle playerTwo;

    public int playerOnePosition = 1;
    public int playerTwoPosition = 1;

    public boolean playerOneTurn = true;
    public boolean playerTwoTurn = true;

    public static int playerOnexPos = 40;
    public static int playerOneyPos = (height*80-40);
    public static int playerTwoxPos = 40;
    public static int playerTwoyPos = (height*80-40);

    public int positionCirOne = 1;
    public int positionCirTwo = 1;

    public boolean gameStart = false;
    public boolean onesFirstStart = false;
    public boolean twosFirstStart = false;

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
        root.setPrefSize((width*t_size)+150, (height * t_size));
        root.getChildren().addAll(tileGroup);

        /*
        creating tiles based on :
            Tile.java file.
            the number of tiles is based on height and width
         */
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Tile tile = new Tile(t_size, t_size, i, j);
                tile.setTranslateX(j * t_size);
                tile.setTranslateY(i * t_size);
                Text tileText = new Text("2");
                tileGroup.getChildren().add(tile);
                tileGroup.getChildren().add(tileText);
            }
        }
        /*
        Creating PLayers and styling it:
            player one is a simple circle with some color
            the following code is setting its position and creating it.
         */
        playerOne = new Circle(30);
        playerOne.setId("playerOne");
        playerOne.setFill(Color.BISQUE);
        playerOne.setStroke(Color.BLACK);
        playerOne.setTranslateX(playerOnexPos);
        playerOne.setTranslateY(playerOneyPos);

        playerTwo = new Circle(30);
        playerTwo.setId("playerTwo");
        playerTwo.setStroke(Color.BLACK);
        playerTwo.setFill(Color.PINK);
        playerTwo.setTranslateX(playerTwoxPos);
        playerTwo.setTranslateY(playerTwoyPos);

        /*
        Creating two button for rolling dice
        each button will be used for one player
         */
        Button btn = new Button("Player One Start");
        btn.setTranslateX((width*80) + 10);
        btn.setTranslateY(60);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart){
                    if(playerOneTurn){
                        if(!onesFirstStart){
                            getRandomNumberForDice();
                            randInt.setText("Dice Value: "+String.valueOf(rand));
                            if(rand < 6){
                                onesFirstStart = false;
                                whoTurn.setText("One need 6");
                                playerTwoTurn = true;
                                playerOneTurn = false;
                            }
                            else{
                                onesFirstStart = true;
                                whoTurn.setText("Reward for One");
                                playerTwoTurn = false;
                                playerOneTurn = true;
                            }
                        }else{
                            getRandomNumberForDice();
                            randInt.setText("Dice Value: "+String.valueOf(rand));
                            movePlayerOne();
                            translatePlayer(playerOnexPos, playerOneyPos, playerOne);
                            if(rand == 6){
                                whoTurn.setText("Reward for One");
                                playerTwoTurn = false;
                                playerOneTurn = true;
                            }
                            whoTurn.setText("Player Two's turn!");
                            playerOneTurn = false;
                            playerTwoTurn = true;
                        }

                    }
                }
            }
        });
        Button btnTwo = new Button("Player Two Start");
        btnTwo.setTranslateX((width*80) + 10);
        btnTwo.setTranslateY(120);
        btnTwo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(gameStart){
                    if(playerTwoTurn){
                        if(!twosFirstStart){
                            getRandomNumberForDice();
                            randInt.setText("Dice Value: "+String.valueOf(rand));
                            if(rand < 6){
                                twosFirstStart = false;
                                whoTurn.setText("Two need 6");
                                playerTwoTurn = false;
                                playerOneTurn = true;
                            }else{
                                twosFirstStart = true;
                                whoTurn.setText("Reward for Two");
                                playerTwoTurn = true;
                                playerOneTurn = false;
                            }
                        }else {
                            getRandomNumberForDice();
                            randInt.setText("Dice Value: " + String.valueOf(rand));
                            movePlayerTwo();
                            translatePlayer(playerTwoxPos, playerTwoyPos, playerTwo);
                            if(rand == 6){
                                whoTurn.setText("Reward for Two");
                                playerTwoTurn = true;
                                playerOneTurn = false;
                            }else{
                                whoTurn.setText("Player One's turn!");
                                playerTwoTurn = false;
                                playerOneTurn = true;
                            }
                        }
                    }
                }
            }
        });
        gameStartBtn = new Button("Start the game");
        gameStartBtn.setTranslateX((width*80) + 10);
        gameStartBtn.setTranslateY(20);
        gameStartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStart = true;
                gameStartBtn.setText("Game is Started");
                playerOnexPos = 40;
                playerOneyPos = (height*80-40);

                playerTwoxPos = 40;
                playerTwoyPos = (height*80-40);

                playerOne.setTranslateX(playerOnexPos);
                playerOne.setTranslateY(playerOneyPos);
                playerTwo.setTranslateX(playerTwoxPos);
                playerTwo.setTranslateY(playerTwoyPos);

            }
        });

        randInt = new Label("ِDice Value : 0");
        randInt.setFont(new Font(22));
        randInt.setTextFill(Color.DARKRED);
        randInt.setTranslateX((width*80) + 5);
        randInt.setTranslateY(300);

        whoTurn = new Label("Start the game \rfor playing");
        whoTurn.setFont(new Font(20));
        whoTurn.setTextFill(Color.CORNFLOWERBLUE);
        whoTurn.setTranslateX((width*80) + 5);
        whoTurn.setTranslateY(220);

        /*
        creating a snake image and showing it:
            first we set the location.
            then we use Imageview to show it
         */

        tileGroup.getChildren().addAll(playerOne, playerTwo, btn, btnTwo, gameStartBtn, randInt, whoTurn);
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
        if(onesFirstStart) {
            for (int i = 0; i < rand; i++) {
                if (positionCirOne % 2 == 1) {
                    playerOnexPos += 80;
                }
                if (positionCirOne % 2 == 0) {
                    playerOnexPos -= 80;
                }
                if (playerOnexPos > (width * 80 - 40)) {
                    playerOneyPos -= 80;
                    playerOnexPos -= 80;
                    positionCirOne++;
                }
                if (playerOnexPos < 40) {
                    playerOneyPos -= 80;
                    playerOnexPos += 80;
                    positionCirOne++;
                }

                if (playerOnexPos < 30 || playerOneyPos < 30) {
                    playerOnexPos = 40;
                    playerOneyPos = 40;
                    randInt.setText("player One Won!");
                    gameStartBtn.setText("Start Again!");
                }
            }
        }
    }
    //rules for moving player two
    private void movePlayerTwo(){
        if(twosFirstStart){
        for(int i = 0; i < rand; i++){
            if(positionCirTwo %2 == 1){
                playerTwoxPos += 80;
            }
            if(positionCirTwo%2 == 0){
                playerTwoxPos -=80;
            }
            if(playerTwoxPos > (width*80-40)){
                playerTwoyPos -=80;
                playerTwoxPos -=80;
                positionCirTwo ++;
            }
            if(playerTwoxPos < 40){
                playerTwoyPos -= 80;
                playerTwoxPos += 80;
                positionCirTwo ++;
            }

            if(playerTwoxPos < 30 || playerTwoyPos < 30){
                playerTwoxPos = 40;
                playerTwoyPos = 40;
                randInt.setText("player Two Won!");
                gameStartBtn.setText("Start Again!");
            }
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