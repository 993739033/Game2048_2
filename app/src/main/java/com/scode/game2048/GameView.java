package com.scode.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 知らないのセカイ on 2017/4/23.
 */

public class GameView extends GridLayout {
    public GameView(Context context) {
        super(context);
        initView(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private Point point = null;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int cardsize = (Math.min(w, h) - 30) / 4;
//        Log.w("sizechange","sizechange");

        addCard(cardsize, cardsize);
        startGame();

    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int cardsize =(Math.min(getMeasuredWidth(), getMeasuredHeight()) - 10) / 4;
//        addCard(cardsize,cardsize);
//    }

    private void addCard(int width, int height) {
        Card card;
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                card = new Card(getContext());
                Cards[x][y] = card;
//                card.setNum(4);
//                Log.d("getnum", String.valueOf(card.getNum()));
//                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, height);
                //addView(card,layoutParams);
                this.addView(card, width, height);
            }
        }
    }

    private Card[][] Cards = new Card[4][4];
    private List<Point> emptyPoint = new ArrayList<>();






    public  void startGame() {
        MainActivity.getMainActivity().clearScore();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Cards[x][y].setNum(0);
            }

        }
        getRandomNum();
        getRandomNum();
        setColor();


    }

    private void getRandomNum() {
        emptyPoint.clear();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                if (Cards[x][y].getNum() <= 0) {
                    Point point = new Point(x, y);
                    emptyPoint.add(point);
                }
            }
        }
        if (emptyPoint.size()!=0) {
            Point point = emptyPoint.remove((int) (Math.random() * (emptyPoint.size() - 1)));
            Cards[point.x][point.y].setNum(Math.random() > 0.1 ? 2 : 4);
        }else{
            Checkout();

        }


    }


    private int offsetX, offsetY;
    private void Checkout(){
        boolean complete=true;

        ALL:for (int x=0;x<3;x++) {
            for (int y=0;y<3;y++) {
                if ((Cards[x][y].equals(Cards[x+1][y]))||(Cards[x][y].equals(Cards[x][y+1])))
                {
                  complete=false;
                    break ALL;

                }
            }
        }
        if (complete){
            new AlertDialog.Builder(getContext()).setTitle("GAME OVER").setMessage("YOU SCORE:"+MainActivity.getMainActivity().getScore()+"\n"+"PLAY AGAIN?")
                    .setNegativeButton("OUT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setPositiveButton("RESTART", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   startGame();
                }
            }).show();

        }


    }

    private void initView(Context context) {
        setBackgroundColor(0xffbbada0);
        setColumnCount(4);


        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                for (int y = 0; y < 4; y++) {
//                    for (int x = 0; x < 3; x++) {
//                        Cards[x][y]
//
//                    }}
                //TODO 添加颜色变化
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        point = new Point();
                        point.x = (int) event.getX();
                        point.y = (int) event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX = (int) (event.getX() - point.x);
                        offsetY = (int) (event.getY() - point.y);
                        if (Math.abs(offsetX) > Math.abs(offsetY)) {
                            if (offsetX > 5) {
                                swipeRight();
                                getRandomNum();
                                setColor();
                            } else if (offsetX < -5) {
                                swipeLeft();
                                getRandomNum();
                                setColor();
                            }
                        } else {
                            if (offsetY > 5) {
                                swipeDown();
                                getRandomNum();
                                setColor();
                            } else if (offsetY < -5) {
                                swipeUp();
                                getRandomNum();
                                setColor();
                            }
                        }
                        break;
                }
                return true;
            }
        });

    }

    private void swipeLeft() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                if (Cards[y][x + 1].getNum() > 0) {
                    if (Cards[y][x].getNum() <= 0) {
                        Cards[y][x].setNum(Cards[y][x + 1].getNum());
                        Cards[y][x + 1].setNum(0);

                        if (x > 0 && (Cards[y][x - 1].getNum() == 0 || Cards[y][x - 1].equals(Cards[y][x]))) {
                            x -= 2;
                        }
                    } else if (Cards[y][x].equals(Cards[y][x + 1])) {
                        Cards[y][x].setNum(Cards[y][x].getNum() * 2);
                        Cards[y][x + 1].setNum(0);
                        MainActivity.getMainActivity().addScore(Cards[y][x].getNum());
                        //TODO 这里可能会有bug产生
//                      if (x>=0&Cards[y][x-1].getNum()==0){
//                          x--;
//                      }

                    }
                }

            }
        }
    }

    private void swipeRight() {
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x > 0; x--) {
                if (Cards[y][x - 1].getNum() > 0) {
                    if (Cards[y][x].getNum() <= 0) {
                        Cards[y][x].setNum(Cards[y][x - 1].getNum());
                        Cards[y][x - 1].setNum(0);

                        if (x < 3 && (Cards[y][x + 1].getNum() == 0 || Cards[y][x + 1].equals(Cards[y][x]))) {
                            x += 2;
                        }
                    } else if (Cards[y][x].equals(Cards[y][x - 1])) {
                        Cards[y][x].setNum(Cards[y][x - 1].getNum() * 2);
                        Cards[y][x - 1].setNum(0);
                        MainActivity.getMainActivity().addScore(Cards[y][x].getNum());
                        //TODO 这里可能会有bug产生
//                      if (x>=0&Cards[y][x-1].getNum()==0){
//                          x--;
//                      }
                    }
                }

            }
        }

    }

    private void swipeUp() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 3; y++) {
                if (Cards[y + 1][x].getNum() > 0) {
                    if (Cards[y][x].getNum() <= 0) {
                        Cards[y][x].setNum(Cards[y + 1][x].getNum());
                        Cards[y + 1][x].setNum(0);

                        if (y > 0 && (Cards[y - 1][x].getNum() == 0 || Cards[y - 1][x].equals(Cards[y][x]))) {
                            y -= 2;
                        }
                    } else if (Cards[y][x].equals(Cards[y + 1][x])) {
                        Cards[y][x].setNum(Cards[y][x].getNum() * 2);
                        Cards[y + 1][x].setNum(0);
                        MainActivity.getMainActivity().addScore(Cards[y][x].getNum());
                        //TODO 这里可能会有bug产生
//                      if (x>=0&Cards[y][x-1].getNum()==0){
//                          x--;
//                      }
                    }
                }

            }
        }
    }

    private void swipeDown() {
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y > 0; y--) {
                if (Cards[y - 1][x].getNum() > 0) {
                    if (Cards[y][x].getNum() <= 0) {
                        Cards[y][x].setNum(Cards[y - 1][x].getNum());
                        Cards[y - 1][x].setNum(0);

                        if (y < 3 && (Cards[y + 1][x].getNum() == 0 || Cards[y + 1][x].equals(Cards[y][x]))) {
                            y += 2;
                        }
                    } else if (Cards[y][x].equals(Cards[y - 1][x])) {
                        Cards[y][x].setNum(Cards[y][x].getNum() * 2);
                        Cards[y - 1][x].setNum(0);
                      MainActivity.getMainActivity().addScore(Cards[y][x].getNum());
                        //TODO 这里可能会有bug产生
//                      if (x>=0&Cards[y][x-1].getNum()==0){
//                          x--;
//                      }
                    }
                }
            }
        }

    }
    Resources resources=getContext().getResources();
    private void setColor() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                switch (Cards[x][y].getNum()) {
                    case 2:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color1));
                        break;
                    case 4:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color2));
                        break;
                    case 8:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color3));
                        break;
                    case 16:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color4));
                        break;
                    case 32:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color5));
                        break;
                    case 64:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color6));
                        break;
                    case 128:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color7));
                        break;
                    case 256:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color8));
                        break;
                    case 512:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color9));
                        break;
                    case 1024:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color10));
                        break;
                    case 2048:
                        Cards[x][y].getLabel().setBackgroundColor(resources.getColor(R.color.color11));
                        break;
                    default:
                        Cards[x][y].getLabel().setBackgroundColor(0x33ffffff);
                        break;
                }
            }

        }
    }
}
