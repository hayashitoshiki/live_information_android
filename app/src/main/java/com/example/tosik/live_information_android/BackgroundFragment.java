package com.example.tosik.live_information_android;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BackgroundFragment extends Fragment {
    private Map<String, RadioButton> radioList = new HashMap<>();
    private ArrayList<TextView> textList = new ArrayList<>();
    private OnClickListener _clickListener;
    private Button c_button;
    private View view;

    //Activityにイベント通知
    public interface OnClickListener {
        void onClickBackImage(String title);
        void setImageFirst();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (View)inflater.inflate(R.layout.fragment_background_setting, container, false);

        radioList.put("note",(RadioButton)view.findViewById(R.id.relax_set));
        radioList.put("water",(RadioButton)view.findViewById(R.id.wate_set));
        radioList.put("fire",(RadioButton)view.findViewById(R.id.fire_set));
        radioList.put("sun",(RadioButton)view.findViewById(R.id.sun_set));
        c_button = (Button)view.findViewById(R.id.clrea_button);

        textList.add((TextView)view.findViewById(R.id.relax_text));
        textList.add((TextView)view.findViewById(R.id.wate_text));
        textList.add((TextView)view.findViewById(R.id.fire_text));
        textList.add((TextView)view.findViewById(R.id.sun_text));

       _clickListener. setImageFirst();

        //ラジオボタンセット
        radioList.get("note").setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setRadioButton("note");
            }
        });

        radioList.get("water").setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setRadioButton("water");
            }
        });
        radioList.get("fire").setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setRadioButton("fire");
            }
        });
        radioList.get("sun").setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setRadioButton("sun");
            }
        });
        c_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setRadioButton(null);
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
        Log.d("BackgroundFragment", "タイトル："+title);
        for(RadioButton r:radioList.values()){r.setChecked(false);}
        if(title != null)radioList.get(title).setChecked(true);

        _clickListener.onClickBackImage(title);
    }

    //文字色セット
    public void setColor(int color){
        for(TextView textView:textList){textView.setTextColor(color);}
    }
}
