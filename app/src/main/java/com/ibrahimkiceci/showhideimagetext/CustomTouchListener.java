package com.ibrahimkiceci.showhideimagetext;

import android.content.Context;
import android.gesture.Gesture;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;

public class CustomTouchListener implements View.OnTouchListener {
    GestureDetectorCompat gestureDetectorCompat; // Bir cok cesit gestures'a ulasmak icin(asagida olusturduklarimiz LongPress, DoubleTap vs..) once bunu daha sonra CustomeGestureListenerimizi olusturduk.

    Context context;

    public CustomTouchListener(Context context) {
        this.context = context;
        // need to create gestureDetector object using its constructor.
        //for the constructor, you need context and custom gesture listener.

        gestureDetectorCompat = new GestureDetectorCompat(context, new CustomGestureListener());


    }

    // CustomGestureListener'a, gesture detector obje olusturabilmemiz icin ihtiyacimiz var.
    public class CustomGestureListener extends GestureDetector.SimpleOnGestureListener{
                            //GestureDetector dan yararlanmak icin SimpleOnGestureListeneri cagirdik.
                            // Bu classin soyle bir avantaji var. Biz tum eventler icin override yapmak zorunda kalmiyacagiz.

        // Burada sag tik yapip Generate sonra override methods diyorsun tum eventler orada


        @Override
        public void onLongPress(@NonNull MotionEvent e) {
            super.onLongPress(e);
            //customize Long Press
            onLongClick();

        }

        @Override
        public boolean onFling(@NonNull MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {

            final int SWIPE_DIST_THRESHOLD = 10;
            final int SWIPE_VEL_THRESHOLD = 50;
   //Yukaridaki degerleri deneyerek buluyoruz, gercekten de istedigin degerleri bulmak icin denemen gerek

            float distX = e2.getX()-e1.getX(); // x offset(x2-x1)
            float distY = e2.getY()-e1.getY(); // y offset(y2-y1)

            if (Math.abs(distX) > Math.abs(distY) && Math.abs(distX) > SWIPE_DIST_THRESHOLD && Math.abs(velocityX)> SWIPE_VEL_THRESHOLD){
                //Simdi hatirlayalim eger x offset > y offset ise bu bir horizontal swipe
                // Eger y_offset > x_offset ise bu bir vertical swipe.
                //Bu bilgiler dahilinde ilk if state'imizde horizontal swipe ise dedik !!
                if (distX > 0){

                    //Burayi hatirlayalim simdi ilk if imizde acikca dedik ki bur bir horizontal ise.
                    //Simdi buarada ise eger x_offset > 0 ise swipe right
                    // x_offset < 0 ise swipoe left;

                    onSwipeRight();

                }else{
                    onSwipeLeft();
                }

            }else if(Math.abs(distY) > Math.abs(distX) && Math.abs(distY) > SWIPE_DIST_THRESHOLD && Math.abs(velocityY) > SWIPE_VEL_THRESHOLD){

                // y_offset > x_offset ise bu bir vertical swipe'tir demek

                if (distY > 0){
                    // y_offset > 0 --> downside
                    // y_offset< 0 --> upside
                    onSwipeDown();
                }else{
                    onSwipeUp();
                }

            }

            return super.onFling(e1, e2, velocityX, velocityY);

        }

        @Override
        public boolean onDown(@NonNull MotionEvent e) {

            // OnDown herzaman secilmek zorunda yoksa default olarak false gelir. Eger false gelirse diger gesture'lar calismaz, tespit edilemezler.
            // Default olarak false geldigini unutma.
           // return super.onDown(e);
            return true; // need to return true, so that other gestures may be detected.

        }

        @Override
        public boolean onDoubleTap(@NonNull MotionEvent e) {

            onDoubleClick();
            return super.onDoubleTap(e);

        }

        @Override
        public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
            onSingleClick();
            return super.onSingleTapConfirmed(e);
        }
    }

    public void onSwipeUp() {
    }

    public void onSwipeDown() {
    }

    public void onSwipeLeft() {
    }

    public void onSwipeRight() {
    }

    public void onSingleClick() {

    }

    public void onDoubleClick() {

    }

    public void onLongClick() {
        Log.d("GestureDemo", "Detected long click in custom touch");

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //on Logic can be coded
        //return false; // Simdi burasi false donmemeli
                     // false donerse gesture eventleri olan click double click, saga sola kaydirma gibi bir cok eventi cagiramayiz.
                    // Bu nedenle bunun yerine gesture dedector object onTouchEventi cagirmamiz gerek!!

        return gestureDetectorCompat.onTouchEvent(event);

        // Yukaridaki motion event ise mouses up, mouses down, mouse held down



    }
}
