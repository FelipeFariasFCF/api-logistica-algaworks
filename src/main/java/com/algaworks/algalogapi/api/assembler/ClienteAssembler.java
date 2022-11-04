package com.algaworks.algalogapi.api.assembler;

import com.algaworks.algalogapi.api.model.ClienteModel;
import com.algaworks.algalogapi.api.model.input.ClienteInput;
import com.algaworks.algalogapi.domain.model.Cliente;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class ClienteAssembler {

    private ModelMapper modelMapper;

    public ClienteModel toModel(Cliente cliente) {
        return modelMapper.map(cliente, ClienteModel.class);
    }

    public Cliente toEntity(ClienteInput clienteInput) {
        return modelMapper.map(clienteInput, Cliente.class);
    }

    public List<ClienteModel> toCollectionModel(List<Cliente> clientes) {
        return clientes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}