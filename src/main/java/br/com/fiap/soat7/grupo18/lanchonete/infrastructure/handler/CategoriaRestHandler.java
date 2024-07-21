package br.com.fiap.soat7.grupo18.lanchonete.infrastructure.handler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/lanchonete/v1/categorias")
@Tag(name = "Categorias", description = "Categorias de produtos")
public class CategoriaRestHandler {

    //TODO seguir implementando os gateways e controllers para categoria...

}
