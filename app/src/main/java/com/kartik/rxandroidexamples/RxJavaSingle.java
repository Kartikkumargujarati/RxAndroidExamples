package com.kartik.rxandroidexamples;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Callable;

import model.AnswerData;
import model.AnswerItem;
import network.RetrofitInstance;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
* Example using RxJava Single(simple version of observable) and Retrofit2. Using Single with 2 callbacks (onSuccess, onError).
* */
public class RxJavaSingle extends AppCompatActivity implements View.OnClickListener {

	private EditText searchEditText;
	private Button searchButton;
	private Button nextButton;
	private TextView searchResult;
	private String resultText;

	private Subscription singleSubscription;



/* -------------------------------------- Lifecycle Methods -------------------------------------- */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_java_single);
		setTitle("UI Non-Blocking(Single)");

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
				resultText = "";

				Single<List<String>> single = Single.fromCallable(new Callable<List<String>>() {
					@Override
					public List<String> call () throws Exception {
						return new RetrofitInstance().getColors(searchEditText.getText().toString());
					}
				});

				singleSubscription = single.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new SingleSubscriber<List<String>>() {
							@Override
							public void onSuccess(List<String> colors) {
								for (String s: colors){
									resultText += s + "\n";
								}
								if (resultText.equals("")){
									searchResult.setText("No colors found..!!");
								} else {
									searchResult.setText(resultText);
								}
								progressDialog.dismiss();
							}

							@Override
							public void onError(Throwable error) {
								searchResult.setText(error.toString());
								progressDialog.dismiss();
							}
						});
				break;

			case R.id.nextButton:
				Intent intent = new Intent(this, RxJavaSingle.class);
				startActivity(intent);
				break;
		}
	}

}
