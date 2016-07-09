package com.dalong.adbanner.transformer;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
/**
 * 通明度动画
 * Created by zwl on 16/7/6.
 */
public class AdAlphaPageTransformer extends AdBasePageTransformer {

    private static final float DEFAULT_MIN_ALPHA = 0.5f;//默认通明度

    private float mMinAlpha = DEFAULT_MIN_ALPHA;//最小通明度

    public AdAlphaPageTransformer() {
    }
    /**
     * 构造方法
     * @param minAlpha 通明度
     */
    public AdAlphaPageTransformer(float minAlpha) {
        this(minAlpha, AdNonPageTransformer.INSTANCE);
    }

    /**
     * 构造方法
     * @param pageTransformer
     */
    public AdAlphaPageTransformer(ViewPager.PageTransformer pageTransformer) {
        this(DEFAULT_MIN_ALPHA, pageTransformer);
    }

    public AdAlphaPageTransformer(float minAlpha, ViewPager.PageTransformer pageTransformer) {
        mMinAlpha = minAlpha;
        mPageTransformer = pageTransformer;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void pageTransform(View view, float position) {
        view.setScaleX( 0.999f);//hack
        if (position < -1) { // [-Infinity,-1)
            view.setAlpha(mMinAlpha);
        } else if (position <= 1) { // [-1,1]

            if (position < 0){ //[0，-1]
                       //[1,min]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else{//[1，0]
                //[min,1]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else { // (1,+Infinity]
            view.setAlpha(mMinAlpha);
        }
    }
}
