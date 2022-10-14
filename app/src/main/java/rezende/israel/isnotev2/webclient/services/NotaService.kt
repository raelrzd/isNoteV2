package rezende.israel.isnotev2.webclient.services

import retrofit2.http.GET
import rezende.israel.isnotev2.webclient.model.NotaResposta

interface NotaService {

    // Função de requisição para o método utilizado com Call
//    @GET("notas")
//    fun buscaTodasNotas(): Call<List<NotaResposta>>

    @GET("notas")
    suspend fun buscaTodasNotasComCoroutines(): List<NotaResposta>

}