package com.bjdv.lib.utils.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.bjdv.lib.utils.R;

/**
 * Created by liuliuchen on 2017/3/8.
 */

public class SoundUtil {

    private volatile static SoundUtil soundUtil = null;
    private Context mContext;

    private SoundUtil(Context context) {
        this.mContext = context;
        soundUtil = this;
    }

    public static SoundUtil getInstance(Context context) {
        if (soundUtil == null) {
            synchronized (SoundUtil.class) {
                if (soundUtil == null) {
                    soundUtil = new SoundUtil(context);
                }
            }
        }
        return soundUtil;
    }


    public void play(int type) {
        SoundPool pool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        int sourceid = pool.load(mContext, R.raw.ok, 0);
        switch (type) {
            case PlayType.yes:
                sourceid = pool.load(mContext, R.raw.ok, 0);
                break;
            case PlayType.no:
                sourceid = pool.load(mContext, R.raw.fauilar, 0);
                break;
        }
        final int finalSourceid = sourceid;
        pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(finalSourceid, 1, 1, 0, 0, 1);
            }
        });
        pool = null;
    }


    public interface PlayType {
        int yes = 1;
        int no = 0;
    }


}
