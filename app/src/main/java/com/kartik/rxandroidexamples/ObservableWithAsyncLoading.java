package com.kartik.rxandroidexamples;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ObservableWithAsyncLoading extends AppCompatActivity implements View.OnClickListener {

	private EditText searchEditText;
	private Button searchButton;
	private Button nextButton;

	private TextView searchResult;
	private Observable mObservable;
	private String resultText;
	private Subscription subscription;
/* -------------------------------------- Lifecycle Methods -------------------------------------- */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_observable_with_async_loading);
		setTitle("Async Loading");

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

				mObservable = Observable.fromCallable(new Callable<List<String>>() {
					@Override
					public List<String> call() {
						return getColors(searchEditText.getText().toString());
					}
				});

				subscription = mObservable
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(
								new Observer<List<String>>() {
									@Override
									public void onCompleted() {
										resultText += "Completed";
										searchResult.setText(resultText);
										progressDialog.dismiss();
									}

									@Override
									public void onError(Throwable e) {

									}

									@Override
									public void onNext(List<String> strings) {
										resultText = "";
										resultText += "Subscribed to the Observable\n";
										resultText += strings.toString() + "\n";
									}
								});

				break;
			case R.id.nextButton:
				Intent intent = new Intent(this, ObservableWithAsyncLoading.class);
				startActivity(intent);
		}
	}

	private List<String> getColors (final String s) {
		List<String> colors = new ArrayList<>();
		List<String> resultColors = new ArrayList<>();

		for (int i = 0; i <100000; i++){
			colors.add("blue");
			colors.add("green");
			colors.add("red");
		}
		for (String color : colors){
			if (color.contains(s)){
				resultColors.add(color);
			}
		}
		return resultColors;
	}



	@Override
	protected void onDestroy () {
		super.onDestroy();
		if (subscription!= null && subscription.isUnsubscribed()){
			subscription.unsubscribe();
		}
	}
}
