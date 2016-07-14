package com.ruan.project.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.ui_sdk.DensityUtil;
import com.ruan.project.R;


/**
 * Created by Soft on 2016/6/23.
 */
public class Fragment2  extends Fragment {

    private View view = null;

    private Context context = null;

    private View fragment2Top = null;
    private RelativeLayout base_top_relative = null;
    private TextView base_top_text1 = null;
    private TextView base_top_title = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment2 , container , false);

        fragment2Top = view.findViewById(R.id.fragment2Top);
        base_top_relative = (RelativeLayout) view.findViewById(R.id.base_top_relative);
        base_top_text1 = (TextView) view.findViewById(R.id.base_top_text1);
        base_top_title = (TextView) view.findViewById(R.id.base_top_title);

        fragment2Top.setBackgroundResource(R.color.Blue);
        base_top_title.setPadding(0 , DensityUtil.dip2px(getActivity() , 20) , 0 , 0);
        base_top_title.setText("场景");
        base_top_title.setTextColor(getResources().getColor(R.color.White));
        base_top_relative.setVisibility(View.GONE);
        base_top_text1.setText("新建场景");

        return view;
    }
}
