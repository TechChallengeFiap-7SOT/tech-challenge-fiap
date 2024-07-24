package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

public class ClienteHandlerResponseDto {

    private final String cpf;

    private final String nome;

    private final String email;

    /**
     * 
     * @param cpf
     * @param nome
     * @param email
     */
    public ClienteHandlerResponseDto(String cpf, String nome, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

}
