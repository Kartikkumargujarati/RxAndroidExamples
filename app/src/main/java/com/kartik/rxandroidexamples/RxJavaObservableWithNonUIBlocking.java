package com.kartik.rxandroidexamples;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//RxJava2
//import io.reactivex.android.schedulers.AndroidSchedulers;
import model.AnswerData;
import model.AnswerItem;
import network.RetrofitInstance;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
//RxJava2
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/*
* Example using RxJava Observable and Retrofit2.
* */
public class RxJavaObservableWithNonUIBlocking extends AppCompatActivity implements View.OnClickListener {

	private EditText searchEditText;
	private Button searchButton;
	private Button nextButton;

	private TextView searchResult;
	private String resultText;
	//RxJava2
	//private Disposable disposable;


	private static Retrofit retrofit = null;


	public static final String BASE_URL = "https://api.stackexchange.com/2.2/";

/* -------------------------------------- Lifecycle Methods -------------------------------------- */
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rxjava_observable_non_ui_blocking);
		setTitle("UI Non-Blocking");

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

				//RxJava2
				/*Observable<AnswerData> observable =  getClient(BASE_URL).create(StackExchangeEndpointInterface.class).getRecentAnswers();
				disposable = observable.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
				.subscribe(searchResponse -> {
					for (AnswerItem s : searchResponse.getFlickerImageItems()) {

						if (s.getOwner().getDisplayName().contains(searchEditText.getText().toString())){
							resultText += s.getOwner().getDisplayName() + "\n";
						}
					}
					searchResult.setText(resultText);
					progressDialog.dismiss();
				}, throwable -> searchResult.setText(throwable.toString()));*/


				Observable<AnswerData> observable = new RetrofitInstance().getRetrofit().create(RetrofitInstance.StackExchangeEndpointInterface.class).getRecentAnswers();
				observable.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<AnswerData>() {
							@Override
							public void onCompleted () {
								if (resultText.equals("")){
									searchResult.setText("No users found..!!");
								} else {
									searchResult.setText(resultText);
								}
								progressDialog.dismiss();
							}

							@Override
							public void onError (final Throwable e) {
								progressDialog.dismiss();
								searchResult.setText(e.toString());
							}

							@Override
							public void onNext (final AnswerData answerData) {
								resultText = "";
								for (AnswerItem s : answerData.getFlickerImageItems()) {

									if (s.getOwner().getDisplayName().contains(searchEditText.getText().toString())){
										resultText += s.getOwner().getDisplayName() + "\n";
									}
								}
							}
						});

				break;
			case R.id.nextButton:
				Intent intent = new Intent(this, RxJavaSingle.class);
				startActivity(intent);
		}
	}

	@Override
	protected void onDestroy () {
		super.onDestroy();

		//RxJava2
		/*if (disposable!= null && !disposable.isDisposed()){
			disposable.dispose();
		}*/
	}

}
