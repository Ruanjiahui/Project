package com.blink.blinkiot.Start;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.blink.blinkiot.Other.Adapter.FragmentAdapter;
import com.blink.blinkiot.R;
import com.blink.blinkiot.View.Fragment.LeadFragment1;
import com.blink.blinkiot.View.Fragment.LeadFragment2;
import com.blink.blinkiot.View.Fragment.LeadFragment3;
import com.example.administrator.data_sdk.ImageUtil.ImageTransformation;
import com.example.administrator.ui_sdk.Applications;
import com.example.administrator.ui_sdk.MyBaseActivity.BaseActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/20.
 */
public class LeadActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private ViewPager leadViewpager = null;
    private FragmentAdapter adapter = null;
    private ArrayList<Fragment> list = null;
    private View view = null;
    private ImageView leadImg1, leadImg2, leadImg3 = null;

    /**
     * Start()
     */
    @Override
    public void init() {
        setTileBar(0);

        view = LayoutInflater.from(context).inflate(R.layout.lead_main, null);

        leadViewpager = (ViewPager) view.findViewById(R.id.leadViewpager);
        leadImg1 = (ImageView) view.findViewById(R.id.leadImg1);
        leadImg2 = (ImageView) view.findViewById(R.id.leadImg2);
        leadImg3 = (ImageView) view.findViewById(R.id.leadImg3);


        list = new ArrayList<>();
        list.add(new LeadFragment1());
        list.add(new LeadFragment2());
        list.add(new LeadFragment3());

        adapter = new FragmentAdapter(getSupportFragmentManager(), list);
        leadViewpager.setAdapter(adapter);

        setContent(view);

        leadViewpager.setCurrentItem(0);
        Selector(0);


        leadViewpager.addOnPageChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Applications.getInstance().removeOneActivity(this);
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       Value from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels Value in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    /**
     * This method will be invoked when a new page becomes selected. Animation is not
     * necessarily complete.
     *
     * @param position Position index of the new selected page.
     */
    @Override
    public void onPageSelected(int position) {
        Selector(position);
    }

    /**
     * Called when the scroll state changes. Useful for discovering when the user
     * begins dragging, when the pager is automatically settling to the current page,
     * or when it is fully stopped/idle.
     *
     * @param state The new scroll state.
     * @see ViewPager#SCROLL_STATE_IDLE
     * @see ViewPager#SCROLL_STATE_DRAGGING
     * @see ViewPager#SCROLL_STATE_SETTLING
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void Selector(int position) {
        switch (position) {
            case 0:
                leadImg1.setImageDrawable(getBackDrawable(R.mipmap.dot1));
                leadImg2.setImageDrawable(getBackDrawable(R.mipmap.dot));
                leadImg3.setImageDrawable(getBackDrawable(R.mipmap.dot));
                break;
            case 1:
                leadImg1.setImageDrawable(getBackDrawable(R.mipmap.dot));
                leadImg2.setImageDrawable(getBackDrawable(R.mipmap.dot1));
                leadImg3.setImageDrawable(getBackDrawable(R.mipmap.dot));
                break;
            case 2:
                leadImg1.setImageDrawable(getBackDrawable(R.mipmap.dot));
                leadImg2.setImageDrawable(getBackDrawable(R.mipmap.dot));
                leadImg3.setImageDrawable(getBackDrawable(R.mipmap.dot1));
                break;
        }
    }

    private Drawable getBackDrawable(int resid) {
        return ImageTransformation.Resouce2Drawable(context, resid);
    }
}
