package com.example.s_a_l;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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

    public static final int t_size = 80;
    public static final int width = 12;
    public static final int height = 12;
    public int snakeNumber = 3;
    public int ladderNumber = 4;

    public Circle playerOne;
    public Circle playerTwo;
    public Circle[] snake = new Circle[10];
    public Circle ladder;

    public Button btn, btnTwo;

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
    private Parent createContent() {
        Pane root = new Pane();

        //Setting the main size for Our root pane. it can be changed dynamically
        root.setPrefSize((width * t_size) + 160, (height * t_size));
        root.getChildren().addAll(tileGroup);

        /*
        creating tiles based on :
            Tile.java file.
            the number of tiles is based on height and width
         */
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Tile tile = new Tile(t_size, t_size, i, j);
                tile.setTranslateX(j * t_size);
                tile.setTranslateY(i * t_size);
                Text tileText = new Text("2");
                tileGroup.getChildren().add(tile);
                tileGroup.getChildren().add(tileText);
            }
        }

        //Creating player one
        playerOne = new Circle(30);
        playerOne.setId("playerOne");
        playerOne.setFill(Color.BISQUE);
        playerOne.setStroke(Color.BLACK);
        playerOne.setTranslateX(playerOnexPos);
        playerOne.setTranslateY(playerOneyPos);

        //Creating a Button for player number 1
        btn = new Button("Player One Start");
        btn.setTranslateX((width * 80) + 10);
        btn.setTranslateY(60);
        btn.setFont(new Font(16));
        btn.setStyle("-fx-background-color: #ff0000");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (gameStart) {
                    if (playerOneTurn) {
                        btn.setStyle("-fx-background-color: #ff0000");
                        btnTwo.setStyle("-fx-background-color: #0ec700");
                        if (!onesFirstStart) {
                            getRandomNumberForDice();
                            randInt.setText("Dice Value: " + String.valueOf(rand));
                            if (rand < 6) {
                                onesFirstStart = false;
                                whoTurn.setText("One need 6");
                                playerTwoTurn = true;
                                playerOneTurn = false;
                            } else {
                                onesFirstStart = true;
                                whoTurn.setText("Reward for One");
                                btnTwo.setStyle("-fx-background-color: #ff0000");
                                btn.setStyle("-fx-background-color: #0ec700");
                                playerTwoTurn = false;
                                playerOneTurn = true;
                            }
                        } else {
                            getRandomNumberForDice();
                            randInt.setText("Dice Value: " + String.valueOf(rand));
                            movePlayerOne();
                            translatePlayer(playerOnexPos, playerOneyPos, playerOne);
                            if (rand == 6) {
                                whoTurn.setText("Reward for One");
                                btnTwo.setStyle("-fx-background-color: #ff0000");
                                btn.setStyle("-fx-background-color: #0ec700");
                                playerTwoTurn = false;
                                playerOneTurn = true;
                            } else {
                                whoTurn.setText("Player Two's turn!");
                                playerOneTurn = false;
                                playerTwoTurn = true;
                            }
                        }

                    }
                }
            }
        });

        //Creating player two
        playerTwo = new Circle(30);
        playerTwo.setId("playerTwo");
        playerTwo.setStroke(Color.BLACK);
        playerTwo.setFill(Color.PINK);
        playerTwo.setTranslateX(playerTwoxPos);
        playerTwo.setTranslateY(playerTwoyPos);

        //Creating a button for Player Number 2
        btnTwo = new Button("Player Two Start");
        btnTwo.setTranslateX((width * 80) + 10);
        btnTwo.setTranslateY(120);
        btnTwo.setFont(new Font(16));
        btnTwo.setStyle("-fx-background-color: #ff0000");
        btnTwo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (gameStart) {
                    if (playerTwoTurn) {
                        btnTwo.setStyle("-fx-background-color: #ff0000");
                        btn.setStyle("-fx-background-color: #0ec700");
                        if (!twosFirstStart) {
                            getRandomNumberForDice();
                            randInt.setText("Dice Value: " + String.valueOf(rand));
                            if (rand < 6) {
                                twosFirstStart = false;
                                whoTurn.setText("Two need 6");
                                playerTwoTurn = false;
                                playerOneTurn = true;
                            } else {
                                twosFirstStart = true;
                                whoTurn.setText("Reward for Two");
                                btn.setStyle("-fx-background-color: #ff0000");
                                btnTwo.setStyle("-fx-background-color: #0ec700");
                                playerTwoTurn = true;
                                playerOneTurn = false;
                            }
                        } else {
                            getRandomNumberForDice();
                            randInt.setText("Dice Value: " + String.valueOf(rand));
                            movePlayerTwo();
                            translatePlayer(playerTwoxPos, playerTwoyPos, playerTwo);
                            if (rand == 6) {
                                whoTurn.setText("Reward for Two");
                                btn.setStyle("-fx-background-color: #ff0000");
                                btnTwo.setStyle("-fx-background-color: #0ec700");
                                playerTwoTurn = true;
                                playerOneTurn = false;
                            } else {
                                whoTurn.setText("Player One's turn!");
                                playerTwoTurn = false;
                                playerOneTurn = true;
                            }
                        }
                    }
                }
            }
        });

        //A button for starting the game. it can be used to Restart the game as well
        gameStartBtn = new Button("Start the game");
        gameStartBtn.setTranslateX((width * 80) + 10);
        gameStartBtn.setTranslateY(20);
        gameStartBtn.setFont(new Font(16));
        gameStartBtn.setStyle("-fx-background-color: #ff0000");
        gameStartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameStart = true;
                gameStartBtn.setText("Game is Started");
                gameStartBtn.setStyle("-fx-background-color: #0ec700");
                playerOnexPos = 40;
                playerOneyPos = (height * 80 - 40);

                playerTwoxPos = 40;
                playerTwoyPos = (height * 80 - 40);

                playerOne.setTranslateX(playerOnexPos);
                playerOne.setTranslateY(playerOneyPos);
                playerTwo.setTranslateX(playerTwoxPos);
                playerTwo.setTranslateY(playerTwoyPos);

            }
        });

        //Creator Button
        Button creatorBtn = new Button("Creator");
        creatorBtn.setTranslateX((width * 80) + 10);
        creatorBtn.setTranslateY((height * 80) - 120);
        creatorBtn.setFont(new Font(20));
        Alert creator = new Alert(Alert.AlertType.INFORMATION, "This game is created by: \r" +
                "Crisiroid\r" +
                "Amir Sajjad Hosein Pour\r" +
                "Winter 2022", ButtonType.OK);
        creator.setHeaderText("Creator");
        creator.setTitle("Creator");
        creatorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                creator.showAndWait();
                if(creator.getResult() == ButtonType.OK){
                    creator.close();
                }
            }
        });

        //Rules button
        Button rulesbtn = new Button("Game Rules");
        rulesbtn.setTranslateX((width * 80) + 10);
        rulesbtn.setTranslateY(height * 80 - 50);
        rulesbtn.setFont(new Font(20));
        rulesbtn.setTextFill(Color.BLACK);
        Alert gamerules = new Alert(Alert.AlertType.INFORMATION, "Welcome to Snake and Ladders game. \r" +
                "For playing this game, we need to players.\r" +
                "Each player has to roll the dice until the first 6 reveals.\r" +
                "Players can't start the game without finding the first 6.\r" +
                "The First player who reaches the end wins. \r" +
                "Red Dots with number indicated Snakes. snakes will change the position of player based on their numbers. \r" +
                "Green Dots with number indicated ladder. ladder will change the position of player based on their numbers.", ButtonType.OK);
        gamerules.setHeaderText("Game Rules");
        gamerules.setTitle("Game Rules");
        rulesbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gamerules.showAndWait();
                if(gamerules.getResult() == ButtonType.OK){
                    gamerules.close();
                }
            }
        });

        //Dice value Label
        randInt = new Label("ِDice Value : 0");
        randInt.setFont(new Font(22));
        randInt.setTextFill(Color.DARKRED);
        randInt.setTranslateX((width * 80) + 5);
        randInt.setTranslateY(300);

        //Status label
        whoTurn = new Label("Start the game \rfor playing");
        whoTurn.setFont(new Font(20));
        whoTurn.setTextFill(Color.CORNFLOWERBLUE);
        whoTurn.setTranslateX((width * 80) + 5);
        whoTurn.setTranslateY(220);

        //Creating Snakes
        snake[1] = new Circle(40);
        snake[1].setId("snake");
        snake[1].setStroke(Color.BLACK);
        snake[1].setFill(Color.RED);
        snake[1].setTranslateX((7*80)-40);
        snake[1].setTranslateY((6*80)-40);

        //Filling pane with items
        tileGroup.getChildren().addAll(playerOne, playerTwo, btn, btnTwo, gameStartBtn, randInt, whoTurn, creatorBtn, rulesbtn, snake[1]);
        return root;
    }

    //Creating dice functionality
    private void getRandomNumberForDice(){
        rand = (int)(Math.random()*6+1);
    }

    //Rules for moving player one
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
                    gameStart = false;
                }
            }
        }
    }

    //Rules for moving player two
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
                gameStart = false;
            }
        }
        }

    }

    //Creating main animation for moving players
    private void translatePlayer(int x, int y, Circle b){
        TranslateTransition animate = new TranslateTransition(Duration.millis(1000), b);
        animate.setToX(x);
        animate.setToY(y);
        animate.setAutoReverse(false);
        animate.play();
    }

    //Required start function
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(createContent());
        stage.setTitle("Snake and Ladder");
        stage.setScene(scene);
        stage.show();
    }

    //Main function; nothing special
    public static void main(String[] args) {
        launch(args);
    }
}