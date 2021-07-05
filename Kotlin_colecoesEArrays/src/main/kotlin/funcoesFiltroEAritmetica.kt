fun funcoesDeFiltroEAritmetica() {
    val idades: IntArray = intArrayOf(10, 12, 18, 33, 40, 67)
    val maiorIdade = idades.maxOrNull()
    println(maiorIdade)

    val menorIdade = idades.minOrNull()
    println("Menor idade: ${menorIdade}")

    val media = idades.average()
    println("Media: ${media}")

//    Verifica se TODOS valores que satisfazem a condição e retorna um boolean
    val todosMaiores = idades.all { it >= 18 }
    println("Todos maiores ? $todosMaiores")

//    Verifica se existem valores que satisfaçam a condição e retorna um boolean
    val existeMaiorDeIdade = idades.any { it >= 18 }
    println("Existe maior de idade ${existeMaiorDeIdade}")

//    Filtra por todos os valores que satisfaçam a condição
    val maioresDeDezoito = idades.filter { it >= 18 }
    println("Maiores $maioresDeDezoito")

//    Busca o primeiro valor que satisfaça a condição.
    val buscaPrimeiroValor = idades.find { it == 18 }
    println("Busca ${buscaPrimeiroValor}")
}