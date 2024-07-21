package br.com.fiap.soat7.grupo18.lanchonete.infrastructure.exception;

public class DomainEntityException extends RuntimeException {

    public DomainEntityException(String message){
        super(message);
    }

}
