package ru.zizik.cryptomobile;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private EditText input;
	private TextView output;
	private Spinner chooser;
	private Crypto c;
	private EditText key;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initializeUI();
		c = new Crypto();

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
		if (id == android.R.id.home)
			input.setText("");
		switchUI();
		return super.onOptionsItemSelected(item);
	}

	public void initializeUI() {
		input = (EditText) findViewById(R.id.editText1);
		input.setHint("Введите шифруемое сообщение");
		output = (TextView) findViewById(R.id.textView1);
		chooser = (Spinner) findViewById(R.id.spinner1);
		key = (EditText) findViewById(R.id.editText2);
		chooser.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Log.e("Crypt", arg2 + "");
				switch (arg2) {
				case 0:
					key.setVisibility(View.VISIBLE);
					key.setHint("Введите числовой ключ");
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	@SuppressLint("NewApi")
	public void switchUI() {
		input.setVisibility(input.getVisibility() == View.VISIBLE ? View.INVISIBLE
				: View.VISIBLE);
		chooser.setVisibility(chooser.getVisibility() == View.VISIBLE ? View.INVISIBLE
				: View.VISIBLE);
		output.setVisibility(output.getVisibility() == View.VISIBLE ? View.INVISIBLE
				: View.VISIBLE);
		getActionBar().setHomeButtonEnabled(
				input.getVisibility() != View.VISIBLE);
		getActionBar().setDisplayHomeAsUpEnabled(
				input.getVisibility() != View.VISIBLE);

		// input.setVisibility(input.getVisibility()==View.VISIBLE?View.INVISIBLE:View.VISIBLE);
	}
	
		
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			if (input.getText().toString().length() > 0) {
				c.setOriginalMessage(input.getText().toString().toLowerCase());
				c.setKey(Integer.parseInt(key.getText().toString()));
				output.setText(c.crypt(chooser.getSelectedItemPosition()));
			} else {
				output.setText("Введите сообщение для шифрования!");
			}
			switchUI();
			break;
		case R.id.button2:
			// Дешифровка
			break;
		case R.id.button3:
			// Шеринг
			break;
		case R.id.button4:
			// Копирование
			break;

		}
	}

}
