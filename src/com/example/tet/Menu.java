package com.example.tet;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ToggleButton;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

public class Menu extends ActionBarActivity {
	private MediaPlayer mediaSound;
	SharedPreferences pref;
	String game = "Teris";
	SharedPreferences.Editor editorScore;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		mediaSound = MediaPlayer.create(this, R.raw.menumusic);
		mediaSound.start();

		ImageButton playButton = (ImageButton) findViewById(R.id.imageButton1);
		ImageButton highscoreButton = (ImageButton) findViewById(R.id.imageButton2);

		playButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent act2 = new Intent(view.getContext(), MainActivity.class);
				if (mediaSound != null) {
					mediaSound.stop();
				}
				startActivity(act2);
			}
		});

		ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton1);
		toggle.setTextOn("");
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				if (isChecked) {
					mediaSound.stop();
				} else {
					if (mediaSound != null) {
						mediaSound.stop();
					}
					mediaSound = MediaPlayer.create(context, R.raw.menumusic);
					mediaSound.start();
				}
			}
		});

		// /////////////////////////// HIGH SCORE
		// ////////////////////////////////////
		pref = getSharedPreferences(game, 0);
		editorScore = pref.edit();
		// ///////////////////////////////////////////////////////////////////////////

		highscoreButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mediaSound != null) {
					mediaSound.stop();
				}

				String sca = pref.getString("high", "");
				String nama = pref.getString("nama", "");

				Intent i = new Intent(getApplicationContext(),
						Leaderboard.class);
				i.putExtra("scores", sca);
				i.putExtra("nama", nama);

				startActivity(i);
			}
		});

		ImageView flower = (ImageView) findViewById(R.id.imageView1);
		ImageButton highscore = (ImageButton) findViewById(R.id.imageButton2);

		RotateAnimation anim = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		anim.setInterpolator(new LinearInterpolator());
		anim.setRepeatCount(Animation.INFINITE);
		anim.setDuration(10000);
		flower.startAnimation(anim);
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (mediaSound != null) {
			mediaSound.stop();
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (mediaSound != null) {
			mediaSound.stop();
		}
	}
}
