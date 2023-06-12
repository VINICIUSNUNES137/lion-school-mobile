package br.senai.sp.jandira.lionschool.gui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import br.senai.sp.jandira.lionschool.gui.ui.theme.LionSchoolTheme
import br.senai.sp.jandira.lionschool.model.Aluno
import br.senai.sp.jandira.lionschool.service.RetrofitFactory
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



    var results by remember {
        mutableStateOf(Aluno())
    }




    val call = RetrofitFactory().getAlunosService().getAlunoMatricula(matricula)

    call.enqueue(object : Callback<Aluno> {
        override fun onResponse(
            call: Call<Aluno>,
            response: Response<Aluno>
        ) {
            results = response.body()!!

//                              Log.i("ds3m", "${results}: ")
        }

        override fun onFailure(call: Call<Aluno>, t: Throwable) {
            TODO("Not yet implemented")
            Log.i(
                "ds2m",
                "onFailure: ${t.message}"
            )
        }

    })
    Column(modifier = Modifier.fillMaxSize()) {


        Text(text = "${results}")
//        Text(text = "${results.status}")
//        Text(text = "${results.matricula}")
    }
}
