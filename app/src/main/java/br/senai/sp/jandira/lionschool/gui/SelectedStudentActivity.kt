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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.R
import br.senai.sp.jandira.lionschool.gui.ui.theme.LionSchoolTheme
import br.senai.sp.jandira.lionschool.model.Aluno
import br.senai.sp.jandira.lionschool.model.Disciplina
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SelectedStudentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var matricula = intent.getStringExtra("matricula").toString()
        setContent {
            LionSchoolTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreateSelectedStudentActivity(matricula = matricula)
                }
            }
        }
    }
}

@Composable
fun CreateSelectedStudentActivity(matricula: String) {
    //    Text(text = "${matricula}")
    var context = LocalContext.current


    var results by remember {
        mutableStateOf(Aluno())
    }

    var disciplinas by remember {
        mutableStateOf(listOf<Disciplina>())
    }


    //    Text(text = "${matricula}")

    val call = RetrofitFactory().getAlunosService().getAlunoMatricula(matricula)

    call.enqueue(object : Callback<Aluno> {
        override fun onResponse(
            call: Call<Aluno>,
            response: Response<Aluno>
        ) {
            results = response.body()!!
            disciplinas = response.body()!!.curso?.get(0)!!.disciplinas

        }

        override fun onFailure(call: Call<Aluno>, t: Throwable) {
            TODO("Not yet implemented")
            Log.i(
                "ds2m",
                "onFailure: ${t.message}"
            )
        }

    })
    Column(modifier = Modifier.fillMaxSize().padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
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
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = results.foto,
                contentDescription = "${results.nome}",
                modifier = Modifier.size(100.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "${results.nome}"
                        .uppercase(),
                    color = Color(35, 125, 254),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${results.status}",
                    color = Color(35, 125, 254),
                    fontSize = 16.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            backgroundColor = Color(35, 125, 254),
            shape = RoundedCornerShape(16.dp)
        )
        {
            Column(
                Modifier.fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "INFORMAÇÕES",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp
                )
                Row() {
                    Text(text = "MATRICULA: ", color = Color.White, fontWeight = FontWeight.Bold)
                    Text(text = "${results.matricula}", color = Color.White)
                }
                Row() {
                    Text(text = "CURSO: ", color = Color.White, fontWeight = FontWeight.Bold)
                    val parteCortada = results.curso?.get(0)?.nome?.length?.let {
                        results.curso?.get(0)?.nome?.cortarString(17,
                            it
                        )
                    }
                    if (parteCortada != null) {
                        Text(text = parteCortada, color = Color.White)
                    }
                }
                Row() {
                    Text(
                        text = "QUANTIDADE DE DISCIPLINAS: ",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "${results.curso?.get(0)?.disciplinas?.size}", color = Color.White)
                }
                Row() {
                    Text(text = "CARGA: ", color = Color.White, fontWeight = FontWeight.Bold)
                    Text(text = "${results.curso?.get(0)?.carga}", color = Color.White)
                }
                Row() {
                    Text(text = "CONCLUSÃO: ", color = Color.White, fontWeight = FontWeight.Bold)
                    Text(text = "${results.curso?.get(0)?.conclusao}", color = Color.White)

                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        LazyRow() {
            items(disciplinas) {
                Spacer(modifier = Modifier.width(16.dp))
                Card(
                    modifier = Modifier
                        .height(100.dp)
                        .width(200.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    if (it.status.uppercase() == "APROVADO") {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(96, 156, 81)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,

                            ) {
                            Text(
                                text = "${it.nome}",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                            Text(
                                text = "APROVADO",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${it.media}",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(164, 65, 65)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,

                            ) {
                            Text(
                                text = "${it.nome}",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                            Text(
                                text = "REPROVADO",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${it.media}",
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LazyRow() {
                items(disciplinas) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Column() {
                        Text(text = "${it.media}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Column(
                            modifier = Modifier
                                .width(20.dp)
                                .height(200.dp)
                                .background(Color(230, 230, 230)),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            if (it.status.uppercase() == "APROVADO") {
                                Column(
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height((it.media.toInt() * 2).dp)
                                        .background(Color(96, 156, 81))
                                ) {
                                }
                            } else {
                                Column(
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height((it.media.toInt() * 2).dp)
                                        .background(Color(164, 65, 65))
                                ) {
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        val parteCortada = it.nome.cortarString(0 , 3)
                        Text(text = parteCortada.uppercase())


                    }
                }
            }

        }
        //AQUIIIII
    }
}

@Composable
fun String.cortarString(inicio: Int, fim: Int): String {
    return this.substring(inicio, fim)
}
