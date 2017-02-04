package com.ike.saylovedemo;

import android.animation.ValueAnimator;
import android.media.DrmInitData;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ike.saylovedemo.adapter.MyPagerAdapter;
import com.ike.saylovedemo.heartView.HeartView;
import com.ike.saylovedemo.heartView.ZoomOutPageTransformer;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String Tag="MainActivity";
    private TextView tv_agree;
    private MoveTextView tv_disagree;
    private LinearLayout ll_parent;
    private HeartView heart_view;
    private ViewPager vp;
    private List<Integer> mData=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        tv_agree= (TextView) findViewById(R.id.tv_agree);
        tv_disagree= (MoveTextView) findViewById(R.id.tv_disagree);
        ll_parent= (LinearLayout) findViewById(R.id.ll_parent);
        heart_view= (HeartView) findViewById(R.id.heart_view);
        vp= (ViewPager) findViewById(R.id.vp);
        vp.setPageTransformer(true,new ZoomOutPageTransformer());
        initListener();
        initData();

    }

    private void initData() {
        for (int i=0;i<10;i++){
            mData.add(R.drawable.ic_launcher);
        }
        vp.setAdapter(new MyPagerAdapter(mData));
    }

    private void initListener() {
        tv_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backAnimation();
            }
        });
    }

    /**
     * 退场动画
     */
    public void backAnimation(){
        ViewPropertyAnimator.animate(ll_parent)
                .rotation(360)
                .alpha(0)
                .scaleX(0)
                .scaleY(0)
                .setDuration(500)
        .setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                heart_view.setVisibility(View.VISIBLE);
                heart_view.reDraw();
                vpShowAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        })
        ;
    }
    public void vpShowAnimation(){
        ValueAnimator animation=new ValueAnimator();
        animation.setDuration(1000);
        animation.setIntValues(-vp.getMeasuredHeight(),0);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value= (int) animation.getAnimatedValue();
                vp.setVisibility(View.VISIBLE);
                vp.setTranslationY(-value);
                vp.setAlpha(1-(-value*1.0f/vp.getMeasuredHeight()));
            }
        });
        animation.start();
    }
}
