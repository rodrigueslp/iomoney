package com.lprodrigues.iomoney.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class IomoneyExcepetion extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String mensagemRequisicaoInvalida = messageSource.getMessage("recurso.nao-criado", null,
                LocaleContextHolder.getLocale());
        String mensagemTecnica = ex.toString();

        return handleExceptionInternal(ex, new Error(mensagemRequisicaoInvalida, mensagemTecnica), new HttpHeaders(),
                HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                        HttpHeaders headers, HttpStatus status, WebRequest request) {

        String mensagemRequisicaoInvalida = messageSource.getMessage("requisicao.invalida", null,
                LocaleContextHolder.getLocale());
        String mensagemTecnica = ex.getCause().toString();

        return handleExceptionInternal(ex, new Error(mensagemRequisicaoInvalida, mensagemTecnica), headers,
                HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                        HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleExceptionInternal(ex, this.listaDeErros(ex.getBindingResult()), headers,
                HttpStatus.BAD_REQUEST, request);
    }

    private List<Error> listaDeErros(BindingResult bindingResult) {

        List<Error> erros = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String mensagemUsuario = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String mensagemTecnica = fieldError.toString();
            erros.add(new Error(mensagemUsuario, mensagemTecnica));
        }

        return erros;

    }

    public static class Error {
        private String mensagemUsuario;
        private String mensagemTecnica
                ;

        public Error(String mensagemUsuario, String mensagemTecnica
        ) {
            this.mensagemUsuario = mensagemUsuario;
            this.mensagemTecnica
                    = mensagemTecnica
            ;
        }

        public String getMensagemUsuario() {
            return mensagemUsuario;
        }

        public String getMensagemDesenvolvedor() {
            return mensagemTecnica
                    ;
        }

    }

}
