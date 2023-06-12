package br.senai.sp.jandira.lionschool.gui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.senai.sp.jandira.lionschool.gui.ui.theme.LionSchoolTheme
import br.senai.sp.jandira.lionschool.model.AlunosList
import br.senai.sp.jandira.lionschool.model.CursosList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectedCurso : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        Log.i("SIGLA DO CURSOOOO", "siglaCurso: ${intent.extras()}")

        var siglaDoCurso = intent.getStringExtra("siglaCurso").toString()
        Log.i("peguei a sigla selected", "${siglaDoCurso}")
        setContent {
            LionSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreateSelectedCursoActivity(siglaDoCurso)
                }
            }
        }
    }
}

@Composable
fun CreateSelectedCursoActivity(sigla: String) {
    var context = LocalContext.current
    var results by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Aluno>())
    }


    val call = RetrofitFactory().getAlunosService().getAlunosCurso(sigla)

    call.enqueue(object : Callback<AlunosList> {
        override fun onResponse(
            call: Call<AlunosList>,
            response: Response<AlunosList>
        ) {
            results = response.body()!!.alunos
//                                Log.i("ds3m", "${results}: ")
        }

        override fun onFailure(call: Call<AlunosList>, t: Throwable) {
            TODO("Not yet implemented")
            Log.i(
                "ds2m",
                "onFailure: ${t.message}"
            )
        }

    })
    Column(modifier = Modifier.fillMaxSize()) {
        

    Text(text = "alunos", color = Color(35, 125, 254))
    LazyColumn(modifier = Modifier.padding(start = 32.dp, end = 32.dp)) {
        items(results) {

            Card(modifier = Modifier.fillMaxWidth().clickable {
                val intent = Intent(context, SelectedStudentActivity::class.java)
                intent.putExtra("matricula", it.matricula)
                context.startActivity(intent)

            }, backgroundColor = Color(35, 125, 254)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = it.foto,
                        contentDescription = "${it.nome}",
                        modifier = Modifier.size(80.dp)
                    )
                    Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                        Text(text = "${it.nome}", color = Color.White)
                        Text(text = "${it.matricula}", color = Color.White)
                    }
                }
            }
        }

    }
    }
}

