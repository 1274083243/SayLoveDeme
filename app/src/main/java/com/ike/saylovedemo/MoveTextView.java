package com.ike.saylovedemo;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewPropertyAnimator;

/**
作者：ike
时间：2017/2/4 10:49
功能描述：移动的Textview
**/

public class MoveTextView extends TextView {
    private String Tag="MoveTextView";
    private int screen_width;
    private int width;
    private float curr_x;
    private float curr_y;
    private Handler handler=new Handler();
    private BackTask task;
    public MoveTextView(Context context) {
        this(context,null);
    }

    public MoveTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MoveTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        screen_width=context.getResources().getDisplayMetrics().widthPixels;
        task=new BackTask();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width=getMeasuredWidth();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                task.removeAll();
                setText("别点我在想想吧,反正点我也没有用");
                move();
                break;
            case MotionEvent.ACTION_UP:
                task.startBack();

                break;
        }
        return true;
    }

    private void moveToOriginnal() {
        ViewPropertyAnimator.animate(this).translationXBy(-getTranslationX()).translationYBy(-getTranslationY()).setDuration(500).setStartDelay(200).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setText("不同意");
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private boolean isReachTop;
    private boolean isReachLeft;
    private boolean isReachRight;
    private boolean isReachBottom;
    private int defaultDistance=200;
    private boolean isFirst=true;
    private int X_move_distence=defaultDistance;
    private int Y_move_distence=defaultDistance;
    private void move(){
        if (isFirst){
            X_move_distence=-defaultDistance;
            Y_move_distence=-defaultDistance;
            isFirst=false;
        }
        ViewPropertyAnimator.animate(this).translationXBy(X_move_distence).translationYBy(Y_move_distence).setDuration(100).
                setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                curr_x=getX();
                X_move_distence= (int) Math.min(curr_x,defaultDistance);
                if (X_move_distence!=defaultDistance){
                    isReachLeft=true;
                    isReachRight=false;
                }
                if (isReachLeft){
                    X_move_distence=defaultDistance;
                    if ((screen_width-(width+curr_x))<defaultDistance){
                        isReachLeft=false;
                        isReachRight=true;
                    }
                } else {
                    X_move_distence=-defaultDistance ;
                }
                if (isReachRight){
                    X_move_distence=-defaultDistance;
                }
                curr_y=getY();
                Y_move_distence= (int) Math.min(curr_y,defaultDistance);
                if (Y_move_distence!=defaultDistance){
                    isReachTop=true;
                    isReachBottom=false;
                }
                if (isReachTop){
                    isReachBottom=false;
                    isReachTop=true;
                    Y_move_distence=defaultDistance;
                    if ((getTop()-getY())<defaultDistance){
                        isReachBottom=true;
                        isReachTop=false;
                    }
                }else {
                    Y_move_distence=-defaultDistance;
                }
                if (isReachBottom){
                    Y_move_distence=-defaultDistance;
                }


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    class BackTask implements Runnable{

        @Override
        public void run() {
            setText("我要回去了，下次别点我，烦");
            moveToOriginnal();
        }
        public void removeAll(){
            handler.removeCallbacks(this);
        }
        public void startBack(){
            removeAll();
            handler.postDelayed(this,3000);
        }

    }
}
