package com.example.tilesgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

class Tile {
    int color;
    int left;
    int right;
    int bottom;
    int top;

    Tile(int l, int t, int r, int b, int c) {
        left = l;
        top = t;
        right = r;
        bottom = b;
        color = c;
    }

    int getColor() { // dont forget its int
        return color;
    }

    void setColor(int c) {
        color = c;
    }
}



public class MyTiles extends View implements View.OnClickListener {
    Tile[][] tiles = new Tile[4][4];

    int darkColor = Color.GRAY;
    int brightColor = Color.rgb(98,0,238);

    public MyTiles(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnClickListener(this);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();

        int width = getWidth();
        int height = getHeight();
        int heightCenterCorrection = (height - width) / 2;

        int tileSize = width / 13;

        //Рисуем плитку

        int color = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {

                int left = width / 5 * (i + 1) - tileSize;
                int top = heightCenterCorrection + width / 5 * (j + 1) - tileSize;
                int right = width / 5 * (i + 1) + tileSize;
                int bottom = heightCenterCorrection + width / 5 * (j + 1) + tileSize;

                Rect tile = new Rect();
                tile.set(left, top, right, bottom);

                Random r = new Random();

                if (r.nextInt(2) == 1) {
                    color = 0;
                     p.setColor(brightColor);
                } else  {
                    color = 1;
                    p.setColor(darkColor);
                }

                canvas.drawRect(tile, p);
                tiles[i][j] = new Tile(left, top, right, bottom, color);

            }
        }

    }


//TODO: we have tiles array to track our on touch event
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int width = getWidth();
        int height = getHeight();

        if (event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {

                        if (tiles[i][j].left < x && tiles[i][j].right > x && tiles[i][j].top < y && tiles[i][j].bottom > y) {

                            int k = i, m = j;

                            for (int ii = 0; ii < 4; ii++) {
                                for (int jj = 0; jj < 4; jj++) {

                                    if (ii == k || jj == m) {
                                        if (tiles[ii][jj].getColor() == 0)
                                            tiles[ii][jj].setColor(1);
                                        else
                                            tiles[ii][jj].setColor(0);
                                    }
                                }
                            }

                            checkForWin();
                            invalidate();
                            break;

                        }
                    }
                }
        }
        return true;
    }


    //TODO:
    public boolean checkForWin() {
        int sum = 0;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                sum += tiles[i][j].getColor();
            }
        }
        Log.d("my tag", "sum " + sum);

        if (sum == 4 * 4 || sum == 0) {
            Toast.makeText(getContext(),
                    "Победа, победа, вместо обеда!", Toast.LENGTH_SHORT).show();

            return true;
        } else {
            return false;
        }
    }


    //Функция для авто победы
    @Override
    public void onClick(View v) {
        Log.d("check", "functiom " + checkForWin());
        while (!checkForWin()) {
            Log.d("check", "in while ");
            for (int i = 0; i < tiles.length; i++) {
                for (int j = 0; j < tiles.length; j++) {
                    if (tiles[i][j].color == 0) {
                        tiles[i][j].setColor(1);
                    }
                }
            }

        }
    }

}


