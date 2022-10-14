package rezende.israel.isnotev2.webclient

import android.util.Log
import rezende.israel.isnotev2.model.Nota
import rezende.israel.isnotev2.webclient.services.NotaService

private val notaService: NotaService = RetrofitInicializador().notaService

private const val TAG = "NotaWebClient"

class NotaWebClient {

    suspend fun buscaTodas(): List<Nota>? {
        return try {
            val notasResposta = notaService.buscaTodasNotasComCoroutines()
            return notasResposta.map { notaResposta ->
                notaResposta.nota
            }
        } catch (e: Exception) {
            Log.e(TAG, "buscaTodas: ", e)
            null
        }
    }
}