package com.ruan.project.Other.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.administrator.ui_sdk.ItemClick;
import com.ruan.project.Moudle.Item;
import com.ruan.project.Moudle.ViewHolder;
import com.ruan.project.R;

import java.util.ArrayList;

/**
 * Created by Soft on 2016/7/9.
 */
public class SideListViewAdapter extends BaseAdapter {

    private Context context = null;
    private ArrayList<Object> list = null;
    private ViewHolder viewHolder = null;

    //子控件点击事件的接口
    private ItemClick itemClick = null;

    public SideListViewAdapter(Context context, ArrayList<Object> list) {
        this.context = context;
        this.list = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        Item item = (Item) list.get(position);

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.sideitem, null);

            viewHolder = new ViewHolder();

            viewHolder.sideDelete = (TextView) convertView.findViewById(R.id.delete);
            viewHolder.sideEdit = (TextView) convertView.findViewById(R.id.edit);
            viewHolder.Logo = (ImageView) convertView.findViewById(R.id.Logo);
            viewHolder.Title = (TextView) convertView.findViewById(R.id.Title);
            viewHolder.Subtitle = (TextView) convertView.findViewById(R.id.Subtitle);
            viewHolder.RightTitle = (TextView) convertView.findViewById(R.id.RightTitle);

            convertView.setTag(viewHolder);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.Title.setText(item.getTitle());
        viewHolder.Subtitle.setText(item.getSubtitle());
        viewHolder.Logo.setImageDrawable(item.getLogo());
        viewHolder.RightTitle.setText(item.getRightTitle());

        //子控件注册点击事件
        if (itemClick != null) {
            viewHolder.sideDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.OnClick(position, 1);
                }
            });
            viewHolder.sideEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.OnClick(position, 0);
                }
            });
        }

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
}
