package com.ike.saylovedemo.widget;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ike.saylovedemo.R;


import java.util.Random;

/**
 * 作者：ike
 * 时间：2017/3/13 10:18
 * 功能描述：心形点赞布局
 **/
public class LoveLayout extends RelativeLayout {
    private Drawable pic_1, pic_2, pic_3;
    private Drawable[] drawableList = new Drawable[3];
    private Random random;
    private int mWidth, mHeight;

    public LoveLayout(Context context) {
        this(context, null);
    }

    public LoveLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoveLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        random = new Random();
        drawableList[0] = pic_1 = getResources().getDrawable(R.drawable.dress_3x);
        drawableList[1] = pic_2 = getResources().getDrawable(R.drawable.beauty_makeup_3x);
        drawableList[2] = pic_3 = getResources().getDrawable(R.drawable.shose_3x);
    }

    /**
     * 添加图片到心形布局中
     */
    public void addViewToLayout(int picNum) {
        Drawable pic = drawableList[picNum];
        ImageView iv = new ImageView(getContext());
        RelativeLayout.LayoutParams layoutParam = new RelativeLayout.LayoutParams(400, 400);
        layoutParam.addRule(CENTER_HORIZONTAL, TRUE);
        layoutParam.addRule(ALIGN_PARENT_BOTTOM, TRUE);
        iv.setLayoutParams(layoutParam);
        iv.setImageDrawable(pic);
        addView(iv);
        getEnterAnimator(iv).start();
        ValueAnimator bezierValueAnimator = getBezierValueAnimator(iv);
        bezierValueAnimator.addListener(new MyAnimatorListener(iv));
        bezierValueAnimator.start();
    }

    /**
     * 获取心形出现时的动画效果
     *
     * @param target：所要设置动画的目标view
     * @return
     */
    private AnimatorSet getEnterAnimator(View target) {
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(target, "alpha", 0.2f, 1.0f);
        ObjectAnimator scalXAnimator = ObjectAnimator.ofFloat(target, "scaleX", 0.5f, 1.0f);
        ObjectAnimator scalYAnimator = ObjectAnimator.ofFloat(target, "scaleY", 0.5f, 1.0f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.playTogether(alphaAnimator, scalXAnimator, scalYAnimator);
        enter.setTarget(target);
        return enter;
    }

    private ValueAnimator getBezierValueAnimator(View target) {

        //初始化一个BezierEvaluator
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(2), getPointF(1));

        //这里最好画个图 理解一下 传入了起点 和 终点
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF((mWidth - 400) / 2, (mHeight - 400)), new PointF(random.nextInt(getWidth()), 0));//随机
        animator.addUpdateListener(new BezierListenr(target));
        animator.setTarget(target);
        animator.setDuration(3000);
        return animator;
    }

//这里涉及到另外一个方法:getPointF(),这个是我用来获取途径的两个点
// 这里的取值可以随意调整,调整到你希望的样子就好

    /**
     * 获取中间的两个 点
     *
     * @param scale
     */
    private PointF getPointF(int scale) {

        PointF pointF = new PointF();
        pointF.x = random.nextInt((mWidth - 100));//减去100 是为了控制 x轴活动范围,看效果 随意~~
        //再Y轴上 为了确保第二个点 在第一个点之上,我把Y分成了上下两半 这样动画效果好一些  也可以用其他方法
        pointF.y = random.nextInt((mHeight - 100)) / scale;
        return pointF;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    private class BezierListenr implements ValueAnimator.AnimatorUpdateListener {

        private View target;

        public BezierListenr(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            // 这里偷个懒,顺便做一个alpha动画,这样alpha渐变也完成啦
            target.setAlpha(1 - animation.getAnimatedFraction());
        }
    }

    private class MyAnimatorListener extends AnimatorListenerAdapter {
        private View target;

        public MyAnimatorListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(android.animation.Animator animation) {
            super.onAnimationEnd(animation);
            removeView(target);
        }
    }
}
