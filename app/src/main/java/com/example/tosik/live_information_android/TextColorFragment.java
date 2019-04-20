package com.example.tosik.live_information_android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.tosik.live_information_android.ColorStack.AQUA;
import static com.example.tosik.live_information_android.ColorStack.BLACK;
import static com.example.tosik.live_information_android.ColorStack.BLUE;
import static com.example.tosik.live_information_android.ColorStack.BROWN;
import static com.example.tosik.live_information_android.ColorStack.GRAY;
import static com.example.tosik.live_information_android.ColorStack.GREEN;
import static com.example.tosik.live_information_android.ColorStack.LIME;
import static com.example.tosik.live_information_android.ColorStack.NAVY;
import static com.example.tosik.live_information_android.ColorStack.PINK;
import static com.example.tosik.live_information_android.ColorStack.RED;
import static com.example.tosik.live_information_android.ColorStack.WHITE;
import static com.example.tosik.live_information_android.ColorStack.YELLOW;


public class TextColorFragment extends Fragment {

    private Map<ColorStack,RadioButton> radioList = new HashMap<>();
    private ArrayList<TextView> textList = new ArrayList<>();
    private OnClickListener _clickListener;

    //Activityにイベント通知
    public interface OnClickListener {
        void onClickTextColor(ColorStack color);
        void setColorFirst();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View)inflater.inflate(R.layout.fragment_text_color_setting, container, false);

        radioList.put(BLUE, (RadioButton)view.findViewById(R.id.blue_radio));
        radioList.put(RED, (RadioButton)view.findViewById(R.id.red_radio));
        radioList.put(LIME, (RadioButton)view.findViewById(R.id.green_radio));
        radioList.put(WHITE, (RadioButton)view.findViewById(R.id.white_radio));
        radioList.put(BLACK, (RadioButton)view.findViewById(R.id.blak_radio));
        radioList.put(YELLOW, (RadioButton)view.findViewById(R.id.yello_radio));
        radioList.put(BROWN, (RadioButton)view.findViewById(R.id.brown_radio));
        radioList.put(GREEN, (RadioButton)view.findViewById(R.id.darkgreen_radio));
        radioList.put(NAVY, (RadioButton)view.findViewById(R.id.darkblue_radio));
        radioList.put(PINK, (RadioButton)view.findViewById(R.id.pink_radio));
        radioList.put(AQUA, (RadioButton)view.findViewById(R.id.aqua_radio));
        radioList.put(GRAY, (RadioButton)view.findViewById(R.id.gray_radio));

        textList.add((TextView)view.findViewById(R.id.blue_text));
        textList.add((TextView)view.findViewById(R.id.red_text));
        textList.add((TextView)view.findViewById(R.id.lime_text));
        textList.add((TextView)view.findViewById(R.id.white_text));
        textList.add((TextView)view.findViewById(R.id.black_text));
        textList.add((TextView)view.findViewById(R.id.yello_text));
        textList.add((TextView)view.findViewById(R.id.brown_text));
        textList.add((TextView)view.findViewById(R.id.green_text));
        textList.add((TextView)view.findViewById(R.id.navy_text));
        textList.add((TextView)view.findViewById(R.id.pink_text));
        textList.add((TextView)view.findViewById(R.id.aqua_text));
        textList.add((TextView)view.findViewById(R.id.gray_text));

        _clickListener.setColorFirst();

        //ラジオボタンセット
        radioList.get(BLUE).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(BLUE);
            }
        });
        radioList.get(LIME).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(LIME);
            }
        });
        radioList.get(RED).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(RED);
            }
        });
        radioList.get(WHITE).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(WHITE);
            }
        });
        radioList.get(BROWN).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(BROWN);
            }
        });
        radioList.get(GREEN).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(GREEN);
            }
        });
        radioList.get(NAVY).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(NAVY);
            }
        });
        radioList.get(BLACK).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(BLACK);
            }
        });
        radioList.get(PINK).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(PINK);
            }
        });
        radioList.get(YELLOW).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(YELLOW);
            }
        });
        radioList.get(AQUA).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(AQUA);
            }
        });
        radioList.get(GRAY).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setColor(GRAY);
            }
        });

        return view;
    }

    //Fragmentに文字色、ラジオボタン、セット
    public void setColor(ColorStack colorName){
        int color =  colorName.getColor();

        for(TextView textView:textList){ textView.setTextColor(color); }
        for(RadioButton radio:radioList.values()){ radio.setChecked(false); }
        radioList.get(colorName).setChecked(true);

        _clickListener.onClickTextColor(colorName);
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
