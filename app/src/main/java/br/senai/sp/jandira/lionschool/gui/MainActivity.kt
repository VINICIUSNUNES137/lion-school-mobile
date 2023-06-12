package br.senai.sp.jandira.lionschool.gui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.R
import br.senai.sp.jandira.lionschool.model.Curso
import br.senai.sp.jandira.lionschool.model.CursosList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import br.senai.sp.jandira.lionschool.ui.theme.LionSchoolTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {

                    MainActivityScreen()
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainActivityScreen() {

    val context = LocalContext.current
    var results by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Curso>())
    }


    Surface() {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(35, 125, 254))
        ) {

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                verticalArrangement = Arrangement.SpaceBetween
            )
            {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {

                    Text(text = "next", color = Color.White)
                    Image(
                        painter = painterResource(id = R.drawable.arrow_right_24),
                        contentDescription = ""
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.logosvg),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth()
                )

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "LION SCHOOL",
                        fontSize = 48.sp,
                        fontWeight = FontWeight(800),
                        color = Color.White
                    )
                    Text(
                        text = "The best school in latin America",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(400),
                        color = Color(218, 218, 218)
                    )
                }

                Button(
                    onClick = {

                        val call = RetrofitFactory().getCursoService().getCursos()

                        call.enqueue(object : Callback<CursosList> {
                            override fun onResponse(
                                call: Call<CursosList>,
                                response: Response<CursosList>
                            ) {
                                results = response.body()!!.cursos
//                                Log.i("ds3m", "${results}: ")
                            }

                            override fun onFailure(call: Call<CursosList>, t: Throwable) {
                                TODO("Not yet implemented")
                                Log.i(
                                    "ds2m",
                                    "onFailure: ${t.message}"
                                )
                            }

                        })

                        openActivity(context)
                              },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(255, 255, 255)),
                ) {
                    Text(
                        text = "GET STARTED",
                        color = Color(35, 125, 254),
                        fontWeight = FontWeight(700)
                    )
                }
            }

        }
    }
}

fun openActivity(context: Context) {
//    var i = 0
//    var listaNomes: List<String>
//    while(i < listaCursos.count()){
//        listaNomes = listOf(listaCursos[i].nome)
//        Log.i("ds2m", "${listaNomes[i]}")
//        i++
//    }
    val intent = Intent(context, CursosActivity::class.java)
    context.startActivity(intent)
}