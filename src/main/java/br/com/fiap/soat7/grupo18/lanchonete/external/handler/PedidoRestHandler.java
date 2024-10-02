package br.com.fiap.soat7.grupo18.lanchonete.external.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import br.com.fiap.soat7.grupo18.lanchonete.core.type.StatusPgtoType;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.PedidoHandlerRequestDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto.PedidoHandlerResponseDto;
import br.com.fiap.soat7.grupo18.lanchonete.external.handler.service.PedidoRestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/lanchonete/v1/pedidos")
@Tag(name = "Pedidos - Demonstração Deploy", description = "Pedidos de clientes")
public class PedidoRestHandler {

    private PedidoRestService pedidoService;

    public PedidoRestHandler(PedidoRestService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping(value = "/{idPedido}")
    @Operation(description = "Consulta um pedido por ID")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = PedidoHandlerResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<PedidoHandlerResponseDto> findById(@PathVariable String idPedido) {
        return ResponseEntity.ok(pedidoService.findById(idPedido));
    }

    @GetMapping(value = "/{idPedido}/qr", produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(description = "Consulta o QR-CODE para pagamento do pedido")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "image/png" , schema = @Schema(type = "string", format = "binary")))
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<byte[]> getQrCodePayment(@PathVariable String idPedido) throws WriterException, IOException{
        final int QR_SIZE = 300;
        var pedido = pedidoService.findById(idPedido);
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMtx = qrWriter.encode(pedido.getIdTransacaoPagamento(), BarcodeFormat.QR_CODE, QR_SIZE, QR_SIZE);
        ByteArrayOutputStream imageOutPutStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMtx, "PNG", imageOutPutStream);
        byte[] imageBytes = imageOutPutStream.toByteArray();
        return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"qrcode.png\"")
                    .body(imageBytes);

    }

    @PostMapping
    @Operation(description = "Cria um novo pedido")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = PedidoHandlerRequestDto.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    public ResponseEntity<PedidoHandlerResponseDto> save(@RequestBody PedidoHandlerRequestDto pedidoDto) {
        return ResponseEntity.ok(pedidoService.save(pedidoDto));
    }

    @PutMapping("/{idPedido}/atualiza-para-status/{novoStatus}")
    @Operation(description = "Atualiza o status do pedido")
    @ApiResponse(responseCode = "201", description = "Sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<String> updateStatus(@PathVariable String idPedido, @PathVariable String novoStatus) {
        pedidoService.updateStatus(idPedido, novoStatus);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("OK");
    }

    @PutMapping("/{idPedido}/atualiza-para-status-pgto/{novoStatus}")
    @Operation(description = "Atualiza o status de pagamento do pedido")
    @ApiResponse(responseCode = "201", description = "Sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<String> updateStatusPgto(@PathVariable String idPedido, @PathVariable String novoStatus) {
        pedidoService.updateStatusPgto(idPedido, novoStatus);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("OK");
    }

    @GetMapping("/status-pgto/{idPedido}")
    @Operation(description = "Consulta o status de pagamento do pedido")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = StatusPgtoType.class)))
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    public ResponseEntity<StatusPgtoType> getStatusPgto(@PathVariable String idPedido) {
        return ResponseEntity.ok(pedidoService.getStatusPgto(idPedido));
    }

    @GetMapping
    @Operation(description = "Lista todos os pedidos")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = PedidoHandlerResponseDto.class)))
    public ResponseEntity<List<PedidoHandlerResponseDto>> findAll() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/lista-fila")
    @Operation(description = "Lista a fila de pedidos")
    @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(mediaType = "application/json" , schema = @Schema(implementation = PedidoHandlerResponseDto.class)))
    public ResponseEntity<List<PedidoHandlerResponseDto>> findNaoFinalizadosOuCancelados() {
        return ResponseEntity.ok(pedidoService.findNaoFinalizadosOuCancelados());
    }

}
