package com.example.tic_tac_toegame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TicTacToe extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private int roundCount = 0;
    private TextView textViewStatus;
    ImageButton img1;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tic_tac_toe);

        textViewStatus = findViewById(R.id.text_view_status);
        img1=findViewById(R.id.backbtn1);

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TicTacToe.this, MainActivity.class);
                startActivity(intent1);
            }
        });


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);

                int finalI = i;
                int finalJ = j;
                buttons[i][j].setOnClickListener(v -> onButtonClick(finalI, finalJ));
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(v -> resetGame());
    }

    private void onButtonClick(int row, int col) {
        if (!buttons[row][col].getText().toString().equals("")) return;

        buttons[row][col].setText(playerXTurn ? "X" : "O");
        roundCount++;

        if (checkWin()) {
            showWinner(playerXTurn ? "Player 1" : "Player 2");
        } else if (roundCount == 9) {
            showWinner("Draw");
        } else {
            playerXTurn = !playerXTurn;
            textViewStatus.setText("Player " + (playerXTurn ? "1" : "2") + "'s Turn");
        }
    }

    private boolean checkWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                field[i][j] = buttons[i][j].getText().toString();

        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2]) && !field[i][0].equals(""))
                return true;
            if (field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i]) && !field[0][i].equals(""))
                return true;
        }

        // Check diagonals
        if (field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(""))
            return true;
        if (field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(""))
            return true;

        return false;
    }

    private void showWinner(String winner) {
        textViewStatus.setText(winner + " Wins!");
        disableButtons();
        if (winner.equals("Draw")) textViewStatus.setText("It's a Draw!");
    }

    private void disableButtons() {
        for (Button[] row : buttons)
            for (Button button : row)
                button.setEnabled(false);
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }

        roundCount = 0;
        playerXTurn = true;
        textViewStatus.setText("Player 1's Turn");
    }
}