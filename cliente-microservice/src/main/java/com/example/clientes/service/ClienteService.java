package com.example.clientes.service;

import com.example.clientes.model.Cliente;
import com.example.clientes.exception.ClienteNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {

    /*
     * Lsta utilizada como almacenamiento tempral de clientes.
     *
     */
    private final List<Cliente> clientes = new ArrayList<>();

    /**
     * crea un nevo cliente y lo almacena en la lista.
     *
     * @param cliente cliente que se desea registrar
     * @return cliente creado
     */
    public Cliente crearCliente(Cliente cliente) {

        clientes.add(cliente);

        return cliente;
    }

    /**
     * obtiene todos los clientes registrados.
     *
     * @return lista de clientes registrados
     */
    public List<Cliente> obtenerTodos() {

        return List.copyOf(clientes);
    }

    /**
     * busca un cliente utilizando su identificador.
     *
     *
     * @param id identificador del cliente
     * @return cliente encontrado
     * @throws ClienteNotFoundException si el cliente no existe
     */
    public Cliente obtenerPorId(String id) {

        return clientes.stream()
                .filter(cliente -> cliente.id().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new ClienteNotFoundException(
                                "Cliente no encontrado: " + id
                        )
                );
    }

    /**
     * actualiza la información de un cliente existente.
     *
     *
     * @param id identificador del cliente que se desea actualizar
     * @param clienteActualizado información nueva del cliente
     * @return cliente actualizado
     * @throws ClienteNotFoundException si el cliente no existe
     */
    public Cliente actualizarCliente(
            String id,
            Cliente clienteActualizado) {

        for (int i = 0; i < clientes.size(); i++) {

            // buscamos el cliente utilizando el ID recibido.
            if (clientes.get(i).id().equals(id)) {

                /*
                 * los Records son inmutables, por lo que creamos
                 * una nueva instancia con la información actualizada.
                 *
                 * conservamos el ID recibido como identificador
                 * del recurso que estamos actualizando.
                 */
                Cliente cliente = new Cliente(
                        id,
                        clienteActualizado.nombre(),
                        clienteActualizado.email(),
                        clienteActualizado.edad(),
                        clienteActualizado.tipoCliente()
                );

                // Reemplazamos el cliente anterior por el actualizado.
                clientes.set(i, cliente);

                return cliente;
            }
        }

        // si terminamos el recorrido sin encontrar el ID,
        // informamos que el cliente no existe.
        throw new ClienteNotFoundException(
                "Cliente no encontrado: " + id
        );
    }

    /**
     * elimina un cliente utilizando su identificador.
     *
     *
     * @param id identificador del cliente que se desea eliminar
     * @throws ClienteNotFoundException si el cliente no existe
     */
    public void eliminarCliente(String id) {

        boolean eliminado = clientes.removeIf(
                cliente -> cliente.id().equals(id)
        );

        /*
         * Si removeIf() devuelve false significa que ningún
         * cliente coincidió con el ID proporcionado.
         */
        if (!eliminado) {

            throw new ClienteNotFoundException(
                    "Cliente no encontrado: " + id
            );
        }
    }

    /**
     * procesa un objeto y determina el comportamiento correspondiente
     * dependiendo de si se trata de un Cliente y de su tipo.
     *
     * @param objeto objeto que se desea procesar
     * @return mensaje correspondiente al tipo de cliente
     */
    public String procesarCliente(Object objeto) {

        /*
         * Pattern Matching:
         *
         * si objeto es un Cliente, Java realiza automáticamente
         * el cast y nos permite utilizar la variable "cliente".
         */
        if (objeto instanceof Cliente cliente) {

            /*
             * Pattern Matching / switch expression sobre el enum
             * TipoCliente.
             */
            return switch (cliente.tipoCliente()) {

                // Los clientes VIP reciben un descuento del 20%.
                case VIP -> "Cliente VIP: descuento del 20%";

                // Los clientes REGULAR no reciben descuento.
                case REGULAR -> "Cliente REGULAR: sin descuento";
            };
        }

        /*
         * El objeto recibido no corresponde a un Cliente.
         */
        return "El objeto no es un cliente";
    }
}
