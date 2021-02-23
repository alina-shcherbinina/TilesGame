package com.example.tilesgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;


public class MyTiles extends View implements View.OnClickListener {
    int[][] tiles = new int[4][4];
    int darkColor = Color.GRAY;
    int brightColor = Color.rgb(98,0,238);
    DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
    int screenSize = displaymetrics.widthPixels;
    int heightCenterCorrection = (displaymetrics.heightPixels - screenSize) / 2;
    int tileSize = screenSize / 13;
    boolean check;

    public MyTiles(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);

        Random r = new Random();
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                if (r.nextInt(2) == 1) {
                    tiles[i][j] = brightColor;
                } else  {
                    tiles[i][j] = darkColor;
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation") Paint p = new Paint();

        //Рисуем плитку
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                p.setColor(tiles[i][j]);
                canvas.drawRect(
                        screenSize / 5 * (i + 1) - tileSize,
                        heightCenterCorrection + screenSize / 5 * (j + 1) - tileSize,
                        screenSize / 5 * (i + 1) + tileSize,
                        heightCenterCorrection + screenSize / 5 * (j + 1) + tileSize,
                        p);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            //Определяем на какую плитку тыкнули
            int i = -1;
            int j = -1;
            if (x > screenSize / 5 - tileSize && x < screenSize / 5 + tileSize) {
                i = 0;
            } else if (x > screenSize / 5 * 2 - tileSize && x < screenSize / 5 * 2 + tileSize) {
                i = 1;
            } else if (x > screenSize / 5 * 3 - tileSize && x < screenSize / 5 * 3 + tileSize) {
                i = 2;
            } else if (x > screenSize / 5 * 4 - tileSize && x < screenSize / 5 * 4 + tileSize) {
                i = 3;
            }

            if (y > heightCenterCorrection + screenSize / 5 - tileSize &&
                    y < heightCenterCorrection + screenSize / 5 + tileSize) {
                j = 0;
            } else if (y > heightCenterCorrection + screenSize / 5 * 2 - tileSize &&
                    y < heightCenterCorrection + screenSize / 5 * 2 + tileSize) {
                j = 1;
            } else if (y > heightCenterCorrection + screenSize / 5 * 3 - tileSize &&
                    y < heightCenterCorrection + screenSize / 5 * 3 + tileSize) {
                j = 2;
            } else if (y > heightCenterCorrection + screenSize / 5 * 4 - tileSize &&
                    y < heightCenterCorrection + screenSize / 5 * 4 + tileSize) {
                j = 3;
            }
            changeTilesColors(i, j);
            checkForWin();
            invalidate();
        }
        return true;
    }

    //Проверка условия для победы
    public boolean checkForWin() {
        check = true;
        for (int k = 0; k < tiles.length; k++) {
            for (int d = 1; d < tiles.length; d++) {
                if (tiles[k][d] != tiles[k][d - 1]) {
                    check = false;
                    break;
                }
            }
            if (!check) {
                break;
            }
            for (int d = 1; d < tiles.length; d++) {
                if (tiles[d][k] != tiles[d - 1][k]) {
                    check = false;
                    break;
                }
            }
        }
        if (check) {
            Toast toast = Toast.makeText(getContext(),
                    "Победа, победа, вместо обеда!", Toast.LENGTH_LONG);
            toast.show();
            return true;
        } else {
            return false;
        }
    }

    public void changeTilesColors(int i, int j) {
        //Меняем цвета плиток
        if (i != -1 && j != -1) {
            if (tiles[i][j] == brightColor) {
                tiles[i][j] = darkColor;
            } else {
                tiles[i][j] = brightColor;
            }
            for (int k = 0; k < tiles.length; k++) {
                if (tiles[i][k] == brightColor) {
                    tiles[i][k] = darkColor;
                } else {
                    tiles[i][k] = brightColor;
                }
            }
            for (int k = 0; k < tiles.length; k++) {
                if (tiles[k][j] == brightColor) {
                    tiles[k][j] = darkColor;
                } else {
                    tiles[k][j] = brightColor;
                }
            }
            invalidate();
        }
    }

    //Функция для авто победы
    @Override
    public void onClick(View v) {
        while (!checkForWin()) {
            ArrayList<int[]> listOfDarkTiles = new ArrayList<>();
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (tiles[i][j] == darkColor) {
                        listOfDarkTiles.add(new int[] {i, j});
                    }
                }
            }
            for (int i = 0; i < listOfDarkTiles.size(); i++) {
                changeTilesColors(listOfDarkTiles.get(i)[0], listOfDarkTiles.get(i)[1]);
            }
        }
    }
}


