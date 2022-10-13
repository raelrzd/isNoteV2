package rezende.israel.isnotev2.webclient

import retrofit2.Retrofit
import rezende.israel.isnotev2.webclient.NotaService.NotaService

class RetrofitInicializador {

    private val retrofit = Retrofit.Builder().baseUrl("http://localhost:8080/").build()

    val notaService = retrofit.create(NotaService::class.java)

}