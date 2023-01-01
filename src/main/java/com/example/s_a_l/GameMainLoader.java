package com.example.s_a_l;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    public Circle[] snake;
    public Circle[] ladder;

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
    private Group FirstScene = new Group();
    //Menu Scene
    private Parent mainScene(){
        Pane rroot = new Pane();
        rroot.setPrefSize(800, 900);
        rroot.getChildren().addAll(FirstScene);

        Label gameText = new Label("Snakes and Ladders");
        gameText.setFont(new Font(45));
        gameText.setTranslateY(260);
        gameText.setTranslateX(210);

        //Snake numbers field
        Label snakeNumLabel = new Label("Enter the number of \rsnakes: ");
        snakeNumLabel.setFont(new Font(16));
        snakeNumLabel.setTextFill(Color.RED);
        snakeNumLabel.setTranslateX(320);
        snakeNumLabel.setTranslateY(320);
        TextField snakeNumTextField = new TextField("0");
        snakeNumTextField.setTranslateY(380);
        snakeNumTextField.setTranslateX(320);

        //Ladder numbers field
        Label ladderNumLabel = new Label("Enter the number of \rLadders: ");
        ladderNumLabel.setFont(new Font(16));
        ladderNumLabel.setTextFill(Color.RED);
        ladderNumLabel.setTranslateX(320);
        ladderNumLabel.setTranslateY(420);
        TextField ladderNumTextField = new TextField("0");
        ladderNumTextField.setTranslateY(480);
        ladderNumTextField.setTranslateX(320);
        Button start = new Button("Start the game");
        start.setTranslateX(320);
        start.setTranslateY(510);

        FirstScene.getChildren().addAll(snakeNumLabel, snakeNumTextField, ladderNumLabel, ladderNumTextField, start, gameText);
        return rroot;
    }

    //Game Scene
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

        TextInputDialog snakeInput = new TextInputDialog("0");
        snakeInput.setHeaderText("Enter Snakes number: ");
        TextInputDialog ladderInput = new TextInputDialog("0");
        ladderInput.setHeaderText("Enter Ladders number: ");
        //A button for starting the game. it can be used to Restart the game as well
        gameStartBtn = new Button("Start the game");
        gameStartBtn.setTranslateX((width * 80) + 10);
        gameStartBtn.setTranslateY(20);
        gameStartBtn.setFont(new Font(16));
        gameStartBtn.setStyle("-fx-background-color: #ff0000");
        gameStartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Adding snakes based on user input
                snakeInput.showAndWait();
                snakeNumber = Integer.parseInt(snakeInput.getResult());
                snake = new Circle[snakeNumber];
                System.out.println(snakeInput.getResult());
                for (int i = 0; i < snakeNumber; i++){
                    snake[i] = new Circle(40);
                    snake[i].setId("Snake");
                    snake[i].setStroke(Color.BLACK);
                    snake[i].setFill(Color.RED);

                    TextInputDialog snakey = new TextInputDialog("");
                    snakey.setHeaderText("Snake number" + i + " Y position: ");
                    snakey.showAndWait();
                    snake[i].setTranslateY(Integer.parseInt((snakey.getResult())) * 80 - 40);

                    TextInputDialog snakex = new TextInputDialog("");
                    snakex.setHeaderText("Snake number" + i + " X position: ");
                    snakex.showAndWait();
                    snake[i].setTranslateX(Integer.parseInt((snakey.getResult())) * 80 - 40);
                    root.getChildren().addAll(snake[i]);
                }
                //Adding ladders based on user input
                ladderInput.showAndWait();
                ladderNumber = Integer.parseInt(ladderInput.getResult());
                System.out.println(ladderInput.getResult());
                ladder = new Circle[ladderNumber];
                for (int i = 0; i < ladderNumber; i++){
                    ladder[i] = new Circle(40);
                    ladder[i].setId("ladder");
                    ladder[i].setStroke(Color.BLACK);
                    ladder[i].setFill(Color.GREEN);

                    TextInputDialog laddery = new TextInputDialog("");
                    laddery.setHeaderText("ladder number " + i + " Y position: ");
                    laddery.showAndWait();
                    ladder[i].setTranslateY(Integer.parseInt((laddery.getResult())) * 80 - 40);

                    TextInputDialog ladderx = new TextInputDialog("");
                    ladderx.setHeaderText("ladder number " + i + " X position: ");
                    ladderx.showAndWait();
                    ladder[i].setTranslateX(Integer.parseInt((ladderx.getResult())) * 80 - 40);
                    root.getChildren().addAll(ladder[i]);
                }
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


        //Filling pane with items
        tileGroup.getChildren().addAll(playerOne, playerTwo, btn, btnTwo, gameStartBtn, randInt, whoTurn, creatorBtn, rulesbtn);
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

                /*for(int j = 0; j<snake.length; j++){
                    if(playerTwo.getBoundsInParent().intersects(snake[1].getBoundsInParent())){
                        System.out.println("Collision");
                    }
                }*/
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

            /*for(int j = 0; j<snake.length; j++){
                if(playerTwo.getBoundsInParent().intersects(snake[1].getBoundsInParent())){
                    System.out.println("Collision");
                }
            }*/
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
        Scene sceneOne = new Scene(mainScene());
        Scene sceneTwo = new Scene(createContent());
        stage.setTitle("Snake and Ladder");
        stage.setScene(sceneTwo);
        stage.show();
    }

    //Main function; nothing special
    public static void main(String[] args) {
        launch(args);
    }
}