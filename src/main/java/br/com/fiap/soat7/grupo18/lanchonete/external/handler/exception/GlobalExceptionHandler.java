package br.com.fiap.soat7.grupo18.lanchonete.external.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainEntityException;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainUseCaseException;
import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.NotFoundEntityException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({DomainEntityException.class, DomainUseCaseException.class})
    @ResponseBody
    public ResponseEntity<String> handleDomainException(RuntimeException re){
        re.printStackTrace();
        return ResponseEntity.badRequest().body(re.getMessage());
    }

    @ExceptionHandler(NotFoundEntityException.class)
    @ResponseBody
    public ResponseEntity<String> handleNotFoundException(RuntimeException re){
        re.printStackTrace();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleNotFoundException(Exception e){
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(e.getMessage());
    }


}
