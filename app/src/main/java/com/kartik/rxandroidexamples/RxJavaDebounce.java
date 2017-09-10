package com.kartik.rxandroidexamples;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;



public class RxJavaDebounce extends AppCompatActivity implements View.OnClickListener{

	private EditText searchEditText;
	private Button searchButton;
	private Button nextButton;
	private TextView searchResult;
	private String resultText;
	private ProgressDialog progressDialog;



	/* -------------------------------------- Lifecycle Methods -------------------------------------- */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_java_debounce);
		setTitle("Debounce Example");

		searchEditText = (EditText) findViewById(R.id.searchEditText);
		searchButton = (Button) findViewById(R.id.searchButton);
		nextButton = (Button) findViewById(R.id.nextButton);
		nextButton.setOnClickListener(this);
		searchResult = (TextView) findViewById(R.id.searchResult);
		searchButton.setOnClickListener(this);
	}
/* ------------------------------------ End Lifecycle Methods ------------------------------------ */



	@Override
	public void onClick (final View view) {
		switch (view.getId()){
			case R.id.searchButton:
				progressDialog = new ProgressDialog(this);
				progressDialog.setTitle("Loading");
				progressDialog.show();
				resultText = "";
				break;

			case R.id.nextButton:
				Intent intent = new Intent(this, RxJavaDebounce.class);
				startActivity(intent);
				break;
		}
	}


}
