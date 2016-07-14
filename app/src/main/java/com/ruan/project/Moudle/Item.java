package com.ruan.project.Moudle;

import android.graphics.drawable.Drawable;

/**
 * Created by Soft on 2016/7/9.
 */
public class Item {

    private String delete = null;
    private String edit = null;
    private String Title = null;
    private String Subtitle = null;
    private Drawable Logo = null;
    private int height = 0;

    private Drawable gridImage = null;
    private String gridText = null;

    private Drawable listright = null;
    private Drawable listImage = null;
    private String listText = null;
    private String listSubText = null;
    private String listRightText = null;
    private String FLAG = null;

    public String getListRightText() {
        return listRightText;
    }

    public void setListRightText(String listRightText) {
        this.listRightText = listRightText;
    }

    public String getFLAG() {
        return FLAG;
    }

    public void setFLAG(String FLAG) {
        this.FLAG = FLAG;
    }

    public String getListSubText() {
        return listSubText;
    }

    public void setListSubText(String listSubText) {
        this.listSubText = listSubText;
    }

    public Drawable getListright() {
        return listright;
    }

    public void setListright(Drawable listright) {
        this.listright = listright;
    }

    public Drawable getListImage() {
        return listImage;
    }

    public void setListImage(Drawable listImage) {
        this.listImage = listImage;
    }

    public String getListText() {
        return listText;
    }

    public void setListText(String listText) {
        this.listText = listText;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Drawable getGridImage() {
        return gridImage;
    }

    public void setGridImage(Drawable gridImage) {
        this.gridImage = gridImage;
    }

    public String getGridText() {
        return gridText;
    }

    public void setGridText(String gridText) {
        this.gridText = gridText;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubtitle() {
        return Subtitle;
    }

    public void setSubtitle(String subtitle) {
        Subtitle = subtitle;
    }

    public Drawable getLogo() {
        return Logo;
    }

    public void setLogo(Drawable logo) {
        Logo = logo;
    }
}
