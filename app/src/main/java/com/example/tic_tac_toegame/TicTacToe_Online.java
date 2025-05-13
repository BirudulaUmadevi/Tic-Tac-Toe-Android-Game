package com.example.tic_tac_toegame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToe_Online extends AppCompatActivity {

    Button[][] buttons = new Button[3][3];
    TextView statusText;
    Button resetBtn;
    char[][] board = new char[3][3];
    boolean playerTurn = true; // true: X, false: O (computer)
    boolean gameOver = false;
    ImageButton img2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tic_tac_toe_online);

        statusText = findViewById(R.id.statusText);
        resetBtn = findViewById(R.id.resetBtn);

        img2=findViewById(R.id.backbtn2);

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(TicTacToe_Online.this, MainActivity.class);
                startActivity(intent2);
            }
        });


        int[][] ids = {
                {R.id.btn00, R.id.btn01, R.id.btn02},
                {R.id.btn10, R.id.btn11, R.id.btn12},
                {R.id.btn20, R.id.btn21, R.id.btn22}
        };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = findViewById(ids[i][j]);
                final int x = i, y = j;
                buttons[i][j].setOnClickListener(v -> onPlayerMove(x, y));
                board[i][j] = ' ';
            }
        }

        resetBtn.setOnClickListener(v -> resetGame());
    }

    void onPlayerMove(int x, int y) {
        if (!playerTurn || board[x][y] != ' ' || gameOver) return;

        board[x][y] = 'X';
        buttons[x][y].setText("X");

        if (checkWin('X')) {
            statusText.setText("You Win!");
            gameOver = true;
            return;
        }

        if (isDraw()) {
            statusText.setText("Draw!");
            gameOver = true;
            return;
        }

        playerTurn = false;
        statusText.setText("Computer's Turn");
        new Handler().postDelayed(this::computerMove, 500);
    }

    void computerMove() {
        if (gameOver) return;

        List<int[]> empty = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    empty.add(new int[]{i, j});

        if (!empty.isEmpty()) {
            int[] move = empty.get(new Random().nextInt(empty.size()));
            board[move[0]][move[1]] = 'O';
            buttons[move[0]][move[1]].setText("O");

            if (checkWin('O')) {
                statusText.setText("Computer Wins!");
                gameOver = true;
                return;
            }

            if (isDraw()) {
                statusText.setText("Draw!");
                gameOver = true;
                return;
            }
        }

        playerTurn = true;
        statusText.setText("Your Turn");
    }

    boolean checkWin(char symbol) {
        for (int i = 0; i < 3; i++)
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol))
                return true;

        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    boolean isDraw() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ') return false;
        return true;
    }

    void resetGame() {
        playerTurn = true;
        gameOver = false;
        statusText.setText("Your Turn");

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
                buttons[i][j].setText("");
            }
    }
}