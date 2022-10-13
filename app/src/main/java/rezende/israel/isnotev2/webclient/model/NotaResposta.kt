package rezende.israel.isnotev2.webclient.model

import rezende.israel.isnotev2.model.Nota

class NotaResposta(
    val id: String?,
    val titulo: String?,
    val descricao: String?,
    val imagem: String?
) {
    val nota: Nota
        get() = Nota(
            id = 0,
            titulo = titulo ?: "",
            descricao = descricao ?: "",
            imagem = imagem ?: ""
        )

}