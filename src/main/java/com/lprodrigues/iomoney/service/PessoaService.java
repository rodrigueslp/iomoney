package com.lprodrigues.iomoney.service;

import com.lprodrigues.iomoney.entity.Pessoa;
import com.lprodrigues.iomoney.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    public Pessoa salvar(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa buscar(Long codigo) {
        return pessoaRepository.findOne(codigo);
    }

    public Pessoa atualizar(Long codigo, Pessoa novaPessoa) {
        Pessoa pessoa = pessoaRepository.findOne(codigo);
        if (pessoa == null) throw new EmptyResultDataAccessException(1);

        BeanUtils.copyProperties(novaPessoa, pessoa, "codigo");
        return pessoaRepository.save(pessoa);
    }

    public void remover(Long codigo) {
        pessoaRepository.delete(codigo);
    }

}
