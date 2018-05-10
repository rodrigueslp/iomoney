package com.lprodrigues.iomoney.service;

import com.lprodrigues.iomoney.entity.Categoria;
import com.lprodrigues.iomoney.repository.CategoriaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    public Categoria salvar(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Categoria buscar(Long codigo) {
        return categoriaRepository.findOne(codigo);
    }

    public Categoria atualizar(Long codigo, Categoria novaCategoria) {
        Categoria categoria = categoriaRepository.findOne(codigo);
        if (categoria == null) throw new EmptyResultDataAccessException(1);

        BeanUtils.copyProperties(novaCategoria, categoria, "codigo");
        return categoriaRepository.save(categoria);
    }

    public void remover(Long codigo) {
        categoriaRepository.delete(codigo);
    }

}
