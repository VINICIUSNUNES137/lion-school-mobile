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
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.lionschool.R
import br.senai.sp.jandira.lionschool.gui.ui.theme.ui.theme.LionSchoolTheme
import br.senai.sp.jandira.lionschool.model.Curso
import br.senai.sp.jandira.lionschool.model.CursosList
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CursosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LionSchoolTheme {
                // A surface container using the 'background' color from the theme

                CreateCursosActivity()
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateCursosActivity() {

    var context = LocalContext.current
    var results by remember {
        mutableStateOf(listOf<br.senai.sp.jandira.lionschool.model.Curso>())
    }


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


    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(35, 125, 254)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )



        {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Image(
                    painter = painterResource(id = R.drawable.arrow_blue),
                    contentDescription = ""
                )
                Text(
                    modifier = Modifier.clickable {
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    },
                    text = "prev",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(35, 125, 254)
                )

            }

            Text(
                text = "What's your course?",
                style = TextStyle(
                    letterSpacing = 2.sp,
                    color = (Color.White),
                    fontSize = 32.sp,
                    fontWeight = FontWeight(200)

                )
            )
           LazyRow(){
               items(results){
                   Spacer(modifier = Modifier.width(24.dp))
                    Button(onClick = {
                        val intent = Intent(context, SelectedCurso::class.java)
                        intent.putExtra("siglaCurso", it.sigla)
                        context.startActivity(intent)
                    },
                        colors = ButtonDefaults.buttonColors(Color(255, 255, 255)),
                        modifier = Modifier.width(100.dp),
                    ) {
                        Text(text = "${it.sigla}", color = Color(35, 125, 254))
                    }
               }
           }

        }
    }
}