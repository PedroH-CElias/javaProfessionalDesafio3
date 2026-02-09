package com.devsuperior.desafio3.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.desafio3.dto.ClientDTO;
import com.devsuperior.desafio3.entities.Client;
import com.devsuperior.desafio3.repositories.ClientRepository;
import com.devsuperior.desafio3.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {

		Client client = clientRepository //
				.findById(id) //
				.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado") //
				);

		return new ClientDTO(client);

	}
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAll(Pageable pageable) {
		Page<Client> clients = clientRepository.findAll(pageable);
		return clients.map(client -> new ClientDTO(client));
	}
	
	@Transactional()
	public ClientDTO insert(ClientDTO clientDTO) {
		Client client = copyClientDtoToClient(clientDTO);
		client = clientRepository.save(client);
		
		return new ClientDTO(client);
	}

	private Client copyClientDtoToClient(ClientDTO dto) {
		Client client = new Client();
		client.setName(dto.getName());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setBirthDate(dto.getBirthDate());
		client.setChildren(dto.getChildren());
		
		return client;
		
	}
	
	
	
	

}
