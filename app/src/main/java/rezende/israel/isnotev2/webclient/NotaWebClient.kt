package rezende.israel.isnotev2.webclient

import android.util.Log
import rezende.israel.isnotev2.model.Nota
import rezende.israel.isnotev2.webclient.model.NotaRequisicao
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

    suspend fun salva(nota: Nota): Boolean {
        try {
            val resposta = notaService.salva(
                nota.id, NotaRequisicao(
                    titulo = nota.titulo,
                    descricao = nota.descricao,
                    imagem = nota.imagem
                )
            )
            return resposta.isSuccessful
        } catch (e: Exception) {
            Log.e(TAG, "salva: falha ao tentar salvar", e)
        }
        return false
    }

    suspend fun remove(id: String): Boolean {
        try {
            val resposta = notaService.remove(id)
            return resposta.isSuccessful
        } catch (e: Exception) {
            Log.e(TAG, "remove: falha ao tentar remover", e)
        }
        return false
    }


}