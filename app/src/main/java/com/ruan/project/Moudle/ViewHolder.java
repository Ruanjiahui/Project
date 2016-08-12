package com.ruan.project.Moudle;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruan.project.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Soft on 2016/7/9.
 */
public class ViewHolder {


    public LinearLayout gridLinear;
    public ImageView gridImage;
    public TextView gridText;
    public ImageView gridCenterImage = null;


    public TextView Sorttitle;
    public TextView Sortname;


    public RelativeLayout listRelative;
    public ImageView listImage;
    public TextView listText;
    public TextView listSubText;
    public ImageView listright;
    public TextView listRightText;


    public RelativeLayout homeRelative;
    public ImageView homeImage;
    public TextView homeText;
    public TextView homeSubText;
    public ImageView homeRightImage;
    public TextView homeRight;
    public TextView homeRight1;
    public TextView edit;
    public TextView delete;


    public ViewHolder(View view, String position) {
        //带有侧滑菜单的
        switch (position) {
            case "SideView":
                homeRelative = (RelativeLayout) view.findViewById(R.id.homeRelative);
                homeImage = (ImageView) view.findViewById(R.id.homeImage);
                homeText = (TextView) view.findViewById(R.id.homeText);
                homeSubText = (TextView) view.findViewById(R.id.homeSubText);
                homeRightImage = (ImageView) view.findViewById(R.id.homeRightImage);
                homeRight = (TextView) view.findViewById(R.id.homeRight);
                homeRight1 = (TextView) view.findViewById(R.id.homeRight1);
                edit = (TextView) view.findViewById(R.id.edit);
                delete = (TextView) view.findViewById(R.id.delete);
                break;
            case "ListView":
                //纯listview布局
                listRelative = (RelativeLayout) view.findViewById(R.id.listRelative);
                listImage = (ImageView) view.findViewById(R.id.listImage);
                listText = (TextView) view.findViewById(R.id.listText);
                listSubText = (TextView) view.findViewById(R.id.listSubText);
                listright = (ImageView) view.findViewById(R.id.listright);
                listRightText = (TextView) view.findViewById(R.id.listRightText);
                break;
            case "GridView":
                //纯GirdView
                gridLinear = (LinearLayout) view.findViewById(R.id.gridLinear);
                gridImage = (ImageView) view.findViewById(R.id.gridImage);
                gridText = (TextView) view.findViewById(R.id.gridText);
                gridCenterImage = (ImageView) view.findViewById(R.id.gridCenterImage);
                break;

            case "SortListView":
                Sorttitle = (TextView) view.findViewById(R.id.title);
                Sortname = (TextView) view.findViewById(R.id.name);
                break;
        }

    }
}
