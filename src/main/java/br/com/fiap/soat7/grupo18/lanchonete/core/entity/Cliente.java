package br.com.fiap.soat7.grupo18.lanchonete.core.entity;

import java.util.Optional;

import br.com.fiap.soat7.grupo18.lanchonete.external.infra.exception.DomainEntityException;

public class Cliente {

    private static final int LENGTH_CPF = 11;
    private static final String _11_DIGITS_REGEX = "^[0-9]{11}$";

    private final String cpf;

    private final String nome;

    private final String email;

    /**
     * 
     * @param cpf
     * @param nome
     * @param email
     */
    public Cliente(String cpf, String nome, String email) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;

        if (Optional.ofNullable(cpf).orElse("").isBlank() || cpf.length() != LENGTH_CPF || !cpf.matches(_11_DIGITS_REGEX)){
            throw new DomainEntityException("CPF não informado ou inválido");
        }

        if (Optional.ofNullable(nome).orElse("").isBlank()){
            throw new DomainEntityException("Nome do cliente não informado ou inválido");
        }

        if (Optional.ofNullable(email).orElse("").isBlank()){
            throw new DomainEntityException("Email do cliente não informado ou inválido");
        }

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
