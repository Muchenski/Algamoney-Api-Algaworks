package com.muchenski.api.resources.exceptions.errors;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.muchenski.api.resources.exceptions.enums.TipoErro;

@JsonInclude(Include.NON_EMPTY)
public class ErroPadrao implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tipoErro;
	private String mensagem;
	private String razao;
	private Integer codigo;
	private String path;
	private Instant instante;

	private List<HashMap<String, List<HashMap<String, List<String>>>>> erros = new ArrayList<HashMap<String, List<HashMap<String, List<String>>>>>();

	public ErroPadrao() {
	}

	public ErroPadrao(TipoErro tipoErro, String mensagem, HttpStatus status, String path) {
		this.tipoErro = tipoErro.getDescricao();
		this.mensagem = mensagem;
		this.razao = status.getReasonPhrase();
		this.codigo = status.value();
		this.path = path;
		this.instante = Instant.now();
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Instant getInstante() {
		return instante;
	}

	public void setInstante(Instant instante) {
		this.instante = instante;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getTipoErro() {
		return tipoErro;
	}

	public void setTipoErro(TipoErro tipoErro) {
		this.tipoErro = tipoErro.getDescricao();
	}

	public List<HashMap<String, List<HashMap<String, List<String>>>>> getErros() {
		return erros;
	}

	public void setErros(List<HashMap<String, List<HashMap<String, List<String>>>>> erros) {
		this.erros = erros;
	}

	public void adicionarErroDeValidacao(String classe, String campo, String mensagem) {

		Boolean contemClasse = null;
		Boolean contemCampo = null;

		contemClasse = this.erros.stream().anyMatch(erro -> erro.containsKey(classe));

		if (!contemClasse) {
			HashMap<String, List<HashMap<String, List<String>>>> classeWrapper = new HashMap<String, List<HashMap<String, List<String>>>>();
			List<HashMap<String, List<String>>> camposWrapper = new ArrayList<HashMap<String, List<String>>>();
			classeWrapper.put(classe, camposWrapper);
			this.erros.add(classeWrapper);
		}

		contemCampo = this.erros.stream().anyMatch(erro -> {
			return erro.get(classe).stream().anyMatch(campoWrapper -> campoWrapper.containsKey(campo));
		});

		if (!contemCampo) {
			HashMap<String, List<String>> campoWrapper = new HashMap<String, List<String>>();
			List<String> mensagens = new ArrayList<String>();
			campoWrapper.put(campo, mensagens);
			this.erros.forEach(erro -> {
				if (erro.get(classe) != null) {
					erro.get(classe).add(campoWrapper);
					return;
				}
			});
		}

		this.erros.forEach(erro -> {
			if (erro.get(classe) != null) {
				erro.get(classe).forEach(cls -> {
					if (cls.get(campo) != null) {
						cls.get(campo).add(mensagem);
					}
				});
			}
		});

	}
}
