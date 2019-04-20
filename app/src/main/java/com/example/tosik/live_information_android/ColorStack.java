package com.example.tosik.live_information_android;


import android.graphics.Color;

//文字色箱
public enum ColorStack {

        RED(Color.rgb(255,0,0)),
        LIME(Color.rgb(0,255,0)),
        BLUE(Color.rgb(0,0,255)),
        WHITE(Color.rgb(255,255,255)),
        BROWN(Color.rgb(128,0,0)),
        GREEN(Color.rgb(0,128,0)),
        NAVY(Color.rgb(0,0,128)),
        BLACK(Color.rgb(0,0,0)),
        YELLOW(Color.rgb(255,255,0)),
        AQUA(Color.rgb(0,255,255)),
        PINK(Color.rgb(255,0,255)),
        GRAY(Color.rgb(128,128,128));

        private int color;

        ColorStack (int color){ this.color = color; }

        public int getColor(){return color;}

}
