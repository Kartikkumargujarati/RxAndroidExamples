package com.kartik.rxandroidexamples;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText searchEditText;
	private Button searchButton;
	private TextView searchResult;
	private Observable mObservable;
	private String resultText;
	private Observer<List<String>> mObserver;
/* -------------------------------------- Lifecycle Methods -------------------------------------- */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchEditText = (EditText) findViewById(R.id.searchEditText);
		searchButton = (Button) findViewById(R.id.searchButton);
		searchResult = (TextView) findViewById(R.id.searchResult);
		searchButton.setOnClickListener(this);
	}

/* ------------------------------------ End Lifecycle Methods ------------------------------------ */



	@Override
	public void onClick (final View view) {
		switch (view.getId()){
			case R.id.searchButton:

				mObservable = Observable.just(getColors(searchEditText.getText().toString()));

				mObservable.subscribe(new Observer() {
					@Override
					public void onSubscribe (@NonNull final Disposable d) {
						resultText = "";
						resultText += "Subscribed to the Observable\n";
					}



					@Override
					public void onNext (@NonNull final Object o) {

						resultText += o.toString() + "\n";
					}



					@Override
					public void onError (@NonNull final Throwable e) {
					}



					@Override
					public void onComplete () {
						resultText += "Completed";
					}
				});

				searchResult.setText(resultText);

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


}
