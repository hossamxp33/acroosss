package com.codesroots.mac.firstkotlon.DataLayer.ApiService

import com.codesroots.mac.cards.models.*

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory



interface APIServices {

    @POST("{token}/logup/{username}/{password}")/*{company_id}*/
    fun Login(@Path("token")  token:String,@Path("username")  username:String,
              @Path("password")  password:String): Observable<LogData>

    @POST("logup/{username}/{password}")/*{company_id}*/
    fun LoginFirstTime(@Path("username")  username:String,
                       @Path("password")  password:String): Observable<LogData>



    @POST("fw_main/{auth}/2")/*{company_id}*/
    fun GetCompanyData(@Path("auth") auth: String):
            Observable<List<CompanyDatum>>

    @POST("fw_main/{auth}/12")/*{company_id}*/
    fun GetMyBlanceData(@Path("auth") auth: String):
            Observable<MyBalance>

    @POST("fw_main/{auth}/3")/*{company_id}*/
    fun GetPackageDetails(@Path("auth") auth: String,@Query("val") packageid: String):
            Observable<List<CompanyDatum>>




    @POST("fw_main/{auth}/5")/*{company_id}*/
    fun BuyPackage(@Path("auth") auth: String,@Query("val") packageid: String,@Query("mount") amount: String):
            Observable<Buypackge>

    @POST("fw_main/{auth}/18")/*{company_id}*/
    fun PrintReport(@Path("auth") auth: String,@Query("val") packageid: String):
            Observable<Buypackge>
    @POST("fw_main/{auth}/17")/*{company_id}*/
    fun SliderData(@Path("auth") auth: String):
            Observable<List<SliderElement>>
    @POST("fw_main/{auth}/13")/*{company_id}*/
    fun GetMyDeialyReport(@Path("auth") auth: String):
            Observable<List<ReportDaily>>

    @POST("fw_main/{auth}/13")/*{company_id}*/
    fun GetMyDeialyReport(@Path("auth") auth: String,@Query("val") fromto:String):
            Observable<List<ReportDaily>>
    @GET("fw_main/15")/*{company_id}*/
    fun GetTerms():
            Observable<Terms>
    @GET("fw_main/14")/*{company_id}*/
    fun GetContactData():
            Observable<Terms>

    @GET("fw_main/16")/*{company_id}*/
    fun GetPartnersData():
            Observable<List<PartnersModel>>
    companion object {
        fun create(): APIServices {

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor( HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://across-cities.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory( RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();


            return retrofit.create(APIServices::class.java)
        }


        fun createMap(): APIServices {

            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor( HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()


            val gson = GsonBuilder().setLenient().create()
            val retrofit = retrofit2.Retrofit.Builder()

                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)

                .build()


            return retrofit.create(APIServices::class.java)
        }

    }

}