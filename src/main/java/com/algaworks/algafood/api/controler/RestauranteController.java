package com.algaworks.algafood.api.controler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(id);
		return restaurante.isPresent() ? ResponseEntity.ok(restaurante.get()) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = restauranteService.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}		
	}

	@DeleteMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {
		try {
			Optional<Restaurante> restauranteCarregado = restauranteRepository.findById(restauranteId);
			if (restauranteCarregado.isEmpty()) {
				return ResponseEntity.notFound().build();
			} else {
				restauranteService.excluir(restauranteId);
				return ResponseEntity.noContent().build();
			}
		} catch (DataIntegrityViolationException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		try {
			Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);

			if (restauranteAtual.isPresent()) {
				BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco",
						"dataCadastro", "produtos");

				Restaurante restauranteSalvo = restauranteService.salvar(restauranteAtual.get());
				return ResponseEntity.ok(restauranteSalvo);
			}

			return ResponseEntity.notFound().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, 
			@RequestBody Map<String, Object> camposRequisicao) {
		
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		
		if (restauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		this.merge(camposRequisicao, restauranteAtual.get());
		
		return this.atualizar(restauranteId, restauranteAtual.get());
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteClone = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteClone);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
}
