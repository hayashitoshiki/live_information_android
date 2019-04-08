package com.example.tosik.live_information_android;


//文字色箱
public enum ColorStack {

        RED("RED",255,0,0),
        LIME("LIME",0,255,0),
        BLUE("BLUE",0,0,255),
        WHITE("WHITE",255,255,255),
        BROWN("BROWN",128,0,0),
        GREEN("GREEN",0,128,0),
        NAVY("NAVY",0,0,128),
        BLACK("BLACK",0,0,0),
        YELLOW("YELLOW",255,255,0),
        AQUA("AQUA",0,255,255),
        PINK("PINK",255,0,255),
        GRAY("GRAY",128,128,128);

        private String name;
        private int red;
        private int green;
        private int blue;

        ColorStack (String name, int red, int green,int blue){
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.name = name;
        }

        public String getName(){return name;}
        public int getRed(){return red;}
        public int getGreen(){return green;}
        public int getBlue(){return blue;}



}
