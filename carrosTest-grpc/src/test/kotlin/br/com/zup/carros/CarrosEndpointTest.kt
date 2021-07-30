package br.com.zup.carros

import br.com.zup.CarroRequest
import br.com.zup.CarrosTestGrpcServiceGrpc
import io.grpc.Channel
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class CarrosEndpointTest(
    val repository: CarroRepository,
    val grpcClient: CarrosTestGrpcServiceGrpc.CarrosTestGrpcServiceBlockingStub
) {

    @BeforeEach
    fun setup(){
        repository.deleteAll()
    }

    /*
    * 1. Happy path - ok
    * Quando já existe carro com a placa - ok
    * Quando os dados de entrada são inválidos
     */

    @Test
    fun `deve adicionar um novo carro`() {

        // cenario

        // executar ação

        val response = grpcClient.adicionar(
            CarroRequest.newBuilder()
                .setModelo("Gol")
                .setPlaca("HPX-1234")
                .build()
        )

        //validação
        assertNotNull(response.id)
        assertTrue(repository.existsById(response.id))
    }

    @Test
    fun `não deve adicionar novo carro quando o carro com placa já existente`() {
        //cenário

        val existente = repository.save(Carro(modelo = "PALIO", placa = "OIP-3030"))
        //ação

        val error = org.junit.jupiter.api.assertThrows<StatusRuntimeException> {
            grpcClient.adicionar(
                CarroRequest.newBuilder()
                    .setModelo("Ferrari")
                    .setPlaca(existente.placa)
                    .build()
            )
        }

        //validação

        with(error) {
            assertEquals(Status.ALREADY_EXISTS.code, error.status.code)
            assertEquals("Carro com placa existente", error.status.description)
        }
    }

    @Test
    fun `não deve adicionar novo carro quando dados de entrada forem invalidos`() {
        //cenário

        //ação

        val error = org.junit.jupiter.api.assertThrows<StatusRuntimeException> {
            grpcClient.adicionar(
                CarroRequest.newBuilder()
                    .setModelo("")
                    .setPlaca("")
                    .build()
            )
        }

        //validação

        with(error) {
            assertEquals(Status.INVALID_ARGUMENT.code, error.status.code)
            assertEquals("Dados de entrada inválidos", error.status.description)
        }
    }
}

@Factory
class Clients {
    @Singleton
    fun blockingStub(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): CarrosTestGrpcServiceGrpc.CarrosTestGrpcServiceBlockingStub? {
        return CarrosTestGrpcServiceGrpc.newBlockingStub(channel)
    }
}
