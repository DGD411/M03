package com.example.tictactoe;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button button1;

    @FXML
    private Button button2;

    @FXML
    private Button button3;

    @FXML
    private Button button4;

    @FXML
    private Button button5;

    @FXML
    private Button button6;

    @FXML
    private Button button7;

    @FXML
    private Button button8;

    @FXML
    private Button button9;

    @FXML
    private Text winnerText;
    private Timeline timeline;

    private int playerTurn = 0;
    private boolean isAgainstAI = false; // Bandera para el modo de juego

    ArrayList<Button> buttons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9));

        buttons.forEach(button -> {
            setupButton(button);
            button.setFocusTraversable(false);
        });
    }

    @FXML
    void restartGame(ActionEvent event) {
        buttons.forEach(this::resetButton);
        winnerText.setText("Tic-Tac-Toe");
        playerTurn = 0; // Restablecer el turno de los jugadores

        // Determinar el modo de juego predeterminado al reiniciar
        String buttonText = "Jugador Vs Jugador"; // Modo por defecto
        if (isAgainstAI) {
            buttonText = "Jugador Vs Maquina"; // Modo si es contra IA
        }

        // Cambiar el modo de juego y reiniciar el tablero
        switch (buttonText) {
            case "Jugador Vs Jugador":
                isAgainstAI = false;
                break;
            case "Jugador Vs Maquina":
                isAgainstAI = true;
                if (playerTurn == 1) {
                    makeAIMove(); // Si es el turno de la IA al reiniciar, hacer un movimiento
                }
                break;
            case "Maquina Vs Maquina":
                isAgainstAI = true;
                restartGame(null);
                playAIMatch(); // Iniciar la partida entre IA
                break;
            default:
                break;
        }
    }

    public void resetButton(Button button) {
        button.setDisable(false);
        button.setText("");
    }

    private void setupButton(Button button) {
        button.setOnMouseClicked(mouseEvent -> {
            if (!isAgainstAI) {
                setPlayerSymbol(button);
                button.setDisable(true);
                checkIfGameIsOver();
            } else {
                if (playerTurn == 0) { // Si es el turno del jugador
                    setPlayerSymbol(button);
                    button.setDisable(true);
                    checkIfGameIsOver();
                    makeAIMove(); // Llamada al método de la IA
                }
            }
        });
    }

    private void makeAIMove() {
        // Obtener una lista de botones disponibles
        ArrayList<Button> availableButtons = new ArrayList<>();
        buttons.forEach(button -> {
            if (!button.isDisabled()) {
                availableButtons.add(button);
            }
        });

        if (!availableButtons.isEmpty()) {
            // Elegir un botón aleatorio
            Button randomButton = availableButtons.get((int) (Math.random() * availableButtons.size()));
            setPlayerSymbol(randomButton);
            randomButton.setDisable(true);
            checkIfGameIsOver();
        }
    }

    public void setPlayerSymbol(Button button){
        if(playerTurn % 2 == 0){
            button.setText("X");
            playerTurn = 1;
        } else{
            button.setText("O");
            playerTurn = 0;
        }
    }

    private boolean checkIfGameIsOver() {
        for (int a = 0; a < 8; a++) {
            String line = switch (a) {
                case 0 -> button1.getText() + button2.getText() + button3.getText();
                case 1 -> button4.getText() + button5.getText() + button6.getText();
                case 2 -> button7.getText() + button8.getText() + button9.getText();
                case 3 -> button1.getText() + button5.getText() + button9.getText();
                case 4 -> button3.getText() + button5.getText() + button7.getText();
                case 5 -> button1.getText() + button4.getText() + button7.getText();
                case 6 -> button2.getText() + button5.getText() + button8.getText();
                case 7 -> button3.getText() + button6.getText() + button9.getText();
                default -> null;
            };

            // X winner
            if (line.equals("XXX")) {
                winnerText.setText("X won!");
                return true; // Devuelve true si X gana
            }

            // O winner
            else if (line.equals("OOO")) {
                winnerText.setText("O won!");
                return true; // Devuelve true si O gana
            }
        }

        return false; // Devuelve false si nadie ha ganado
    }

    @FXML
    void changeGameMode(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String buttonText = clickedButton.getText();

        switch (buttonText) {
            case "Jugador Vs Jugador":
                isAgainstAI = false;
                restartGame(null);
                break;
            case "Jugador Vs Maquina":
                isAgainstAI = true;
                restartGame(null);
                if (isAgainstAI && playerTurn == 1) {
                    makeAIMove(); // Si es el turno de la IA al iniciar, hacer un movimiento
                }
                break;
            case "Maquina Vs Maquina":
                if (timeline != null) {
                    timeline.stop(); // Detiene la simulación entre IA si está en curso
                    timeline = null; // Reinicia la Timeline
                }
                isAgainstAI = true;
                restartGame(null);
                playAIMatch(); // Iniciar la partida entre IA
                break;
            default:
                break;
        }
    }


    // Método para simular una partida entre IA
    private void playAIMatch() {
        if (isAgainstAI) {
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
                if (!checkIfGameIsOver()) {
                    makeAIMove();
                } else {
                    if (timeline != null) {
                        timeline.stop(); // Detiene la simulación si el juego termina
                        timeline = null; // Reinicia la Timeline
                    }
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }
}
