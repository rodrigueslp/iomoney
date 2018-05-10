package com.lprodrigues.iomoney.event.listener;

import com.lprodrigues.iomoney.event.ResourceCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@Component
public class ResourceCreatedListener implements ApplicationListener<ResourceCreatedEvent> {

    @Override
    public void onApplicationEvent(ResourceCreatedEvent resourceCreatedEvent) {

        HttpServletResponse response = resourceCreatedEvent.getResponse();
        Long codigo = resourceCreatedEvent.getCodigo();

        addHeaderLocation(response, codigo);
    }

    private void addHeaderLocation(HttpServletResponse response, Long codigo) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
                .buildAndExpand(codigo).toUri();
        response.setHeader("Location", uri.toASCIIString());
    }
}
