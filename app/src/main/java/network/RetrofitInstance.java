package network;

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

	public interface StackExchangeEndpointInterface {
		@GET("/answers?order=desc&sort=activity&site=stackoverflow")
		Observable<AnswerData> getRecentAnswers ();
	}
}
