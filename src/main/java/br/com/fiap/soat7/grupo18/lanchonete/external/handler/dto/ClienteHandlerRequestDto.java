package br.com.fiap.soat7.grupo18.lanchonete.external.handler.dto;

public class ClienteHandlerRequestDto {

    private String cpf;

    private String nome;

    private String email;

    

    public ClienteHandlerRequestDto() {
    }

    /**
     * 
     * @param cpf
     * @param nome
     * @param email
     */
    public ClienteHandlerRequestDto(String cpf, String nome, String email) {
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

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
}
