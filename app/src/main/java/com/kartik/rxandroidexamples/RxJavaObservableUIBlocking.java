package com.kartik.rxandroidexamples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;

public class RxJavaObservableUIBlocking extends AppCompatActivity implements View.OnClickListener {

	private EditText searchEditText;
	private Button searchButton;
	private Button nextButton;

	private TextView searchResult;
	private Observable mObservable;
	private String resultText;
	private Observer<List<String>> mObserver;
/* -------------------------------------- Lifecycle Methods -------------------------------------- */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rxjava_observable_ui_blocking);
		setTitle("Blocking UI");
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

				mObservable = Observable.just(getColors(searchEditText.getText().toString()));
				mObservable.subscribe(new Observer() {
					@Override
					public void onCompleted () {
						resultText += "Completed";
					}

					@Override
					public void onError (final Throwable e) {

					}

					@Override
					public void onNext (final Object o) {
						resultText = "";
						resultText += "Subscribed to the Observable\n";
						resultText += o.toString();
					}
				});

				searchResult.setText(resultText);
				break;
			case R.id.nextButton:
				Intent intent = new Intent(this, RxJavaObservableWithNonUIBlocking.class);
				startActivity(intent);
				break;
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
