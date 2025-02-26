package com.filmes.avaliador.service;

import com.filmes.avaliador.exception.ConflitoException;
import com.filmes.avaliador.exception.NotFoundException;
import com.filmes.avaliador.model.Client;
import com.filmes.avaliador.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final PasswordEncoder encoder;

    private final ClientRepository  repository;

    public Client salvar(Client client){

        Optional<Client> clientBuscado = repository.findByClientId(client.getClientId());

        if (clientBuscado.isPresent()){
            throw new ConflitoException("Já existe um client com esse clientId");
        }

        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return repository.save(client);

    }

    public Client obterPorClientId(String clientId){

        Optional<Client> clientBuscado = repository.findByClientId(clientId);

        if(clientBuscado.isEmpty()){
            throw new NotFoundException("Client não encontrado");
        }

        return clientBuscado.get();
    }

}
