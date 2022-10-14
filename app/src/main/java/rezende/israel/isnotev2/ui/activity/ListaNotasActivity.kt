package rezende.israel.isnotev2.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rezende.israel.isnotev2.database.AppDatabase
import rezende.israel.isnotev2.databinding.ActivityListaNotasBinding
import rezende.israel.isnotev2.extensions.vaiPara
import rezende.israel.isnotev2.model.Nota
import rezende.israel.isnotev2.ui.recyclerview.adapter.ListaNotasAdapter
import rezende.israel.isnotev2.webclient.RetrofitInicializador
import rezende.israel.isnotev2.webclient.model.NotaResposta

class ListaNotasActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityListaNotasBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaNotasAdapter(this)
    }
    private val dao by lazy {
        AppDatabase.instancia(this).notaDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraFab()
        configuraRecyclerView()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                buscaNotas()
            }
        }


    }

    private fun retrofitSemCoroutines() {

        // Criação da CALL para requisição de modo sincrono ou assincrono
        //        val call: Call<List<NotaResposta>> =
        //            RetrofitInicializador().notaService.buscaTodasNotas()


        // Requisição com call de modo sincrono (execute)
        //        lifecycleScope.launch(IO) {
        //            val resposta: Response<List<NotaResposta>> = call.execute()
        //            resposta.body()?.let { notaResposta ->
        //                val notas: List<Nota> = notaResposta.map {
        //                    it.nota
        //                }
        //                Log.i("ListaNotas", "onCreate: $notas")
        //            }
        //        }

        // Requisição com call de modo assincrono (enqueue)
        //        call.enqueue(object : Callback<List<NotaResposta>?> {
        //            override fun onResponse(
        //                call: Call<List<NotaResposta>?>,
        //                resposta: Response<List<NotaResposta>?>
        //            ) {
        //                resposta.body()?.let { notaResposta ->
        //                    val notas: List<Nota> = notaResposta.map {
        //                        it.nota
        //                    }
        //                    Log.i("ListaNotas", "onCreate: $notas")
        //                }
        //            }
        //
        //            override fun onFailure(call: Call<List<NotaResposta>?>, t: Throwable) {
        //                Log.e("ListaNotas", "onFailure: ", t)
        //            }
        //        })
        
    }

    private fun configuraFab() {
        binding.activityListaNotasFab.setOnClickListener {
            Intent(this, FormNotaActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun configuraRecyclerView() {
        binding.activityListaNotasRecyclerview.adapter = adapter
        adapter.quandoClicaNoItem = { nota ->
            vaiPara(FormNotaActivity::class.java) {
                putExtra(NOTA_ID, nota.id)
            }
        }
    }

    private suspend fun buscaNotas() {
        dao.buscaTodas()
            .collect { notasEncontradas ->
                binding.activityListaNotasMensagemSemNotas.visibility =
                    if (notasEncontradas.isEmpty()) {
                        binding.activityListaNotasRecyclerview.visibility = GONE
                        VISIBLE
                    } else {
                        binding.activityListaNotasRecyclerview.visibility = VISIBLE
                        adapter.atualiza(notasEncontradas)
                        GONE
                    }
            }
    }
}