package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PedidoHandlerRequestDto {

    private String cpfCliente;

    private List<ComboPedidoHandlerRequestDto> produtos = new ArrayList<>();

    public PedidoHandlerRequestDto() {
    }

    /**
     * 
     * @param cpfCliente
     * @param produtos
     */
    public PedidoHandlerRequestDto(String cpfCliente, List<ComboPedidoHandlerRequestDto> produtos) {
        this.cpfCliente = cpfCliente;
        this.produtos = produtos;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public List<ComboPedidoHandlerRequestDto> getProdutos() {
        return produtos;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public void setProdutos(List<ComboPedidoHandlerRequestDto> produtos) {
        this.produtos = produtos;
    }

    

}
