package rezende.israel.isnotev2.webclient.NotaService

import retrofit2.Call
import retrofit2.http.GET
import rezende.israel.isnotev2.model.Nota

interface NotaService {

    @GET("notas")
    fun buscaTodasNotas(): Call<List<Nota>>

}