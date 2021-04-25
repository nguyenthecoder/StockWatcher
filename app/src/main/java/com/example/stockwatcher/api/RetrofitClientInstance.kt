package com.example.stockwatcher.api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClientInstance {

    companion object{
        @JvmStatic
        private var retrofit: Retrofit? = null


        @JvmStatic
        private var retrofitTickerLookup: Retrofit? = null

        private val key = "65d060beb5aa4b30be79716bc3c6dbf1"
        private val baseUrl = "https://newsapi.org/here/"

        //IEX
        private val IEX_SANDBOX_BASE_URL = "https://sandbox.iexapis.com"
        private val IEX_SANBOX_TOKEN = "Tpk_4083caf83bde4e94b709e982d5c97da1"
        const val IEX_VERSION = "stable/"

        //query=aa : aa is the search term
        //apiKey is demo
//        const val TICKER_LOOKUP_BASE_URL = "https://financialmodelingprep.com"
//        const val TICKER_LOOKUP_VERSION = "api/v3/"
//        const val TICKER_LOOKUP_API_KEY= "demo"
//        const val TICKER_LOOKUP_DEFAULT_LIMIT = "10"
    }

    fun instance():Retrofit?{
        val logger = HttpLoggingInterceptor()
        val okHttpClient = constructOkClientBuilder(mapOf("apiKey" to key))
        if(retrofit==null) {

            logger.level = HttpLoggingInterceptor.Level.HEADERS

            val client = okHttpClient.addInterceptor(logger)
            client.connectTimeout(30, TimeUnit.SECONDS)

            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(client.build())
                    .build()
        }
        return retrofit
    }

    fun instanceRetrofitIEX(): Retrofit?{
        val logger = HttpLoggingInterceptor()
        val okHttpClient = constructOkClientBuilder(mapOf("token" to IEX_SANBOX_TOKEN))
        if(retrofitTickerLookup==null) {

            logger.level = HttpLoggingInterceptor.Level.HEADERS

            val client = okHttpClient.addInterceptor(logger)
            client.connectTimeout(30, TimeUnit.SECONDS)

            retrofitTickerLookup = Retrofit.Builder()
                    .baseUrl(IEX_SANDBOX_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(client.build())
                    .build()
        }
        return retrofitTickerLookup
    }

    private fun constructOkClientBuilder(dict: Map<String, String> ): OkHttpClient.Builder{
         val okHttpClient = OkHttpClient.Builder().addInterceptor{chain->

            var original = chain.request()
            var newUrl = original.url

             var newUrlBuilder = newUrl.newBuilder()
             dict.forEach{
                 key,value->
                 newUrlBuilder.addQueryParameter(key, value)
             }

             newUrl = newUrlBuilder.build()

            val newRequest = original.newBuilder().url(newUrl).build()
            chain.proceed(newRequest)
        }
        return  okHttpClient
    }


}
