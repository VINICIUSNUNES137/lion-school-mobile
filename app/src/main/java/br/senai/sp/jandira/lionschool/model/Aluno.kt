package br.senai.sp.jandira.lionschool.model

data class Aluno(
    var foto: String? = null,
    var nome: String? = null,
    var matricula: String? = null,
    var sexo: String? = null,
    var curso: List<AlunoCurso>? = null,
    var status: String? = null,

)
