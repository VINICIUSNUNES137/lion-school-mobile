package br.senai.sp.jandira.lionschool.service

import br.senai.sp.jandira.lionschool.model.Aluno
import br.senai.sp.jandira.lionschool.model.AlunosList
import br.senai.sp.jandira.lionschool.model.CursosList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlunoService {

    @GET("alunos")
    fun getAlunosCurso(@Query("curso") curso: String): Call<AlunosList>

    @GET("alunos/{matricula}")
    fun getAlunoMatricula(@Path("matricula") matricula: String): Call<Aluno>
}