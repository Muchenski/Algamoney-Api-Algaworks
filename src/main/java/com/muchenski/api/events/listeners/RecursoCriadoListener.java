package com.muchenski.api.events.listeners;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.muchenski.api.domain.IModel;
import com.muchenski.api.events.RecursoCriadoEvent;

@Component
public class RecursoCriadoListener<T extends IModel<ID>, ID> implements ApplicationListener<RecursoCriadoEvent<T, ID>> {

	@Override
	public void onApplicationEvent(RecursoCriadoEvent<T, ID> event) {
		HttpServletResponse response = event.getResponse();
		ID id = event.getId();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		response.setHeader("location", uri.toASCIIString());
	}

}
