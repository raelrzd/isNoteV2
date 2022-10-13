package rezende.israel.isnotev2.webclient

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import rezende.israel.isnotev2.webclient.NotaService.NotaService

class RetrofitInicializador {

    private val retrofit = Retrofit.Builder().baseUrl("http://localhost:8080/").addConverterFactory(MoshiConverterFactory.create()).build()

    val notaService = retrofit.create(NotaService::class.java)

}