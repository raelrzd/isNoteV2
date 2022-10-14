package rezende.israel.isnotev2.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Nota(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val titulo: String,
    val descricao: String,
    val imagem: String? = null
)
