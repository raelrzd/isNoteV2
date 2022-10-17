package rezende.israel.isnotev2.webclient.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import rezende.israel.isnotev2.webclient.model.NotaRequisicao
import rezende.israel.isnotev2.webclient.model.NotaResposta

interface NotaService {

    // Função de requisição para o método utilizado com Call
//    @GET("notas")
//    fun buscaTodasNotas(): Call<List<NotaResposta>>

    @GET("notas")
    suspend fun buscaTodasNotasComCoroutines(): List<NotaResposta>

    @PUT("notas/{id}")
    suspend fun salva(@Path("id") id: String, @Body nota: NotaRequisicao): Response<NotaResposta>

    @DELETE("notas/{id}")
    suspend fun remove(@Path("id") id: String): Response<Boolean>

}