package rezende.israel.isnotev2.webclient

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import rezende.israel.isnotev2.webclient.NotaService.NotaService

class RetrofitInicializador {

    private val client by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder().addInterceptor(logging).build()
    }


    private val retrofit = Retrofit.Builder().baseUrl("http://172.19.52.49:8080/")
        .addConverterFactory(MoshiConverterFactory.create()).client(client).build()

    val notaService = retrofit.create(NotaService::class.java)

}