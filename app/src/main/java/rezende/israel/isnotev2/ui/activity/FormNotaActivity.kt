package rezende.israel.isnotev2.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import rezende.israel.isnotev2.R
import rezende.israel.isnotev2.database.AppDatabase
import rezende.israel.isnotev2.databinding.ActivityFormNotaBinding
import rezende.israel.isnotev2.extensions.tentaCarregarImagem
import rezende.israel.isnotev2.model.Nota
import rezende.israel.isnotev2.repository.NotaRepository
import rezende.israel.isnotev2.ui.dialog.FormImagemDialog
import rezende.israel.isnotev2.webclient.NotaWebClient

class FormNotaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityFormNotaBinding.inflate(layoutInflater)
    }
    private var imagem: MutableStateFlow<String?> = MutableStateFlow(null)

    private val repository by lazy {
        NotaRepository(
            AppDatabase.instancia(this).notaDao(),
            NotaWebClient()
        )
    }

    private var notaId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.activityFormNotaToolbar)
        configuraImagem()
        tentaCarregarIdDaNota()
        lifecycleScope.launch {
            launch {
                tentaBuscarNota()
            }
            launch {
                configuraCarregamentoDeImagem()
            }
        }
    }

    private suspend fun configuraCarregamentoDeImagem() {
        val imagemNota = binding.activityFormNotaImagem
        imagem.collect { imagemNova ->
            imagemNota.visibility =
                if (imagemNova.isNullOrBlank())
                    GONE
                else {
                    imagemNota.tentaCarregarImagem(imagemNova)
                    VISIBLE
                }
        }
    }

    private suspend fun tentaBuscarNota() {
        notaId?.let { id ->
            repository.buscaPorId(id)
                .filterNotNull()
                .collect { notaEncontrada ->
                    notaId = notaEncontrada.id
                    imagem.value = notaEncontrada.imagem
                    binding.activityFormNotaTitulo.setText(notaEncontrada.titulo)
                    binding.activityFormNotaDescricao.setText(notaEncontrada.descricao)
                }
        }
    }

    private fun tentaCarregarIdDaNota() {
        notaId = intent.getStringExtra(NOTA_ID)
    }

    private fun configuraImagem() {
        binding.activityFormNotaAdicionarImagem.setOnClickListener {
            FormImagemDialog(this)
                .mostra(imagem.value) { imagemCarregada ->
                    binding.activityFormNotaImagem
                        .tentaCarregarImagem(imagemCarregada)
                    imagem.value = imagemCarregada
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.form_nota_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.form_nota_menu_salvar -> {
                salva()
            }
            R.id.form_nota_menu_remover -> {
                remove()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun remove() {
        lifecycleScope.launch {
            notaId?.let { id ->
                repository.remove(id)
            }
            finish()
        }
    }

    private fun salva() {
        val nota = criaNota()
        lifecycleScope.launch {
            repository.salva(nota)
            finish()
        }
    }

    private fun criaNota(): Nota {
        val titulo = binding.activityFormNotaTitulo.text.toString()
        val descricao = binding.activityFormNotaDescricao.text.toString()
        return notaId?.let { id ->
            Nota(
                id = id,
                titulo = titulo,
                descricao = descricao,
                imagem = imagem.value
            )
        } ?: Nota(
            titulo = titulo,
            descricao = descricao,
            imagem = imagem.value
        )
    }
}
