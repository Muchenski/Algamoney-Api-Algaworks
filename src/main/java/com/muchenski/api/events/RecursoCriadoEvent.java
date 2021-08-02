package com.muchenski.api.events;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import com.muchenski.api.domain.IModel;

public class RecursoCriadoEvent<T extends IModel<ID>, ID> extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private ID id;

	private HttpServletResponse response;

	public RecursoCriadoEvent(T source, HttpServletResponse response, ID id) {
		super(source);
		this.id = id;
		this.response = response;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

}
