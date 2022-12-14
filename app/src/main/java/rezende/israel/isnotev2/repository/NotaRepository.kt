package rezende.israel.isnotev2.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.forEach
import rezende.israel.isnotev2.database.dao.NotaDao
import rezende.israel.isnotev2.model.Nota
import rezende.israel.isnotev2.webclient.NotaWebClient

class NotaRepository(private val dao: NotaDao, private val webClient: NotaWebClient) {

    fun buscaTodas(): Flow<List<Nota>> {
        return dao.buscaTodas()
    }

    private suspend fun atualizaTodas() {
        webClient.buscaTodas()?.let { notas ->
            val notasSincronizadas = notas.map { nota ->
                nota.copy(sincronizada = true)
            }
            dao.salva(notasSincronizadas)
        }
    }

    fun buscaPorId(id: String): Flow<Nota> {
        return dao.buscaPorId(id)
    }

    suspend fun remove(id: String) {
        dao.desativa(id)
        if(webClient.remove(id)){
            dao.remove(id)
        }
    }

    suspend fun salva(nota: Nota) {
        dao.salva(nota)
        if (webClient.salva(nota)) {
            val notaSincronizada = nota.copy(sincronizada = true)
            dao.salva(notaSincronizada)
        }
    }


    suspend fun sincroniza() {
        val notasDesativadas = dao.buscaDesativadas().first()
        notasDesativadas.forEach { notaDesativada ->
            remove(notaDesativada.id)
        }
        val notasNaoSincronizadas = dao.buscaNaoSincronizadas().first()
        notasNaoSincronizadas.forEach{ notasNaoSincronizada ->
            salva(notasNaoSincronizada)
        }
        atualizaTodas()
    }
}