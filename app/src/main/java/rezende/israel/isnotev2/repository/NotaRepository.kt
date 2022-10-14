package rezende.israel.isnotev2.repository

import kotlinx.coroutines.flow.Flow
import rezende.israel.isnotev2.database.dao.NotaDao
import rezende.israel.isnotev2.model.Nota
import rezende.israel.isnotev2.webclient.NotaWebClient

class NotaRepository(private val dao: NotaDao, private val webClient: NotaWebClient) {

    fun buscaTodas(): Flow<List<Nota>> {
        return dao.buscaTodas()
    }

    suspend fun atualizaTodas() {
        webClient.buscaTodas()?.let { notas ->
            dao.salva(notas)
        }
    }
}