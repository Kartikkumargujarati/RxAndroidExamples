package network;

import java.util.ArrayList;
import java.util.List;

import model.AnswerData;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by kartik.gujarati on 6/9/17.
 */

public class RetrofitInstance {

	public static final String BASE_URL = "https://api.stackexchange.com/2.2/";

	public Retrofit getRetrofit () {
		return new Retrofit.Builder()

				//RxJava2
				//.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create())
				.baseUrl(BASE_URL)
				.build();
	}


	//Simple method to replicate an API call.
	public List<String> getColors (String s) {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		List<String> colors = new ArrayList<>();
		List<String> resultColors = new ArrayList<>();

		for (int i = 0; i <1000; i++){
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
	public interface StackExchangeEndpointInterface {
		@GET("/answers?order=desc&sort=activity&site=stackoverflow")
		Observable<AnswerData> getRecentAnswers ();
	}
}
