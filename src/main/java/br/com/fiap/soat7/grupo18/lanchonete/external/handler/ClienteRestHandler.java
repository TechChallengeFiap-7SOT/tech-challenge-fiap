package br.com.fiap.soat7.grupo18.lanchonete.external.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ClienteHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ClienteHandlerResponseDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.service.ClienteRestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/lanchonete/v1/clientes")
@Tag(name = "Clientes", description = "Clientes")
public class ClienteRestHandler {

    private ClienteRestService clienteRestService;

    public ClienteRestHandler(ClienteRestService clienteRestService) {
        this.clienteRestService = clienteRestService;
    }

    @GetMapping(value = "/{cpfCliente}", produces = {"application/json"})
    @Operation(description = "Consulta o cliente por CPF")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ClienteHandlerResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Não encontrado")
    public ResponseEntity<ClienteHandlerResponseDto> getClienteByCpf(@PathVariable String cpfCliente) {
        ClienteHandlerResponseDto clienteHandlerResponseDto = clienteRestService.findByCpfCliente(cpfCliente);
        if (clienteHandlerResponseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteHandlerResponseDto);
    }

    @PostMapping
    @Operation(description = "Cadastra um novo cliente")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ClienteHandlerResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<ClienteHandlerResponseDto> createCliente(@Valid @RequestBody ClienteHandlerRequestDto clienteHandlerRequestDto) {
        ClienteHandlerResponseDto savedClienteHandlerResponseDto = clienteRestService.save(clienteHandlerRequestDto);
        return ResponseEntity.ok(savedClienteHandlerResponseDto);
    }

    
}
