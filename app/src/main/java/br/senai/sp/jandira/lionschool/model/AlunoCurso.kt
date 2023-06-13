package br.senai.sp.jandira.lionschool.model

data class AlunoCurso(
    val nome: String? = null,
    val sigla: String? = null,
    val icone: String? = null,
    val carga: String? = null,
    val conclusao: String? = null,
    val disciplinas: List<Disciplina>
//    val disciplinas: List<Disciplina>? = null
)
