package com.example.tet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		SensorEventListener {
	private SensorManager smanager;
	private Sensor accelerometer;
	private MediaRecorder mRecorder = null;
	TextView mEdit;
	TextView mScore;
	Button b;
	Button b2;
	Button b3;
	Button b4;
	Button b5;
	int speed = 400;
	ImageButton gameOver;
	Board board;
	boolean pause = false;
	boolean initialized = false;
	boolean a = false;
	int idb = 1;
	private long lastUpdate = 0;
	private float last_x, last_y, last_z;
	private float init_x, init_y, init_z;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mEdit = (TextView) findViewById(R.id.textView1);
		mEdit.setText(" ");
		mScore = (TextView) findViewById(R.id.textView2);

		smanager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
		accelerometer = smanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		smanager.registerListener(this, accelerometer,
				SensorManager.SENSOR_DELAY_GAME);
		initial();

		// this.checkVoiceRecognition();
		b2 = (Button) findViewById(R.id.button2);
		b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				board.kekiri();
			}

		});
		b3 = (Button) findViewById(R.id.button3);
		b3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				board.kekanan();
			}
		});

		b5 = (Button) findViewById(R.id.button5);

		b5.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				board.puter();
			}
		});
		gameOver = (ImageButton) findViewById(R.id.imageButton1);
		gameOver.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				initial();
				gameOver.setVisibility(View.GONE);

			}
		});

		b4 = (Button) findViewById(R.id.button4);
		b4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pause = false;
				final Handler h = new Handler();
				Runnable rb = new Runnable() {
					@Override
					public void run() {
						if (!pause) {
							board.turun();
							h.postDelayed(this, speed);
							mScore.setText("" + board.score);
							if (board.gameOver) {
								gameOver.setVisibility(View.VISIBLE);
							} else if (board.newBalok) {
								Balok balok1 = new Balok(idb % 7);
								idb++;
								board.getKoordinat(balok1);
								board.board[board.X1[0]][board.X1[1]].setBackgroundResource(R.drawable.tiles);
								board.board[board.X2[0]][board.X2[1]].setBackgroundResource(R.drawable.tiles);
								board.board[board.X3[0]][board.X3[1]].setBackgroundResource(R.drawable.tiles);
								board.board[board.X4[0]][board.X4[1]].setBackgroundResource(R.drawable.tiles);
							}
						}
					}
				};
				h.postDelayed(rb, speed);
			}
		});


		b = (Button) findViewById(R.id.button1);

		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// a = true;
				pause = true;
			}
		});

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		Sensor acc = event.sensor;
		if (event.values[0] > 8) {
			 mEdit.setText("" + event.values[0] + " , " + event.values[1]
			 + " , " + event.values[2]);
		}
		Sensor mySensor = event.sensor;
		//
		 mEdit.setText("" + event.values[0] + " , " + event.values[1]
				 + " , " + event.values[2]);
		if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			if (initialized) {
				float x = event.values[0];
				float y = event.values[1];
				float z = event.values[2];

				long curTime = System.currentTimeMillis();

				if ((curTime - lastUpdate) > 100) {
					long diffTime = (curTime - lastUpdate);
					lastUpdate = curTime;

					// float xSpeed = (x - init_x) / diffTime * 10000;
					float xSpeed = (x - init_x);

					if (xSpeed > 5) {
						board.kekiri();
					}
					if (xSpeed < -5) {
						board.kekanan();
					}

					// float zSpeed = (y - last_y) / diffTime * 10000;
					float zSpeed = (z - init_z);
					if (zSpeed > 7) {
						board.puter();
					}
					// mEdit.setText("" + init_x + " , " + init_y + " , " +
					// init_z
					// + " -- " + xSpeed + " -- " + zSpeed);
					last_x = x;
					last_y = y;
					last_z = z;
				}
			} else {
				// mEdit.setText("lala");
				init_x = event.values[0];
				init_y = event.values[1];
				init_z = event.values[2];
				initialized = true;

			}
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void checkVoiceRecognition() {
		// Check if voice recognition is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() > 0) {
			// mEdit.setText("bisa");
			// board.kekiri();
		}
	}

	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)
	// if (resultCode == RESULT_OK) {
	// ArrayList<String> textMatchList = data
	// .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	//
	// if (!textMatchList.isEmpty()) {
	// mEdit.setText("masuk sini");
	// }
	// }
	// super.onActivityResult(requestCode, resultCode, data);
	// }
	public void initial() {
		board = new Board();
		board.board[0][0] = (ImageView) findViewById(R.id.i00);
		board.board[0][1] = (ImageView) findViewById(R.id.i01);
		board.board[0][2] = (ImageView) findViewById(R.id.i02);
		board.board[0][3] = (ImageView) findViewById(R.id.i03);
		board.board[0][4] = (ImageView) findViewById(R.id.i04);
		board.board[0][5] = (ImageView) findViewById(R.id.i05);
		board.board[0][6] = (ImageView) findViewById(R.id.i06);
		board.board[0][7] = (ImageView) findViewById(R.id.i07);
		board.board[0][8] = (ImageView) findViewById(R.id.i08);
		board.board[1][0] = (ImageView) findViewById(R.id.i10);
		board.board[1][1] = (ImageView) findViewById(R.id.i11);
		board.board[1][2] = (ImageView) findViewById(R.id.i12);
		board.board[1][3] = (ImageView) findViewById(R.id.i13);
		board.board[1][4] = (ImageView) findViewById(R.id.i14);
		board.board[1][5] = (ImageView) findViewById(R.id.i15);
		board.board[1][6] = (ImageView) findViewById(R.id.i16);
		board.board[1][7] = (ImageView) findViewById(R.id.i17);
		board.board[1][8] = (ImageView) findViewById(R.id.i18);
		board.board[2][0] = (ImageView) findViewById(R.id.i20);
		board.board[2][1] = (ImageView) findViewById(R.id.i21);
		board.board[2][2] = (ImageView) findViewById(R.id.i22);
		board.board[2][3] = (ImageView) findViewById(R.id.i23);
		board.board[2][4] = (ImageView) findViewById(R.id.i24);
		board.board[2][5] = (ImageView) findViewById(R.id.i25);
		board.board[2][6] = (ImageView) findViewById(R.id.i26);
		board.board[2][7] = (ImageView) findViewById(R.id.i27);
		board.board[2][8] = (ImageView) findViewById(R.id.i28);
		board.board[3][0] = (ImageView) findViewById(R.id.i30);
		board.board[3][1] = (ImageView) findViewById(R.id.i31);
		board.board[3][2] = (ImageView) findViewById(R.id.i32);
		board.board[3][3] = (ImageView) findViewById(R.id.i33);
		board.board[3][4] = (ImageView) findViewById(R.id.i34);
		board.board[3][5] = (ImageView) findViewById(R.id.i35);
		board.board[3][6] = (ImageView) findViewById(R.id.i36);
		board.board[3][7] = (ImageView) findViewById(R.id.i37);
		board.board[3][8] = (ImageView) findViewById(R.id.i38);
		board.board[4][0] = (ImageView) findViewById(R.id.i40);
		board.board[4][1] = (ImageView) findViewById(R.id.i41);
		board.board[4][2] = (ImageView) findViewById(R.id.i42);
		board.board[4][3] = (ImageView) findViewById(R.id.i43);
		board.board[4][4] = (ImageView) findViewById(R.id.i44);
		board.board[4][5] = (ImageView) findViewById(R.id.i45);
		board.board[4][6] = (ImageView) findViewById(R.id.i46);
		board.board[4][7] = (ImageView) findViewById(R.id.i47);
		board.board[4][8] = (ImageView) findViewById(R.id.i48);
		board.board[5][0] = (ImageView) findViewById(R.id.i50);
		board.board[5][1] = (ImageView) findViewById(R.id.i51);
		board.board[5][2] = (ImageView) findViewById(R.id.i52);
		board.board[5][3] = (ImageView) findViewById(R.id.i53);
		board.board[5][4] = (ImageView) findViewById(R.id.i54);
		board.board[5][5] = (ImageView) findViewById(R.id.i55);
		board.board[5][6] = (ImageView) findViewById(R.id.i56);
		board.board[5][7] = (ImageView) findViewById(R.id.i57);
		board.board[5][8] = (ImageView) findViewById(R.id.i58);
		board.board[6][0] = (ImageView) findViewById(R.id.i60);
		board.board[6][1] = (ImageView) findViewById(R.id.i61);
		board.board[6][2] = (ImageView) findViewById(R.id.i62);
		board.board[6][3] = (ImageView) findViewById(R.id.i63);
		board.board[6][4] = (ImageView) findViewById(R.id.i64);
		board.board[6][5] = (ImageView) findViewById(R.id.i65);
		board.board[6][6] = (ImageView) findViewById(R.id.i66);
		board.board[6][7] = (ImageView) findViewById(R.id.i67);
		board.board[6][8] = (ImageView) findViewById(R.id.i68);
		board.board[7][0] = (ImageView) findViewById(R.id.i70);
		board.board[7][1] = (ImageView) findViewById(R.id.i71);
		board.board[7][2] = (ImageView) findViewById(R.id.i72);
		board.board[7][3] = (ImageView) findViewById(R.id.i73);
		board.board[7][4] = (ImageView) findViewById(R.id.i74);
		board.board[7][5] = (ImageView) findViewById(R.id.i75);
		board.board[7][6] = (ImageView) findViewById(R.id.i76);
		board.board[7][7] = (ImageView) findViewById(R.id.i77);
		board.board[7][8] = (ImageView) findViewById(R.id.i78);
		board.board[8][0] = (ImageView) findViewById(R.id.i80);
		board.board[8][1] = (ImageView) findViewById(R.id.i81);
		board.board[8][2] = (ImageView) findViewById(R.id.i82);
		board.board[8][3] = (ImageView) findViewById(R.id.i83);
		board.board[8][4] = (ImageView) findViewById(R.id.i84);
		board.board[8][5] = (ImageView) findViewById(R.id.i85);
		board.board[8][6] = (ImageView) findViewById(R.id.i86);
		board.board[8][7] = (ImageView) findViewById(R.id.i87);
		board.board[8][8] = (ImageView) findViewById(R.id.i88);
		board.board[9][0] = (ImageView) findViewById(R.id.i90);
		board.board[9][1] = (ImageView) findViewById(R.id.i91);
		board.board[9][2] = (ImageView) findViewById(R.id.i92);
		board.board[9][3] = (ImageView) findViewById(R.id.i93);
		board.board[9][4] = (ImageView) findViewById(R.id.i94);
		board.board[9][5] = (ImageView) findViewById(R.id.i95);
		board.board[9][6] = (ImageView) findViewById(R.id.i96);
		board.board[9][7] = (ImageView) findViewById(R.id.i97);
		board.board[9][8] = (ImageView) findViewById(R.id.i98);
		board.board[10][0] = (ImageView) findViewById(R.id.i100);
		board.board[10][1] = (ImageView) findViewById(R.id.i101);
		board.board[10][2] = (ImageView) findViewById(R.id.i102);
		board.board[10][3] = (ImageView) findViewById(R.id.i103);
		board.board[10][4] = (ImageView) findViewById(R.id.i104);
		board.board[10][5] = (ImageView) findViewById(R.id.i105);
		board.board[10][6] = (ImageView) findViewById(R.id.i106);
		board.board[10][7] = (ImageView) findViewById(R.id.i107);
		board.board[10][8] = (ImageView) findViewById(R.id.i108);
		board.board[11][0] = (ImageView) findViewById(R.id.i110);
		board.board[11][1] = (ImageView) findViewById(R.id.i111);
		board.board[11][2] = (ImageView) findViewById(R.id.i112);
		board.board[11][3] = (ImageView) findViewById(R.id.i113);
		board.board[11][4] = (ImageView) findViewById(R.id.i114);
		board.board[11][5] = (ImageView) findViewById(R.id.i115);
		board.board[11][6] = (ImageView) findViewById(R.id.i116);
		board.board[11][7] = (ImageView) findViewById(R.id.i117);
		board.board[11][8] = (ImageView) findViewById(R.id.i118);
		board.board[12][0] = (ImageView) findViewById(R.id.i120);
		board.board[12][1] = (ImageView) findViewById(R.id.i121);
		board.board[12][2] = (ImageView) findViewById(R.id.i122);
		board.board[12][3] = (ImageView) findViewById(R.id.i123);
		board.board[12][4] = (ImageView) findViewById(R.id.i124);
		board.board[12][5] = (ImageView) findViewById(R.id.i125);
		board.board[12][6] = (ImageView) findViewById(R.id.i126);
		board.board[12][7] = (ImageView) findViewById(R.id.i127);
		board.board[12][8] = (ImageView) findViewById(R.id.i128);
		board.board[13][0] = (ImageView) findViewById(R.id.i130);
		board.board[13][1] = (ImageView) findViewById(R.id.i131);
		board.board[13][2] = (ImageView) findViewById(R.id.i132);
		board.board[13][3] = (ImageView) findViewById(R.id.i133);
		board.board[13][4] = (ImageView) findViewById(R.id.i134);
		board.board[13][5] = (ImageView) findViewById(R.id.i135);
		board.board[13][6] = (ImageView) findViewById(R.id.i136);
		board.board[13][7] = (ImageView) findViewById(R.id.i137);
		board.board[13][8] = (ImageView) findViewById(R.id.i138);
		board.board[14][0] = (ImageView) findViewById(R.id.i140);
		board.board[14][1] = (ImageView) findViewById(R.id.i141);
		board.board[14][2] = (ImageView) findViewById(R.id.i142);
		board.board[14][3] = (ImageView) findViewById(R.id.i143);
		board.board[14][4] = (ImageView) findViewById(R.id.i144);
		board.board[14][5] = (ImageView) findViewById(R.id.i145);
		board.board[14][6] = (ImageView) findViewById(R.id.i146);
		board.board[14][7] = (ImageView) findViewById(R.id.i147);
		board.board[14][8] = (ImageView) findViewById(R.id.i148);
		board.board[15][0] = (ImageView) findViewById(R.id.i150);
		board.board[15][1] = (ImageView) findViewById(R.id.i151);
		board.board[15][2] = (ImageView) findViewById(R.id.i152);
		board.board[15][3] = (ImageView) findViewById(R.id.i153);
		board.board[15][4] = (ImageView) findViewById(R.id.i154);
		board.board[15][5] = (ImageView) findViewById(R.id.i155);
		board.board[15][6] = (ImageView) findViewById(R.id.i156);
		board.board[15][7] = (ImageView) findViewById(R.id.i157);
		board.board[15][8] = (ImageView) findViewById(R.id.i158);
		board.board[16][0] = (ImageView) findViewById(R.id.i160);
		board.board[16][1] = (ImageView) findViewById(R.id.i161);
		board.board[16][2] = (ImageView) findViewById(R.id.i162);
		board.board[16][3] = (ImageView) findViewById(R.id.i163);
		board.board[16][4] = (ImageView) findViewById(R.id.i164);
		board.board[16][5] = (ImageView) findViewById(R.id.i165);
		board.board[16][6] = (ImageView) findViewById(R.id.i166);
		board.board[16][7] = (ImageView) findViewById(R.id.i167);
		board.board[16][8] = (ImageView) findViewById(R.id.i168);
		board.board[17][0] = (ImageView) findViewById(R.id.i170);
		board.board[17][1] = (ImageView) findViewById(R.id.i171);
		board.board[17][2] = (ImageView) findViewById(R.id.i172);
		board.board[17][3] = (ImageView) findViewById(R.id.i173);
		board.board[17][4] = (ImageView) findViewById(R.id.i174);
		board.board[17][5] = (ImageView) findViewById(R.id.i175);
		board.board[17][6] = (ImageView) findViewById(R.id.i176);
		board.board[17][7] = (ImageView) findViewById(R.id.i177);
		board.board[17][8] = (ImageView) findViewById(R.id.i178);
		board.awal();
		for (int i = 0; i < 18; i++) {
			for (int j = 0; j < 9; j++) {
				board.board[i][j].setBackgroundResource(R.drawable.imgview);
			}
		}
		idb = 1;
		Balok balok = new Balok(1);
		board.getKoordinat(balok);
		board.board[board.X1[0]][board.X1[1]].setBackgroundResource(R.drawable.tiles);
		board.board[board.X2[0]][board.X2[1]].setBackgroundResource(R.drawable.tiles);
		board.board[board.X3[0]][board.X3[1]].setBackgroundResource(R.drawable.tiles);
		board.board[board.X4[0]][board.X4[1]].setBackgroundResource(R.drawable.tiles);

		speed = 400;
		final Handler h = new Handler();
		Runnable rb = new Runnable() {
			@Override
			public void run() {
				if (!pause && !board.gameOver) {
					board.turun();
					h.postDelayed(this, speed);
					mScore.setText("" + board.score);
					if (board.gameOver) {
						gameOver.setVisibility(View.VISIBLE);
					} else if (board.newBalok) {
						speed = 400;
						Balok balok1 = new Balok(idb % 8);
						idb++;
						board.getKoordinat(balok1);
						board.board[board.X1[0]][board.X1[1]].setBackgroundResource(R.drawable.tiles);
						board.board[board.X2[0]][board.X2[1]].setBackgroundResource(R.drawable.tiles);
						board.board[board.X3[0]][board.X3[1]].setBackgroundResource(R.drawable.tiles);
						board.board[board.X4[0]][board.X4[1]].setBackgroundResource(R.drawable.tiles);
					}

					if (mRecorder == null) {
						mRecorder = new MediaRecorder();
						mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
						mRecorder
								.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
						mRecorder
								.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
						mRecorder.setOutputFile("/dev/null");
						try {
							mRecorder.prepare();
						} catch (IllegalStateException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mRecorder.start();
					}
					int a = mRecorder.getMaxAmplitude();
					//mEdit.setText("" + a);
					if (a > 25000) {
						speed = 20;
					} else {
						// speed = 400;
					}
				}
			}
		};
		h.postDelayed(rb, speed);

	}
	
}
