package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.dto.TopicoDto;
import br.com.alura.forum.form.TopicoForm;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	@Cacheable(value = "topicosCacheId")
	public Page<TopicoDto> findAll(@RequestParam(required = false) String nomeCurso,
			@PageableDefault(page = 0, size = 10, sort = "titulo") Pageable pageable) {
		if (nomeCurso != null) {
			Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, pageable);
			return TopicoDto.converter(topicos);
		}
		Page<Topico> topicos = topicoRepository.findAll(pageable);
		return TopicoDto.converter(topicos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicoDto> find(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			return ResponseEntity.ok(new TopicoDto(topico.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "topicosCacheId", allEntries = true)
	public ResponseEntity<TopicoDto> create(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "topicosCacheId", allEntries = true)
	public ResponseEntity<TopicoDto> update(@PathVariable Long id, @RequestBody @Valid TopicoForm form) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			Topico topicoUpdated = form.update(topico.get(), topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topicoUpdated));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "topicosCacheId", allEntries = true)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
