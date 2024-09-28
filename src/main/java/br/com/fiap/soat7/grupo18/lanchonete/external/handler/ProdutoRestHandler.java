package br.com.fiap.soat7.grupo18.lanchonete.external.handler;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ProdutoHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.ProdutoHandlerResponseDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.service.ProdutoRestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/lanchonete/v1/produtos")
@Tag(name = "Produtos Weeeee", description = "Produtos Weeeee")
public class ProdutoRestHandler {

    private ProdutoRestService produtoRestService;

    public ProdutoRestHandler(ProdutoRestService produtoRestService) {
        this.produtoRestService = produtoRestService;
    }

    @PostMapping
    @Operation(description = "Cadastra um novo produto")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ProdutoHandlerResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<ProdutoHandlerResponseDto> createProduto(@RequestBody ProdutoHandlerRequestDto produtoDto) {
        var savedProduto = produtoRestService.save(produtoDto);
        return ResponseEntity.ok(savedProduto);
    }

    @GetMapping("/{idProduto}")
    @Operation(description = "Consulta um produto por ID, trazendo o registro mesmo que esteja inativo")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ProdutoHandlerResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<ProdutoHandlerResponseDto> getProdutoById(@PathVariable String idProduto) {
        return ResponseEntity.ok(produtoRestService.findByIdProduto(idProduto));
    }

    @GetMapping("/categoria/{idCategoria}")
    @Operation(description = "Lista todos os produtos ativos por categoria. Para visualizar as categorias disponíveis, consulte o endpoint /categorias")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ProdutoHandlerResponseDto.class)))
    public ResponseEntity<List<ProdutoHandlerResponseDto>> getProdutosByCategoria(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(produtoRestService.findByCategoria(idCategoria));
    }

    @GetMapping
    @Operation(description = "Lista todos os produtos ativos")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = ProdutoHandlerResponseDto.class)))
    public ResponseEntity<List<ProdutoHandlerResponseDto>> getAllProdutos() {
        List<ProdutoHandlerResponseDto> produtos = produtoRestService.findAllByAtivoTrue();
        return ResponseEntity.ok(produtos);
    }

    @DeleteMapping("/{idProduto}")
    @Operation(description = "Efetua a exclusão (lógica) de um produto por ID")
    @ApiResponse(responseCode = "200", description = "Sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<Void> deleteProdutoById(@PathVariable String idProduto) {
        produtoRestService.delete(idProduto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idProduto}")
    @Operation(description = "Atualiza um produto ativo.")
    @ApiResponse(responseCode = "200", description = "Sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<ProdutoHandlerResponseDto> updateProduto(@PathVariable String idProduto, @RequestBody ProdutoHandlerRequestDto produtoDto) {
        ProdutoHandlerResponseDto updatedProduto = produtoRestService.update(idProduto, produtoDto);
        return ResponseEntity.ok(updatedProduto);
    }

}
