package com.example.tet;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
//import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Menu extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		ImageButton play = (ImageButton) findViewById(R.id.imageButton1);
		
		play.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent act2 = new Intent(view.getContext(), MainActivity.class);
				startActivity(act2);
			}
		});

		ImageView flower = (ImageView) findViewById(R.id.imageView1);
		ImageButton highscore = (ImageButton) findViewById(R.id.imageButton2);
//		this.sendViewToBack(play);
//		this.sendViewToBack(highscore);
//		this.sendViewToBack(flower);

		RotateAnimation anim = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		// RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
		// Set the Animation properties
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(10000);
		// Start animating the image
		flower.startAnimation(anim);
		// // Later. if you want to stop the animation
		// flower.setAnimation(null);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, (android.view.Menu) menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static void sendViewToBack(final View child) {
		final ViewGroup parent = (ViewGroup) child.getParent();
		if (null != parent) {
			parent.removeView(child);
			parent.addView(child, 0);
		}
	}
}
