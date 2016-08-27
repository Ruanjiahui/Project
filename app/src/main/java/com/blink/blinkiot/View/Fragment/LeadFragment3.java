package com.blink.blinkiot.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blink.blinkiot.R;
import com.blink.blinkiot.Start.ActivityCode;
import com.blink.blinkiot.Start.MainActivity;
import com.blink.blinkiot.View.Activity.Login;
import com.example.administrator.data_sdk.CommonIntent;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;

/**
 * Created by Administrator on 2016/8/20.
 */
public class LeadFragment3 extends Fragment implements View.OnClickListener {

    private View view = null;
    private TextView leadfragment3But = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.leadfragment3, null);

        leadfragment3But = (TextView) view.findViewById(R.id.leadfragment3But);


        leadfragment3But.setOnClickListener(this);

        return view;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
//        CommonIntent.IntentActivity(getActivity(), Login.class , ActivityCode.GUIDE);
        CommonIntent.IntentActivity(getActivity(), MainActivity.class);
    }
}
