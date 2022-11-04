package com.algaworks.algalogapi.api.controller;

import com.algaworks.algalogapi.api.assembler.ClienteAssembler;
import com.algaworks.algalogapi.api.model.ClienteModel;
import com.algaworks.algalogapi.api.model.input.ClienteInput;
import com.algaworks.algalogapi.domain.model.Cliente;
import com.algaworks.algalogapi.domain.repository.ClienteRepository;
import com.algaworks.algalogapi.domain.service.CatalogoClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private ClienteRepository clienteRepository;
    private CatalogoClienteService catalogoClienteService;
    private ClienteAssembler clienteAssembler;

    @GetMapping
    public List<ClienteModel> listar() {
        return clienteAssembler.toCollectionModel(clienteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteModel> buscar(@PathVariable Long id){
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok(clienteAssembler.toModel(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteModel adicionar(@Valid @RequestBody ClienteInput clienteInput){
        Cliente novoCliente = clienteAssembler.toEntity(clienteInput);
        Cliente salvarCliente = catalogoClienteService.salvar(novoCliente);
        return clienteAssembler.toModel(salvarCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id,@Valid @RequestBody Cliente cliente){
        if(!clienteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        cliente.setId(id);
        cliente = catalogoClienteService.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id){
        if(!clienteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        catalogoClienteService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}