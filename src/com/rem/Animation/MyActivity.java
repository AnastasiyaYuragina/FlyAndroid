package com.rem.Animation;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.*;

import java.util.ArrayList;

public class MyActivity extends Activity {

    static  String AnimationTag = "AnimationTag";

    ImageView imageView;//property definition must be always on top, before all methods
    View parent;
    Button buttonStart;
    Button buttonStop;
    TranslateAnimation animation;
    int endX;
    int endY;
    int borderX;
    int borderY;
    boolean finish;
    final String right = "right";
    final String left = "left";
    final String top = "top";
    final String bottom = "bottom";
    String borderString;
    ArrayList<String> borderArray = new ArrayList<String>();
    ArrayMap<String, Point> bordersMapArray = new ArrayMap<String, Point>();

    Stack stack = new Stack(2);

    String lastBordeer = "";




    
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        buttonStart = (Button)findViewById(R.id.buttonStart);
        buttonStop = (Button)findViewById(R.id.buttonStop);
        imageView = (ImageView)findViewById(R.id.imageView);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endAnimation();
            }
        });

    }






    
    protected void initializationOfBorders() {

        if (borderArray.isEmpty()) {
            parent = (View) imageView.getParent();
            borderX = parent.getWidth() - imageView.getWidth();
            borderY = parent.getHeight() - imageView.getHeight();

            borderArray.add(right);
            borderArray.add(left);
            borderArray.add(top);
            borderArray.add(bottom);

            bordersMapArray.put(left, new Point(0, borderY));
            bordersMapArray.put(right, new Point(borderX, borderY));
            bordersMapArray.put(top, new Point(borderX, 0));
            bordersMapArray.put(bottom, new Point(borderX, borderY));
        }
    }

    public void startAnimation() {
        initializationOfBorders();

        finish = false;

        borderString = (String) randomObjectFormArrayWithEsclude(borderArray, (ArrayList<String>) stack.allElements());

        stack.addElement(borderString);
        Log.v(AnimationTag, stack.allElements().toString());

        Point begin = new Point(imageView.getLeft(), imageView.getTop());
        Point end = deltForPointInPosition(begin, randomPointOnBorder(borderString, bordersMapArray));

        endX = end.x;
        endY = end.y;

        animation = new TranslateAnimation(0, endX, 0, endY);
        animation.setDuration(1000);
        animation.setFillEnabled(true);
        animation.setAnimationListener(new MyAnimationListener());

        Log.v(AnimationTag, "Start with options: " + Integer.toString(imageView.getLeft()) + "->" + Integer.toString(endX) + " " + Integer.toString(imageView.getTop()) + "->" + Integer.toString(endY));
        imageView.startAnimation(animation);
    }

    public void endAnimation() {
        finish = true;
    }

    public Point randomPointOnBorder (String border, ArrayMap<String, Point> arrayMap) {
        Point pointInArrayToString = arrayMap.get(border);
        int xi = pointInArrayToString.x;
        int yi = pointInArrayToString.y;
        double random = Math.random();

        if (border == "left" || border == "right") {
            yi = (int) (random * yi);
            pointInArrayToString = new Point(xi, yi);
        } else {
            xi = (int) (random * xi);
            pointInArrayToString = new Point(xi, yi);
        }

        return pointInArrayToString;
    }

    public Point deltForPointInPosition (Point beginPoint, Point endPoint) {
        int deltX = endPoint.x - beginPoint.x;
        int deltY = endPoint.y - beginPoint.y;

        return new Point(deltX, deltY);
    }


    public Object randomObjectFormArrayWithEsclude (ArrayList<String> array, ArrayList<String> exclude) {
        double size = array.size();
        String border;
        do {
            double random = Math.random() * 1000;
            int i = (int) (random % size);
            border = array.get(i);
        } while (exclude.contains(border));

        return border;
    }

    private class MyAnimationListener implements Animation.AnimationListener {

        @Override
        public void onAnimationEnd(Animation animation) {
            imageView.layout(
                    endX + imageView.getLeft(),
                    endY + imageView.getTop(),
                    endX + imageView.getWidth() + imageView.getLeft(),
                    endY + imageView.getHeight()  + imageView.getTop());

            Log.v(AnimationTag, "End in point " + Integer.toString(imageView.getLeft()) + " / " + Integer.toString(imageView.getTop()));

            if (finish == false) {
                startAnimation();
            } else
            {
                animation.cancel();
                Log.v(AnimationTag, "animation is stop");
            }

        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

    }

}
