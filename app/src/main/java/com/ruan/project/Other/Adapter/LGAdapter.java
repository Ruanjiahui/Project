package com.ruan.project.Other.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.ui_sdk.DensityUtil;
import com.example.administrator.ui_sdk.ItemClick;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Moudle.ViewHolder;
import com.ruan.project.R;

import java.util.ArrayList;

/**
 * Created by Soft on 2016/7/9.
 */
public class LGAdapter extends BaseAdapter implements View.OnClickListener {

    private Context context = null;
    private ArrayList<Object> list = null;
    private ViewHolder viewHolder = null;
    private String state = null;

    //子控件点击事件的接口
    private ItemClick itemClick = null;

    public LGAdapter(Context context, ArrayList<Object> list, String state) {
        this.context = context;
        this.list = list;
        this.state = state;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return null;
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Item item = (Item) list.get(position);

        if (convertView == null) {

            viewHolder = new ViewHolder();
            if ("ListView".equals(state)) {
                convertView = LayoutInflater.from(context).inflate(R.layout.listitem, null);

                viewHolder.listImage = (ImageView) convertView.findViewById(R.id.listImage);
                viewHolder.listright = (ImageView) convertView.findViewById(R.id.listright);
                viewHolder.listText = (TextView) convertView.findViewById(R.id.listText);
                viewHolder.listSubText = (TextView) convertView.findViewById(R.id.listSubText);
                viewHolder.listRelative = (RelativeLayout) convertView.findViewById(R.id.listRelative);
                viewHolder.listRightText = (TextView) convertView.findViewById(R.id.listRightText);
            } else if ("GridView".equals(state)) {
                convertView = LayoutInflater.from(context).inflate(R.layout.girditem, null);

                viewHolder.gridImage = (ImageView) convertView.findViewById(R.id.gridImage);
                viewHolder.gridText = (TextView) convertView.findViewById(R.id.gridText);
                viewHolder.gridLinear = (LinearLayout) convertView.findViewById(R.id.gridLinear);
            }

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        if ("ListView".equals(state)) {
            if ("-".equals(item.getListText())) {
                viewHolder.listRelative.setBackgroundColor(context.getResources().getColor(R.color.GreySmoke));
            } else {
                viewHolder.listRelative.setBackgroundResource(R.drawable.itemsector);
                viewHolder.listText.setText(item.getListText());
                viewHolder.listImage.setImageDrawable(item.getListImage());
                viewHolder.listright.setImageDrawable(item.getListright());
                viewHolder.listRightText.setText(item.getListRightText());
                if (item.getListSubText() != null) {
                    viewHolder.listSubText.setText(item.getListSubText());
                    viewHolder.listSubText.setVisibility(View.VISIBLE);
                }
            }
            if (item.getHeight() != 0)
                DensityUtil.setHeight(viewHolder.listRelative, item.getHeight());
        } else if ("GridView".equals(state)) {
            viewHolder.gridText.setText(item.getGridText());
            viewHolder.gridImage.setImageDrawable(item.getGridImage());
            if (item.getHeight() != 0)
                DensityUtil.setHeight(viewHolder.gridLinear, item.getHeight());

        }
        //子控件注册点击事件
//        if (itemClick != null) {
//            viewHolder.sideDelete.setOnClickListener(this);
//            viewHolder.sideEdit.setOnClickListener(this);
//        }

        return convertView;
    }

    /**
     * 外部调用的接口  子控件的点击事件
     *
     * @param itemClick
     */
    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    /**
     * 外部调用的接口   更新数据
     *
     * @param list
     */
    public void RefreshData(ArrayList<Object> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            //删除的点击事件
//            case R.id.delete:
//                itemClick.OnClick(1);
//                break;
//            //编辑的点击事件
//            case R.id.edit:
//                itemClick.OnClick(0);
//                break;
//        }
    }
}
