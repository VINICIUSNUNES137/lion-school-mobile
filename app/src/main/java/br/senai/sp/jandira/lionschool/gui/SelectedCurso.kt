package br.senai.sp.jandira.lionschool.gui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.R
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.arrow_blue),
                contentDescription = ""
            )
            Text(
                modifier = Modifier.clickable {
                val intent = Intent(context, CursosActivity::class.java)
                context.startActivity(intent)
            },
                text = "prev",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(35, 125, 254)
            )

        }
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            backgroundColor = Color(35, 125, 254),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row() {
                Column(
                    modifier = Modifier
                        .width(200.dp)
                        .fillMaxHeight()
                        .padding(start = 12.dp),
                    verticalArrangement = Arrangement.Center



                ) {
                    Text(
                        text = "BEM VINDO(A) AO SISTEMA DE NOTAS",
                        color = Color.White,
//                        fontWeight = FontWeight(600),
                        fontSize = 18.sp
                    )
                    Text(
                        text = "Aplique filtros de pesquisa para que possa localizar o aluno desejado.",
                        color = Color.LightGray,
                        fontWeight = FontWeight(400),
                        fontSize = 12.sp)
                }
                Image(painter = painterResource(id = R.drawable.livro), contentDescription = "", Modifier.fillMaxSize())
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "alunos", color = Color(35, 125, 254), fontWeight = FontWeight.Bold)
        LazyColumn() {
            items(results) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(context, SelectedStudentActivity::class.java)
                            intent.putExtra("matricula", it.matricula)
                            context.startActivity(intent)


                        },
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color(35, 125, 254)
                ) {
                    Row(modifier = Modifier.padding(12.dp)
                        ,verticalAlignment = Alignment.CenterVertically) {
                        AsyncImage(
                            model = it.foto,
                            contentDescription = "${it.nome}",
                            modifier = Modifier.size(80.dp)
                        )
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row() {
                            Text(text = "NOME: ", fontWeight = FontWeight.Bold,color = Color.White)
                            Text(text = "${it.nome}".uppercase(), color = Color.White, fontSize = 14.sp)
                            }
                            Row() {
                            Text(text = "MATRICULA: ", fontWeight = FontWeight.Bold, color = Color.White)
                            Text(text = "${it.matricula}", color = Color.White, fontSize = 14.sp)
                            }
                            Row() {
                                Text(text = "STATUS: ", fontWeight = FontWeight.Bold, color = Color.White)
                                Text(text = "${it.status}", color = Color.White, fontSize = 14.sp)
                            }
                        }
                    }
                }
            }

        }
    }
}

