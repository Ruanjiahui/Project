package com.ruan.project.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruan.project.R;

/**
 * Created by Administrator on 2016/8/2.
 */
public class ScanDeviceFragment extends Fragment {

    private View view = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.scandevice, container, false);

        return view;
    }
}
