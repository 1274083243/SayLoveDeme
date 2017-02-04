package com.ike.saylovedemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Administrator on 2017/2/4.
 */

public class MyPagerAdapter extends PagerAdapter {
    private String Tag="MyPagerAdapter";
    List<Integer> mData;

    public MyPagerAdapter(List<Integer> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        if (mData!=null&&!mData.isEmpty()){
            return mData.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);

        Log.e(Tag,"mData.get(position):"+mData.get(position));
       Glide.with(container.getContext()).load(mData.get(position)).into(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }
}
