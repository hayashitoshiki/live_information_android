package com.example.tosik.live_information_android;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;


public class TextColorSetting extends Fragment {

    private RadioButton blueButton;
    private RadioButton redButton;
    private RadioButton limeButton;
    private RadioButton whiteButton;

    private RadioButton brownButton;
    private RadioButton greenButton;
    private RadioButton navyButton;
    private RadioButton blackButton;

    private RadioButton pinkButton;
    private RadioButton aquaButton;
    private RadioButton yellowButton;
    private RadioButton grayButton;


    private TextView blueText;
    private TextView redText;
    private TextView limeText;
    private TextView whiteText;

    private TextView brownText;
    private TextView greenText;
    private TextView navyText;
    private TextView blackText;

    private TextView pinkText;
    private TextView aquarText;
    private TextView yellowText;
    private TextView grayText;

    private ColorStack color;
    private ColorStack colo;
    OnClickListener _clickListener;

    //Activityにイベント通知
    public interface OnClickListener {
        void onClickTextColor(ColorStack color);
        void setColorFirst();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.fragment_text_color_setting, container, false);

        blueButton = (RadioButton)view.findViewById(R.id.blue_radio);
        redButton = (RadioButton)view.findViewById(R.id.red_radio);
        limeButton = (RadioButton)view.findViewById(R.id.green_radio);
        whiteButton = (RadioButton)view.findViewById(R.id.white_radio);
        blackButton = (RadioButton)view.findViewById(R.id.blak_radio);
        yellowButton = (RadioButton)view.findViewById(R.id.yello_radio);
        brownButton = (RadioButton)view.findViewById(R.id.brown_radio);
        greenButton = (RadioButton)view.findViewById(R.id.darkgreen_radio);
        navyButton = (RadioButton)view.findViewById(R.id.darkblue_radio);
        pinkButton = (RadioButton)view.findViewById(R.id.pink_radio);
        aquaButton = (RadioButton)view.findViewById(R.id.aqua_radio);
        grayButton = (RadioButton)view.findViewById(R.id.gray_radio);

        blueText = (TextView)view.findViewById(R.id.blue_text);
        redText= (TextView)view.findViewById(R.id.red_text);
        limeText= (TextView)view.findViewById(R.id.lime_text);
        whiteText= (TextView)view.findViewById(R.id.white_text);
        blackText = (TextView)view.findViewById(R.id.black_text);
        yellowText= (TextView)view.findViewById(R.id.yello_text);
        brownText = (TextView)view.findViewById(R.id.brown_text);
        greenText = (TextView)view.findViewById(R.id.green_text);
        navyText = (TextView)view.findViewById(R.id.navy_text);
        pinkText = (TextView)view.findViewById(R.id.pink_text);
        aquarText = (TextView)view.findViewById(R.id.aqua_text);
        grayText = (TextView)view.findViewById(R.id.gray_text);

        _clickListener.setColorFirst();

        //ラジオボタンセット
        blueButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                colo = ColorStack.valueOf("BLUE");
                setColor(colo);
            }
        });
        limeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                colo = ColorStack.valueOf("LIME");
                setColor(colo);
            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color = ColorStack.valueOf("RED");
                setColor(color);
            }
        });
        whiteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color = ColorStack.valueOf("WHITE");
                setColor(color);
            }
        });

        brownButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color = ColorStack.valueOf("BROWN");
                setColor(color);
            }
        });
        greenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color =  ColorStack.valueOf("GREEN");
                setColor(color);
            }
        });
        navyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color =  ColorStack.valueOf("NAVY");
                setColor(color);
            }
        });
        blackButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color =  ColorStack.valueOf("BLACK");
                setColor(color);
            }
        });

        pinkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color = ColorStack.valueOf("PINK");
                setColor(color);
            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color =  ColorStack.valueOf("YELLOW");
                setColor(color);
            }
        });
        aquaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color =  ColorStack.valueOf("AQUA");
                setColor(color);
            }
        });
        grayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                color =  ColorStack.valueOf("GRAY");
                setColor(color);
            }
        });

        return view;
    }

    //Fragmentに文字色、ラジオボタン、セット
    public void setColor(ColorStack color){
        blueText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        redText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        limeText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        whiteText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        brownText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        greenText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        navyText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        blackText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        yellowText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        pinkText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        aquarText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        grayText.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));


        blueButton.setChecked(false);
        redButton.setChecked(false);
        limeButton.setChecked(false);
        whiteButton.setChecked(false);
        blackButton.setChecked(false);
        yellowButton.setChecked(false);
        brownButton.setChecked(false);
        greenButton.setChecked(false);
        navyButton.setChecked(false);
        pinkButton.setChecked(false);
        aquaButton.setChecked(false);
        grayButton.setChecked(false);

        switch(color){
            case RED:
                redButton.setChecked(true);
                break;
            case BLUE:
                blueButton.setChecked(true);
                break;
            case LIME:
                limeButton.setChecked(true);
                break;
            case WHITE:
                whiteButton.setChecked(true);
                break;
            case BROWN:
                brownButton.setChecked(true);
                break;
            case GREEN:
                greenButton.setChecked(true);
                break;
            case NAVY:
                navyButton.setChecked(true);
                break;
            case BLACK:
                blackButton.setChecked(true);
                break;
            case PINK:
                pinkButton.setChecked(true);
                break;
            case YELLOW:
                yellowButton.setChecked(true);
                break;
            case AQUA:
                aquaButton.setChecked(true);
                break;

        }
        _clickListener.onClickTextColor(color);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            _clickListener = (OnClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "must implement OnArticleSelectedListener.");
        }
    }
}
