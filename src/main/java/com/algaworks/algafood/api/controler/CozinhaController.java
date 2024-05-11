package com.algaworks.algafood.api.controler;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.CozinhaRepresentationXmlModel;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping(value = "/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar() {
		return cozinhaRepository.listar();
	}

	@GetMapping("/{cozinhaId}")
	// @ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
		Cozinha cozinha = cozinhaRepository.buscar(id);
		return Objects.nonNull(cozinha) ? ResponseEntity.ok(cozinha) : ResponseEntity.notFound().build();
	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhaRepresentationXmlModel listarXml() {
		return new CozinhaRepresentationXmlModel(listar());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar (@RequestBody Cozinha cozinha) {
		return cadastroCozinha.salvar(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable("cozinhaId") Long cozinhaId, @RequestBody Cozinha cozinha) {
		Cozinha cozinhaCarregada = cozinhaRepository.buscar(cozinhaId);
		if (Objects.isNull(cozinhaCarregada)) {
			return ResponseEntity.notFound().build();
		} else {
			// cozinhaCarregada.setNome(cozinha.getNome());

			// A terceira propriedade em diante desse método
			// representa as propriedades que você deseja
			// ignorar quando a copia for realizada;
			BeanUtils.copyProperties(cozinha, cozinhaCarregada, "id");
			cozinhaRepository.salvar(cozinhaCarregada);
			return ResponseEntity.ok(cozinhaCarregada);
		}
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable("cozinhaId") Long cozinhaId) {
		try {			
			Cozinha cozinhaCarregada = cozinhaRepository.buscar(cozinhaId);
			if (Objects.isNull(cozinhaCarregada)) {
				return ResponseEntity.notFound().build();
			} else {
				cozinhaRepository.remover(cozinhaCarregada);
				return ResponseEntity.noContent().build();
			}
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
}
