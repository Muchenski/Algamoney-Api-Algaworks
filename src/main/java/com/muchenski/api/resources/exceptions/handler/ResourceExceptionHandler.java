package com.muchenski.api.resources.exceptions.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.muchenski.api.resources.exceptions.ParametroDeRequisicaoInvalidoException;
import com.muchenski.api.resources.exceptions.ViaCepApiException;
import com.muchenski.api.resources.exceptions.enums.TipoErro;
import com.muchenski.api.resources.exceptions.errors.ErroPadrao;
import com.muchenski.api.services.exceptions.RecursoAssociadoNaoEncontradoException;
import com.muchenski.api.services.exceptions.PessoaInativaException;
import com.muchenski.api.services.exceptions.RecursoAssociadoException;
import com.muchenski.api.services.exceptions.RecursoNaoEncontradoException;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ErroPadrao erro = new ErroPadrao(TipoErro.REQUISICAO_COM_ERROS, null, status, obterUri(request));

		ex.getFieldErrors().forEach(exErro -> {
			String mensagem = messageSource.getMessage(exErro, LocaleContextHolder.getLocale());
			erro.adicionarErroDeValidacao(ex.getObjectName(), exErro.getField(), mensagem);
		});

		return handleExceptionInternal(ex, erro, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable root = ex.getRootCause();

		if (root instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedProperty((UnrecognizedPropertyException) root, request);
		}

		return handleExceptionInternal(ex, null, headers, status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedProperty(UnrecognizedPropertyException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		String classeDoRecurso = ex.getReferringClass().getSimpleName();
		String propriedadeDesconhecida = ex.getPropertyName();

		String mensagem = String.format("A propriedade %s não é reconhecida em %s", propriedadeDesconhecida,
				classeDoRecurso);
		ErroPadrao erroPadrao = new ErroPadrao(TipoErro.REQUISICAO_COM_ERROS, mensagem, httpStatus, obterUri(request));
		return ResponseEntity.status(httpStatus).body(erroPadrao);
	}

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<Object> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		ErroPadrao erroPadrao = new ErroPadrao(TipoErro.RECURSO_NAO_ENCONTRADO, ex.getMessage(), httpStatus,
				obterUri(request));
		return ResponseEntity.status(httpStatus).body(erroPadrao);
	}

	@ExceptionHandler(RecursoAssociadoException.class)
	public ResponseEntity<Object> handleRecursoAssociado(RecursoAssociadoException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErroPadrao erroPadrao = new ErroPadrao(TipoErro.RECURSO_ASSOCIADO, ex.getMessage(), httpStatus,
				obterUri(request));
		return ResponseEntity.status(httpStatus).body(erroPadrao);
	}

	@ExceptionHandler(ViaCepApiException.class)
	public ResponseEntity<Object> handleViaCepApi(ViaCepApiException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErroPadrao erroPadrao = new ErroPadrao(TipoErro.WEB_SERVICE_EXTERNO, ex.getMessage(), httpStatus,
				obterUri(request));
		return ResponseEntity.status(httpStatus).body(erroPadrao);
	}

	@ExceptionHandler(PessoaInativaException.class)
	public ResponseEntity<Object> handlePessoaInativa(PessoaInativaException ex, WebRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErroPadrao erroPadrao = new ErroPadrao(TipoErro.PESSOA_INATIVA, ex.getMessage(), httpStatus, obterUri(request));
		return ResponseEntity.status(httpStatus).body(erroPadrao);
	}

	@ExceptionHandler(RecursoAssociadoNaoEncontradoException.class)
	public ResponseEntity<Object> handleEntidadeAssociadaNaoEncontrada(RecursoAssociadoNaoEncontradoException ex,
			WebRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErroPadrao erroPadrao = new ErroPadrao(TipoErro.RECURSO_ASSOCIADO_NAO_ENCONTRADO, ex.getMessage(), httpStatus,
				obterUri(request));
		return ResponseEntity.status(httpStatus).body(erroPadrao);
	}
	
	@ExceptionHandler(ParametroDeRequisicaoInvalidoException.class)
	public ResponseEntity<Object> handleParametroDeRequisicaoInvalido(ParametroDeRequisicaoInvalidoException ex,
			WebRequest request) {
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		ErroPadrao erroPadrao = new ErroPadrao(TipoErro.PARAMETRO_DE_REQUISICAO_INVALIDO, ex.getMessage(), httpStatus,
				obterUri(request));
		return ResponseEntity.status(httpStatus).body(erroPadrao);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		if (body == null) {
			String path = obterUri(request);
			body = new ErroPadrao(TipoErro.REQUISICAO_COM_ERROS, ex.getLocalizedMessage(), status, path);
		}

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Métodos utilitários.

	private HttpServletRequest obterHttpServletRequestDeWebRequest(WebRequest request) {
		return ((ServletWebRequest) request).getRequest();
	}

	private String obterUri(WebRequest request) {
		return obterHttpServletRequestDeWebRequest(request).getRequestURI();
	}
}
