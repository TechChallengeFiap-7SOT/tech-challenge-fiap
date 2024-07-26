package br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception;

public class NotFoundEntityException extends RuntimeException {

    public NotFoundEntityException(String message){
        super(message);
    }

}
