package com.scode.game2048;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by 知らないのセカイ on 2017/4/23.
 */

public class Card extends FrameLayout {

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num <= 0) {
            label.setText("");
        }
        else if (num>=2){
            label.setText(num + "");

        }
    }

    public boolean equals(Card obj) {
        return getNum()==obj.getNum();
    }

    private int num=0;

    public TextView getLabel() {
        return label;
    }

    private TextView label;
    public Card( Context context) {
        super(context);

        label = new TextView(getContext());
        label.setTextSize(32);
        label.setBackgroundColor(0x33ffffff);

        label.setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(30,30,0,0);
        label.setText(getNum()+"");



        addView(label, layoutParams);
    }


}
