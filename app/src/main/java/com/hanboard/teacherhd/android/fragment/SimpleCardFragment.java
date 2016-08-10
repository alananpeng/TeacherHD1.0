package com.hanboard.teacherhd.android.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hanboard.teacherhd.R;


@SuppressLint("ValidFragment")
public class SimpleCardFragment extends Fragment {
    private String mTitle;
    private TextView mTextView;

    public static SimpleCardFragment getInstance(String title) {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        mTextView = (TextView) v.findViewById(R.id.card_title_tv);
        mTextView.setText("这是"+mTitle);
        return v;
    }


}