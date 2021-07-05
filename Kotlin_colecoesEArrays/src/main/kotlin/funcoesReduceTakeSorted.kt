import java.math.BigDecimal
import java.math.RoundingMode

fun funcoesReduceSortedTake() {
    val salarios = bigDecimalArrayOf("1500.55", "2000.00", "5000.00", "10000.00")

    println(salarios.contentToString())

    val aumento = "1.1".toBigDecimal()
    val salarioComAumento: Array<BigDecimal> = salarios.map { salario -> calculaAumentoRelativo(salario, aumento) }
        .toTypedArray()
    println(salarioComAumento.contentToString())

    val gastoInicial = salarioComAumento.somatoria()
    println(gastoInicial)

    val meses = 6.toBigDecimal()
    val gastoTotal = salarioComAumento.fold(gastoInicial) { acumulador, salario ->
        acumulador + (salario * meses).setScale(2, RoundingMode.UP)
    }
    println(gastoTotal)

//    val salariosOrdenados = salarioComAumento.sorted()
//    val tresUltimosSalarios: Array<BigDecimal> = salariosOrdenados.takeLast(3).toTypedArray()
//    val media = tresUltimosSalarios.media()

    val media = salarioComAumento.sorted().takeLast(3).toTypedArray().media()

    println(media)
}