package com.ike.saylovedemo.widget;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

//我们自定义一个BezierEvaluator 实现 TypeEvaluator
//由于我们view的移动需要控制x y 所以就传入PointF 作为参数,是不是感觉完全契合??
public class BezierEvaluator implements TypeEvaluator<PointF> {
    private PointF pointF1;//途径的两个点 
    private PointF pointF2;
    public BezierEvaluator(PointF pointF1,PointF pointF2){
        this.pointF1 = pointF1;
        this.pointF2 = pointF2;
    }
    @Override
    public PointF evaluate(float time, PointF startValue,
                           PointF endValue) {

        float timeLeft = 1.0f - time;
        PointF point = new PointF();//结果

        PointF point0 = (PointF)startValue;//起点

        PointF point3 = (PointF)endValue;//终点
        //代入公式 
        point.x = timeLeft * timeLeft * timeLeft * (point0.x)
                + 3 * timeLeft * timeLeft * time * (pointF1.x)
                + 3 * timeLeft * time * time * (pointF2.x)
                + time * time * time * (point3.x);

        point.y = timeLeft * timeLeft * timeLeft * (point0.y)
                + 3 * timeLeft * timeLeft * time * (pointF1.y)
                + 3 * timeLeft * time * time * (pointF2.y)
                + time * time * time * (point3.y);
        return point;
    }
}