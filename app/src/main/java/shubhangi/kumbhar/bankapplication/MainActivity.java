package shubhangi.kumbhar.bankapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SharedPreferences sp = getSharedPreferences("BANK_SP",MODE_PRIVATE);
        YoYo.with(Techniques.BounceIn)
                .duration(700)
                .withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        YoYo.with(Techniques.SlideInRight)
                                .duration(700)
                                .playOn(findViewById(R.id.tv_punch_line));
                        YoYo.with(Techniques.Wobble)
                                .duration(700)
                                .repeat(5)
                                .playOn(findViewById(R.id.tv_title));
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                })
                .playOn(findViewById(R.id.tv_title));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                DemoLoader loader = new DemoLoader(MainActivity.this);

                if (!sp.getBoolean("isDemoLoaded", false)){
                    loader.loadDemo();
                    sp.edit().putBoolean("isDemoLoaded",true).commit();
            }

                Intent intent ;
                    intent = new Intent(MainActivity.this, DashboardActivity.class);
                finish();
                startActivity(intent);


            }
        },3000);
    }
}