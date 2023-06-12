package br.senai.sp.jandira.lionschool.model

class Curso(
    val nome: String,
    val sigla: String,
    val icone: String,
    val carga: String,
    val disciplinas: List<Disciplina>

)