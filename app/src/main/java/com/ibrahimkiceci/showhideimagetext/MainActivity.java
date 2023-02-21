package com.ibrahimkiceci.showhideimagetext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    Button button1;
    boolean bigger = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        button1 = findViewById(R.id.button1);
        // Simdi uygulamaya bir tane cerceve ekleyecegiz. Eger drawable'indaki bir resme ulasmak istiyorsan, ResourcesCompat kullanman gerekiyor.
        // Simdi bunu icin ContextCompat'da kullaniliyor. Aradaki farki;
        //1. Aralarinda ciddi bir fark yok. Ancak Context Compat Android Context'ine ulasmak icin kullaniliyor.
        //2. Context nedir diye soracak olursan , context iki seyi saglar bir bildigin uzere android aktivitelerden olusur ve
        //context senin hangi aktivite'de oldugunu saglar bir diger ozelligide database, resource veya shared preferences vs. ulasmani saglar.

        Drawable img = ResourcesCompat.getDrawable(getResources(),R.drawable.border, getTheme());

        //Asagida NullPointerExceptiondan kacinmak icin objene requireNonNull hatasindan dolayi asagidaki kodu yazman gerek.
          //Eger binding kullansaydik buna gerek yoktu.
        //Checks that the specified object reference is not null and throws a customized NullPointerException if it is.

        Objects.requireNonNull(img);

        img.setBounds(0,0,img.getIntrinsicWidth(),img.getIntrinsicHeight());
        // getIntrinsicWidth ve getIntrinsicHeight nedir ?
        // getIntrinsicWidth/Height simply means to provide you with the default width/height of that drawable.
        //This returns the exact size of the drawable which you have put in the resource folder without any modification
        //Let's say that you have provided the width and height of your ImageView as 100*100 in the XML layout and the drawable you used as the background is of size 200*200.
        //Now getIntrinsicWidth must return 200 whereas getWidth must return 100.
        //Yani bu ikisi senin icin drawable'ina uygun birer width ve height olculeri belirlemekte.

        // where image will be shown, eger setCompoundDrawables methodunu yazmazsan kenarlardaki image gorunmez.

        textView.setCompoundDrawables(img,null,img,null);
        textView.setCompoundDrawablePadding(16);

        // Simdi bu kisimda text'imize bir kere bastigimizda, iki kere bastigimizda, uzun bastigimizda saga, sola, asagi, yukari surukledigimizde neler olacagina dair siniflarimizi yazalim.
        //Buradan once showBoth, showText siniflarimizi tamamladik. Yukaridaki ismlemlerimzi de cerceve eklemek icin yaptik
        // Daha sonra CustomTouchListenirimizi yazdik ve tamamladik.

        textView.setOnTouchListener(new CustomTouchListener(MainActivity.this){

            // sag tik , generate, override method


            @Override
            public void onSingleClick() {
                super.onSingleClick();

                if(textView.getCurrentTextColor() != ResourcesCompat.getColor(getResources(),R.color.teal_200, getTheme())){
                    textView.setTextColor(ResourcesCompat.getColor(getResources(),R.color.teal_200, getTheme()));
                    //eger R'indaki yani sourceundaki renge ulasaksan ResourcesCompat kullanmalisin,
                }else{
                    textView.setTextColor(Color.rgb(100,50,140)); // Color'a bu sekilde de ulasabilrisin ancak kodu biliyor olmalisin


                }
            }

            @Override
            public void onDoubleClick() {
                //Lets increase the text size
                super.onDoubleClick();

                if (!bigger){
                    textView.setTextSize(textView.getTextSize()/getResources().getDisplayMetrics().density +10);
                    bigger = true;
                }else{

                    textView.setTextSize(textView.getTextSize()/getResources().getDisplayMetrics().density -10);
                   bigger =false;
                }


            }

            @Override
            public void onLongClick() {
                super.onLongClick();
            }

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.d("GestureDemo", "Triggered on Touch from text view");
                return super.onTouch(v, event);
            }

            @Override
            public void onSwipeUp() {
                super.onSwipeUp();

                int horizGravity = textView.getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK;
                textView.setGravity(horizGravity | Gravity.TOP);

            }

            @Override
            public void onSwipeDown() {
                super.onSwipeDown();

                int horizGravity = textView.getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK;
                textView.setGravity(horizGravity | Gravity.BOTTOM);
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                int verGravity = textView.getGravity() & Gravity.VERTICAL_GRAVITY_MASK;  // cunku toplayinca horizontal sifir olmus oldu.
                textView.setGravity(verGravity | Gravity.LEFT);
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                int verGravity = textView.getGravity() & Gravity.VERTICAL_GRAVITY_MASK;
                //textView.getGravity --- > getting a current gravity
                textView.setGravity(verGravity | Gravity.RIGHT);

            }
        });

        imageView.setOnTouchListener(new CustomTouchListener(MainActivity.this){

                //CustomTouch listeneri diger viewler icinde kullanabiliyoruz bu nedenle imageview in icin farkli bir bir sinif olusturaman gerek yok
                // Bu sinifta islemlerini gerceklestirebilirsin.


            @Override
            public void onSingleClick() {
                // i am gonna change the image
                super.onSingleClick();
                Drawable birdImage = ResourcesCompat.getDrawable(getResources(),R.drawable.bird,getTheme());
                Objects.requireNonNull(birdImage);

                //drawble'lari karsilastirmak icin getConstantState kullanman gerekiyor!!
                // to compare drawbles, you need to use getConstantState
                if (imageView.getDrawable().getConstantState() != birdImage.getConstantState()){

                    imageView.setImageResource(R.drawable.bird);


                }else{
                    imageView.setImageResource(R.drawable.fire);
                }
            }

            @Override
            public void onDoubleClick() {
                // I am gonna change the scalling
                super.onDoubleClick();

                if (imageView.getScaleType()!= ImageView.ScaleType.FIT_XY){
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                }else{
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }
        });



    }

    public void showBoth(View view){

        textView.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.VISIBLE);


    }

    public void showText(View view){

        if (button1.getText().equals("Show Text")){
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            button1.setText("Show Image");
            Log.d("ViewPractice","Text on Button " + button1.getText());


        }else{
            // Burada else yani button daki yazi show text degilse (Yani-> button1.getText().equals("Show Image"))
            Log.d("ViewPractice", "Else Block Text on Button " + button1.getText());
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            //Burada ozellikle gone ve invisible arasindaki farki bil !!
            //Ornegin burada textview gone oldugunda image view textView olan yeride kapsayarak ekrani daha kullanisli gosteriyor
            //Ancak gone degilde invisible deseydik image view'i layout da nereye koyduksak orada gorunur.
            //textView.setVisibility(View.INVISIBLE);
            button1.setText("Show Text");
        }

    }





}