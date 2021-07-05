fun List<Livro?>.imprimeComMarcadores(){
    val textoFormatado = this
        .filterNotNull()
        .joinToString("\n") {
        " - ${it.titulo} de ${it.autor}"
    }

    println("### Lista de Livros ### \n${textoFormatado}")
}