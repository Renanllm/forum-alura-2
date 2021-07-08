package br.com.alura.forum.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.alura.forum.model.Curso;

@SpringBootTest
@ActiveProfiles("test")
public class CursoRepositoryTest {
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Test
	public void shouldGetCourseByName() {
		String nomeCurso = "Spring Boot";
		createCourse(nomeCurso);
		
		Curso curso = cursoRepository.findByNome(nomeCurso);
		assertNotNull(curso);
		assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	public void shouldNotGetCourseByName() {
		String nomeCurso = "Jpa";
		Curso curso = cursoRepository.findByNome(nomeCurso);
		assertNull(curso);
	}
	
	void createCourse(String name) {
		Curso spring = new Curso();
		spring.setNome(name);
		cursoRepository.save(spring);
	}

}
