package com.lprodrigues.iomoney.resource;

import com.lprodrigues.iomoney.entity.Categoria;
import com.lprodrigues.iomoney.event.ResourceCreatedEvent;
import com.lprodrigues.iomoney.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @GetMapping
    public List<Categoria> listar() {
        return categoriaService.listar();
    }

    @PostMapping
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria categoriaSalva = categoriaService.salvar(categoria);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, categoriaSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscaCategoria(@PathVariable Long codigo) {
        Categoria categoria = categoriaService.buscar(codigo);
        return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Categoria> atualizar(@PathVariable Long codigo, @Valid @RequestBody Categoria categoria) {
        Categoria categoriaSalva = categoriaService.atualizar(codigo, categoria);
        return ResponseEntity.ok(categoriaSalva);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long codigo) {
        categoriaService.remover(codigo);
    }

}
