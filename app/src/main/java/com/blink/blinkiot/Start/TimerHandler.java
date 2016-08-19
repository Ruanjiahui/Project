package com.blink.blinkiot.Start;

import android.os.Message;

/**
 * Created by Administrator on 2016/3/18.
 */
public interface TimerHandler {

    public void timerHandler(Message msg);

    /**
     * 调用标示
     *
     * @param position
     * @return
     */
    public Message timerRun(int position);


}
