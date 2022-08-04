package com.jeanjulio.models;

import java.time.LocalDate;

public class Cliente {

	private String nome;
	private LocalDate nascimento;
	private long cpf;
	private int telefone;

	public Cliente(String nome, LocalDate nascimento, long cpf, int telefone) {
		this.nome = nome;
		this.nascimento = nascimento;
		this.cpf = cpf;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getNascimento() {
		return nascimento;
	}

	public void setNascimento(LocalDate nascimento) {
		this.nascimento = nascimento;
	}

	public long getCpf() {
		return cpf;
	}

	public void setCpf(long cpf) {
		this.cpf = cpf;
	}

	public int getTelefone() {
		return telefone;
	}

	public void setTelefone(int telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", nascimento=" + nascimento + ", cpf=" + cpf + ", telefone=" + telefone + "]";
	}

}
