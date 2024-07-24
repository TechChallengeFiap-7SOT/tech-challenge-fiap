package br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception;

public class DomainUseCaseException extends RuntimeException {

    public DomainUseCaseException(String message){
        super(message);
    }
}
