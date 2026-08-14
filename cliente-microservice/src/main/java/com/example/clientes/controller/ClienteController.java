package com.example.clientes.controller;

import com.example.clientes.model.Cliente;
import com.example.clientes.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	 private final ClienteService clienteService;

	    public ClienteController(ClienteService clienteService) {
	        this.clienteService = clienteService;
	    }
	    
	    //POST http://localhost:8080/api/clientes
	    /* enviamos
	     * {
			    "id": "001",
			    "nombre": "Juan Perez",
			    "email": "juan@gmail.com",
			    "edad": 30,
			    "tipoCliente": "VIP"
			}
	     * 
	     * Recibimos
	     * 
	     * {
			    "id": "001",
			    "nombre": "Juan Perez",
			    "email": "juan@gmail.com",
			    "edad": 30,
			    "tipoCliente": "VIP"
			}
	     * */
	    @PostMapping
	    public ResponseEntity<Cliente> crearCliente(
	            @RequestBody Cliente cliente) {

	        return ResponseEntity.ok(
	                clienteService.crearCliente(cliente)
	        );
	    }
	    
	    //GET /api/clientes
	    @GetMapping
	    public ResponseEntity<List<Cliente>> obtenerClientes() {

	        return ResponseEntity.ok(
	                clienteService.obtenerTodos()
	        );
	    }
	    
	    //GET /api/clientes/001
	    @GetMapping("/{id}")
	    public ResponseEntity<Cliente> obtenerCliente(
	            @PathVariable String id) {

	        return ResponseEntity.ok(
	                clienteService.obtenerPorId(id)
	        );
	    }
	    
	    //PUT /api/clientes/001
	    /*
	     * {
			    "id": "001",
			    "nombre": "Juan Perez",
			    "email": "nuevo@gmail.com",
			    "edad": 31,
			    "tipoCliente": "REGULAR"
			}
	     * */
	    @PutMapping("/{id}")
	    public ResponseEntity<Cliente> actualizarCliente(
	            @PathVariable String id,
	            @RequestBody Cliente cliente) {

	        return ResponseEntity.ok(
	                clienteService.actualizarCliente(id, cliente)
	        );
	    }
	    
	    //Respuesta esperada HTTP 204 No Content
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminarCliente(
	            @PathVariable String id) {

	        clienteService.eliminarCliente(id);

	        return ResponseEntity.noContent().build();
	    }
	    
	    //GET /api/clientes/001/descuento
	    // respuesta esperada: Cliente VIP: tiene 20% de descuento
	    @GetMapping("/{id}/descuento")
	    public ResponseEntity<String> obtenerDescuento(
	            @PathVariable String id) {

	        Cliente cliente = clienteService.obtenerPorId(id);

	        return ResponseEntity.ok(
	                clienteService.procesarCliente(cliente)
	        );
	    }
	    
}
