package com.algaworks.algafood.api.controler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
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
	public Restaurante buscar(@PathVariable Long id) {
		return restauranteService.buscarOuFalhar(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante) {
		try {
			return restauranteService.salvar(restaurante);
		} catch (CozinhaNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@DeleteMapping("/{restauranteId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId) {
		restauranteService.excluir(restauranteId);
	}

	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
				"produtos");
		try {
			return restauranteService.salvar(restauranteAtual);
		} catch (CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PatchMapping("/{restauranteId}")
	public Restaurante atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> camposRequisicao) {
		Restaurante restauranteAtual = restauranteService.buscarOuFalhar(restauranteId);
		this.merge(camposRequisicao, restauranteAtual);
		return this.atualizar(restauranteId, restauranteAtual);
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
