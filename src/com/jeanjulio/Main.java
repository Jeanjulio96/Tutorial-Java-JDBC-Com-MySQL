package com.jeanjulio;

import java.time.LocalDate;

import com.jeanjulio.models.Cliente;

public class Main {
	
	public static void main(String[] args) {
		
		System.out.println("Iniciando aplicação");
		
		DBController db = new DBController();
		/*db.abrirConexao();
		db.criarTabelas();*/
		
		/*Cliente c1 = new Cliente("Nome 1", LocalDate.now(), 12345678911l, 1112344678);
		db.inserirCliente(c1);*/
		
		// Busca o cliente no banco
		Cliente clienteDaConsulta = db.buscarClientePorCPF(12345678911l);
		System.out.println("Resultado da consulta: " + clienteDaConsulta);
		
		// Atualiza as informações do cliente
		
		clienteDaConsulta.setNascimento(LocalDate.of(2005,  05, 05));
		clienteDaConsulta.setTelefone(963542187);
		
		boolean resultado = db.atualizarCliente(clienteDaConsulta);	
		System.out.println("Resultado da atualização " + resultado);
		
		boolean resultadoRemocao = db.removerPorCPF(12345678911l);
		System.out.println("Resultado da remoção " + resultadoRemocao);
		
	}

}
