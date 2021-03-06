package br.com.alura.forum.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.model.Curso;
import br.com.alura.forum.model.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

public class TopicoForm {

	@NotEmpty
	@NotBlank
	@Length(min = 5)
	private String titulo;
	@NotEmpty
	@NotBlank
	@Length(min = 10)
	private String mensagem;
	@NotEmpty
	@NotBlank
	private String nomeCurso;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public Topico converter(CursoRepository cursoRepository) {
		Curso curso = cursoRepository.findByNome(nomeCurso);
		return new Topico(titulo, mensagem, curso);
	}

	public Topico update(Topico topico, TopicoRepository repository) {
		topico.setTitulo(titulo);
		topico.setMensagem(mensagem);
		return topico;
	}

}
