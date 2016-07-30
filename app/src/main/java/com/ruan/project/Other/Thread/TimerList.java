package com.ruan.project.Other.Thread;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by Administrator on 2016/7/27.
 */
public class TimerList {

    private static TimerList timerList = null;
    private static ArrayList<Timer> list = new ArrayList<>();

    /**
     * 全局单变量
     *
     * @return
     */
    public static synchronized TimerList getIntanse() {
        if (timerList == null)
            timerList = new TimerList();
        return timerList;
    }

    /**
     * 将timer添加到链表里面去
     *
     * @param timer
     */
    public void addTime(Timer timer) {
        list.add(timer);
    }

    /**
     * 移除单项
     *
     * @param timer
     */
    public void removeOne(Timer timer) {
        for (Timer timer1 : list) {
            if (timer == timer1) {
                timer1.cancel();
                list.remove(timer1);
            }
        }
    }

    /**
     * 移除所有项
     */
    public void removeAll() {
        for (Timer timer1 : list) {
            timer1.cancel();
            list.remove(timer1);
        }
    }
}
