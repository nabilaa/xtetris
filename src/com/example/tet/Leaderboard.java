package com.example.tet;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Leaderboard extends ActionBarActivity {
	TextView scor;
	private MediaPlayer mediaSound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leaderboard);

		mediaSound = MediaPlayer.create(this, R.raw.leaderboardmusic);
		mediaSound.start();

		Intent in = getIntent();

		String scores = in.getStringExtra("scores");
		String nama = in.getStringExtra("nama");
		if (nama != null && nama.length() > 0) {
			String[] n = nama.split("-");

			scor = (TextView) findViewById(R.id.utama);

			if (scores.length() > 0) {
				String [] s = scores.split(" ");
//				scor = (TextView) findViewById(R.id.utama);
				String jadi = "";
				for(int i=0;i<s.length&&i<10;i++){
					for(int j=0; j<n.length;j++){
						if(n[j].length()>0 && n[j].split(" ")[1].equals(s[i])){
							jadi += n[j]+"\n";
							n[j]="";
							j=n.length;
							
						}
					}
				}
				scor.setText(jadi);
				//scor.setText("" + nama + "\n" + scores);

			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.leaderboard, menu);
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
}
