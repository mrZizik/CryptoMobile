package ru.zizik.cryptomobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private EditText input;
	private TextView output;
	private Spinner chooser;
	private Crypto c;
	private EditText key;
	private Animation elementIn;
	private Button button;
	boolean flag = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		elementIn = AnimationUtils.loadAnimation(this, R.anim.elementsupdown);
		initializeUI();
		c = new Crypto();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	public int state = 0;
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == android.R.id.home) {
			input.setText("");
			switchUI();
		}
		if (id == R.id.action_done) {
			switch (state) {
			case 0:
				if (input.getText().toString().length()!=0) {
					chooser.setVisibility(View.VISIBLE);
					chooser.startAnimation(elementIn);
					key.setVisibility(View.VISIBLE);
					key.startAnimation(elementIn);
				}
				break;
			case 1:
				crypt();
				break;
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void crypt() {
		if (input.getText().toString().length() > 0) {
			c.setOriginalMessage(input.getText().toString().toLowerCase());
			switch (chooser.getSelectedItemPosition()) {
			case 0:
				c.setKey(Integer.parseInt(key.getText().toString()));
				output.setText(c.crypt(chooser.getSelectedItemPosition()));
				switchUI();
				break;
			case 1:
				c.setKey(key.getText().toString());
				output.setText(c.crypt(chooser.getSelectedItemPosition()));
				switchUI();
				break;
			case 2:
				output.setText(c.crypt(chooser.getSelectedItemPosition()));
				switchUI();
				break;
			case 3:
				output.setText(c.crypt(chooser.getSelectedItemPosition()));
				switchUI();
				break;
			}
		} else {
			output.setText("Введите сообщение для шифрования!");
		}
	}
	
	
	public void initializeUI() {
		input = (EditText) findViewById(R.id.editText1);
		input.setVisibility(View.VISIBLE);
		input.startAnimation(elementIn);
		button = (Button) findViewById(R.id.button1);
		chooser = (Spinner) findViewById(R.id.spinner1);
		input.setHint("Введите шифруемое сообщение");

		output = (TextView) findViewById(R.id.textView1);

		key = (EditText) findViewById(R.id.editText2);
		chooser.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (flag) {
					button.setVisibility(View.VISIBLE);
					button.startAnimation(elementIn);
					state = 1;
					switch (arg2) {
					case 0:
						key.setHint("Введите числовой ключ");
						break;
					case 1:
						key.setHint("Введите текстовый ключ");
						break;
					case 2:
						key.setVisibility(View.GONE);
						break;
					case 3:
						key.setVisibility(View.GONE);
						break;
					}
				}
				flag = true;
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

	@SuppressLint("NewApi")
	public void switchUI() {
		input.setVisibility(input.getVisibility() == View.VISIBLE ? View.GONE
				: View.VISIBLE);
		chooser.setVisibility(chooser.getVisibility() == View.VISIBLE ? View.GONE
				: View.VISIBLE);
		output.setVisibility(output.getVisibility() == View.VISIBLE ? View.GONE
				: View.VISIBLE);
		button.setVisibility(button.getVisibility() == View.VISIBLE ? View.GONE
				: View.VISIBLE);
		getActionBar().setHomeButtonEnabled(
				input.getVisibility() != View.VISIBLE);
		getActionBar().setDisplayHomeAsUpEnabled(
				input.getVisibility() != View.VISIBLE);

		// input.setVisibility(input.getVisibility()==View.VISIBLE?View.GONE:View.VISIBLE);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			crypt();
			break;

		case R.id.button3:
			break;
		case R.id.button4:
			break;

		}
	}
}
