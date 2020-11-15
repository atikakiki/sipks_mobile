package id.ac.its.attendance.Retrofit.ServerAttendance;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import id.ac.its.attendance.BuildConfig;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerAttendance {
    private static final String TAG = "RetrofitBuilder";
    private static final String BASE_URL = "https://foodyshark.sembah.com/api/";

    private final static OkHttpClient client = buildClient();
    private final static Retrofit retrofit = buildRetrofit(client);

    private static OkHttpClient buildClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();

                        Request.Builder builder = request.newBuilder()
                                .addHeader("Accept", "application/json")
                                .addHeader("Connection","close");
                        request = builder.build();

                        return chain.proceed(request);
                    }
                });
        if(BuildConfig.DEBUG){
            builder.addNetworkInterceptor(new StethoInterceptor());
        }

        return builder.build();
    }

    private static Retrofit buildRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> service){
        return retrofit.create(service);
    }

    public static <T> T createServiceWithAuth(Class <T> service, final TokenManager tokenManager){
        OkHttpClient newClient = client.newBuilder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                Request.Builder builder = request.newBuilder();

                if(tokenManager.getToken().getAccessToken() !=null){
                    builder.addHeader("Authorization","Bearer " + tokenManager.getToken().getAccessToken());
                }

                request=builder.build();
                return chain.proceed(request);
            }
        }).authenticator(new Authenticator() {
            @Nullable
            @Override
            public Request authenticate(@Nullable Route route, Response response) throws IOException {
                AccessToken token = tokenManager.getToken();

                ApiService service = RetrofitBuilder.createService(ApiService.class);
                Call<AccessToken> call = service.refresh(token.getRefreshToken());
                retrofit2.Response<AccessToken> res = call.execute();

                if(res.isSuccessful()){
                    AccessToken newToken = res.body();
                    tokenManager.saveToken(newToken);

                    return response.request().newBuilder().header("Authorization","Bearer " + res.body().getAccessToken()).build();
                }
                else {
                    return null;
                }
            }
        }).build();

        Retrofit newRetrofit = retrofit.newBuilder().client(newClient).build();
        return newRetrofit.create(service);
    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }
}
