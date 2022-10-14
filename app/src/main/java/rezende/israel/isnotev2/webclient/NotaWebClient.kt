package rezende.israel.isnotev2.webclient

import rezende.israel.isnotev2.model.Nota

class NotaWebClient {

    suspend fun buscaTodas(): List<Nota> {
        val notasResposta = RetrofitInicializador().notaService.buscaTodasNotasComCoroutines()
        return notasResposta.map { notaResposta ->
            notaResposta.nota
        }
    }
}