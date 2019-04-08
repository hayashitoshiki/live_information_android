package com.example.tosik.live_information_android;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class BackgroundSetting extends Fragment {
    private String title = "null";
    private RadioButton r_button;
    private RadioButton w_button;
    private RadioButton f_button;
    private RadioButton s_button;
    private Button c_button;
    private View view;

    private TextView rTextView;
    private TextView wTextView;
    private TextView fTextView;
    private TextView sTextView;


    OnClickListener _clickListener;

    //Activityにイベント通知
    public interface OnClickListener {
        void onClickBackImage(String title);
        void setImageFirst();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View)inflater.inflate(R.layout.fragment_background_setting, container, false);

        r_button = (RadioButton)view.findViewById(R.id.relax_set);
        w_button = (RadioButton)view.findViewById(R.id.wate_set);
        f_button = (RadioButton)view.findViewById(R.id.fire_set);
        s_button = (RadioButton)view.findViewById(R.id.sun_set);
        c_button = (Button)view.findViewById(R.id.clrea_button);

        rTextView = (TextView)view.findViewById(R.id.relax_text);
        wTextView = (TextView)view.findViewById(R.id.wate_text);
        fTextView = (TextView)view.findViewById(R.id.fire_text);
        sTextView = (TextView)view.findViewById(R.id.sun_text);

       _clickListener. setImageFirst();



        //ラジオボタンセット
        r_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title="リラックス";
                setRadioButton(title);
            }
        });
        w_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title="水滴";
                setRadioButton(title);
            }
        });
        f_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title="炎";
                setRadioButton(title);
            }
        });
        s_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title="光";
                setRadioButton(title);
            }
        });
        c_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                title = "なし";
                setRadioButton(title);
            }
        });
        return view;
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


    //背景ボタンセット
    public void setRadioButton(String title) {
        Log.d("BackgroundSetting", "タイトル："+title);
        switch(title){
            case "リラックス":
                r_button.setChecked(true);
                w_button.setChecked(false);
                f_button.setChecked(false);
                s_button.setChecked(false);
                break;
            case "水滴":
                r_button.setChecked(false);
                w_button.setChecked(true);
                f_button.setChecked(false);
                s_button.setChecked(false);
                break;
            case "炎":
                r_button.setChecked(false);
                w_button.setChecked(false);
                f_button.setChecked(true);
                s_button.setChecked(false);
                break;
            case "光":
                r_button.setChecked(false);
                w_button.setChecked(false);
                f_button.setChecked(false);
                s_button.setChecked(true);
                break;
            default:
                r_button.setChecked(false);
                w_button.setChecked(false);
                f_button.setChecked(false);
                s_button.setChecked(false);
                break;
        }
        _clickListener.onClickBackImage(title);
    }

    //文字色セット
    public void setColor(ColorStack color){
        rTextView.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        wTextView.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        fTextView.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
        sTextView.setTextColor(Color.rgb(color.getRed(),color.getGreen(),color.getBlue()));
    }
}
