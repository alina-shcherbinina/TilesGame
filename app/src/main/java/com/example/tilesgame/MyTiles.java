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

import java.util.Random;

public class MyTiles extends View {

    int[][] tiles = new int[4][4];
    int darkColor = Color.GRAY;
    int brightColor = Color.rgb(98,0,238);
    DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
    int width = displaymetrics.widthPixels;
    int height = displaymetrics.heightPixels;
    int tileWidth = width / 13;
    int tileHeight = height / 13;
    boolean check;

    public MyTiles(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
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
                        width / 5 * (i + 1) - tileWidth,
                        height / 5 * (j + 1) - tileHeight,
                        width / 5 * (i + 1) + tileWidth,
                        height / 5 * (j + 1) + tileHeight,
                        p);
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        //Определяем на какую плитку тыкнули
        if (event.getAction() == MotionEvent.ACTION_UP) {
            int i = -1;
            int j = -1;
            if (x > width / 5 - tileWidth && x < width / 5 + tileWidth) {
                i = 0;
            } else if (x > width / 5 * 2 - tileWidth && x < width / 5 * 2 + tileWidth) {
                i = 1;
            } else if (x > width / 5 * 3 - tileWidth && x < width / 5 * 3 + tileWidth) {
                i = 2;
            } else if (x > width / 5 * 4 - tileWidth && x < width / 5 * 4 + tileWidth) {
                i = 3;
            }

            if (y > height / 5 - tileHeight && y < height / 5 + tileHeight) {
                j = 0;
            } else if (y > height / 5 * 2 - tileHeight && y < height / 5 * 2 + tileHeight) {
                j = 1;
            } else if (y > height / 5 * 3 - tileHeight && y < height / 5 * 3 + tileHeight) {
                j = 2;
            } else if (y > height / 5 * 4 - tileHeight && y < height / 5 * 4 + tileHeight) {
                j = 3;
            }

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

                //Проверка условия для победы
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
                }
            }
        }

        invalidate();
        return true;
    }
}
