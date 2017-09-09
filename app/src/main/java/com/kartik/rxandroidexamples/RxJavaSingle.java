package com.kartik.rxandroidexamples;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import model.AnswerData;
import model.AnswerItem;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxJavaSingle extends AppCompatActivity implements View.OnClickListener {

	private EditText searchEditText;
	private Button searchButton;
	private Button nextButton;
	private TextView searchResult;



/* -------------------------------------- Lifecycle Methods -------------------------------------- */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_java_single);

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
				final ProgressDialog progressDialog = new ProgressDialog(this);
				progressDialog.setTitle("Loading");
				progressDialog.show();
				break;

			case R.id.nextButton:
				Intent intent = new Intent(this, RxJavaSingle.class);
				startActivity(intent);
				break;
		}
	}

}
