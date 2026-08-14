package com.example.clientes.service;

import com.example.clientes.exception.ClienteNotFoundException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.clientes.model.Cliente;
import com.example.clientes.model.TipoCliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClienteServiceTest {

	private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        clienteService = new ClienteService();
    }

    @Test
    void deberiaCrearCliente() {

        Cliente cliente = new Cliente(
                "001",
                "Juan Perez",
                "juan@gmail.com",
                30,
                TipoCliente.VIP
        );

        Cliente resultado = clienteService.crearCliente(cliente);

        assertThat(resultado).isEqualTo(cliente);
    }
    
    @Test
    void deberiaObtenerTodosLosClientes() {

        Cliente cliente1 = new Cliente(
                "001",
                "Juan Perez",
                "juan@gmail.com",
                30,
                TipoCliente.VIP
        );

        Cliente cliente2 = new Cliente(
                "002",
                "Maria Lopez",
                "maria@gmail.com",
                25,
                TipoCliente.REGULAR
        );

        clienteService.crearCliente(cliente1);
        clienteService.crearCliente(cliente2);

        var resultado = clienteService.obtenerTodos();

        assertThat(resultado)
                .hasSize(2)
                .containsExactly(cliente1, cliente2);
    }
    
    @Test
    void deberiaObtenerClientePorId() {

        Cliente cliente = new Cliente(
                "001",
                "Juan Perez",
                "juan@gmail.com",
                30,
                TipoCliente.VIP
        );

        clienteService.crearCliente(cliente);

        Cliente resultado =
                clienteService.obtenerPorId("001");

        assertThat(resultado)
                .isEqualTo(cliente);
    }
    
    @Test
    void deberiaLanzarExcepcionCuandoClienteNoExiste() {

        assertThatThrownBy(() ->
                clienteService.obtenerPorId("999")
        )
        .isInstanceOf(ClienteNotFoundException.class)
        .hasMessage("Cliente no encontrado: 999");
    }
    
    @Test
    void deberiaActualizarCliente() {

        Cliente clienteOriginal = new Cliente(
                "001",
                "Juan Perez",
                "juan@gmail.com",
                30,
                TipoCliente.REGULAR
        );

        clienteService.crearCliente(clienteOriginal);

        Cliente datosActualizados = new Cliente(
                "001",
                "Juan Perez",
                "nuevo@gmail.com",
                31,
                TipoCliente.VIP
        );

        Cliente resultado =
                clienteService.actualizarCliente(
                        "001",
                        datosActualizados
                );

        assertThat(resultado.email())
                .isEqualTo("nuevo@gmail.com");

        assertThat(resultado.edad())
                .isEqualTo(31);

        assertThat(resultado.tipoCliente())
                .isEqualTo(TipoCliente.VIP);
    }
    
    @Test
    void deberiaLanzarExcepcionAlActualizarClienteInexistente() {

        Cliente cliente = new Cliente(
                "999",
                "Cliente inexistente",
                "test@gmail.com",
                30,
                TipoCliente.REGULAR
        );

        assertThatThrownBy(() ->
                clienteService.actualizarCliente("999", cliente)
        )
        .isInstanceOf(ClienteNotFoundException.class)
        .hasMessage("Cliente no encontrado: 999");
    }
    
    @Test
    void deberiaEliminarCliente() {

        Cliente cliente = new Cliente(
                "001",
                "Juan Perez",
                "juan@gmail.com",
                30,
                TipoCliente.VIP
        );

        clienteService.crearCliente(cliente);

        clienteService.eliminarCliente("001");

        assertThat(clienteService.obtenerTodos())
                .isEmpty();
    }
    
    @Test
    void deberiaLanzarExcepcionAlEliminarClienteInexistente() {

        assertThatThrownBy(() ->
                clienteService.eliminarCliente("999")
        )
        .isInstanceOf(ClienteNotFoundException.class)
        .hasMessage("Cliente no encontrado: 999");
    }
    
    @Test
    void deberiaAplicarDescuentoAClienteVip() {

        Cliente cliente = new Cliente(
                "001",
                "Juan Perez",
                "juan@gmail.com",
                30,
                TipoCliente.VIP
        );

        String resultado =
                clienteService.procesarCliente(cliente);

        assertThat(resultado)
                .isEqualTo(
                        "Cliente VIP: descuento del 20%"
                );
    }
    
    @Test
    void deberiaNoAplicarDescuentoAClienteRegular() {

        Cliente cliente = new Cliente(
                "002",
                "Maria Lopez",
                "maria@gmail.com",
                25,
                TipoCliente.REGULAR
        );

        String resultado =
                clienteService.procesarCliente(cliente);

        assertThat(resultado)
                .isEqualTo(
                        "Cliente REGULAR: sin descuento"
                );
    }
    
    
}
