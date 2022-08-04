package com.jeanjulio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.jeanjulio.models.Cliente;

public class DBController {

	// Caminho do banco de dados
	private String caminhoDB = "jdbc:mysql://localhost:3306/tutorial_jdbc";

	// Username

	private String username = "";

	// Senha

	private String password = "";

	private Connection connection;

	// Abre a conexão com banco de dados

	public void abrirConexao() {
		try {
			System.out.println("Abrindo conexão com banco de dados.");
			connection = DriverManager.getConnection(caminhoDB, username, password);
			System.out.println("Conexão realizada com sucesso.");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// Cria as tabelas

	public void criarTabelas() {
		abrirConexao();

		System.out.println("Tentando criar tabelas.");

		String tabelaClientes = "CREATE TABLE IF NOT EXISTS Clientes"
				+ "(nome VARCHAR(255) not null, nascimento DATE, cpf bigint Primary key, telefone int not null)";

		try {
			connection.prepareStatement(tabelaClientes).execute();
			System.out.println("Tabelas criadas com sucesso.");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

	}

	// Fechar conexao com banco de dados

	public void fecharConexao() {

		try {
			if (connection != null) {
				if (!connection.getAutoCommit())
					connection.setAutoCommit(true);

				connection.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Adiciona um cliente ao banco de dados

	public void inserirCliente(Cliente cliente) {

		abrirConexao();

		String inserirCliente = "INSERT INTO Clientes (nome, nascimento, cpf, telefone) VALUES (?,?,?,?)";

		try {

			connection.setAutoCommit(false); // Sem alterações no banco de dados de forma automática

			PreparedStatement pStatement = connection.prepareStatement(inserirCliente);

			pStatement.setString(1, cliente.getNome());
			pStatement.setString(2, cliente.getNascimento().toString());
			pStatement.setLong(3, cliente.getCpf());
			pStatement.setInt(4, cliente.getTelefone());

			if (pStatement.execute()) {
				connection.commit();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

	}

	// Busca um cliente de acordo com o cpf informado

	public Cliente buscarClientePorCPF(long cpf) {

		abrirConexao();

		Cliente clienteResultado = null;

		String sqlBuscar = "SELECT * FROM Clientes WHERE cpf = ?";

		try {
			PreparedStatement pStatement = connection.prepareStatement(sqlBuscar);
			pStatement.setLong(1, cpf);

			ResultSet resultado = pStatement.executeQuery();
			while (resultado.next()) {
				String nome = resultado.getString("nome");
				String nascimento = resultado.getString("nascimento");
				long cpfResultado = resultado.getLong("cpf");
				int telefone = resultado.getInt("telefone");

				clienteResultado = new Cliente(nome, LocalDate.parse(nascimento), cpfResultado, telefone);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}

		return clienteResultado;
	}

	// Atualiza os dados de um cliente

	public boolean atualizarCliente(Cliente cliente) {
		abrirConexao();
		String sqlAtualizar = "UPDATE Clientes set nome = ?, nascimento = ?, telefone = ? WHERE cpf = ?";

		try {
			connection.setAutoCommit(false);
			PreparedStatement pStatement = connection.prepareStatement(sqlAtualizar);
			pStatement.setString(1, cliente.getNome());
			pStatement.setString(2, cliente.getNascimento().toString());
			pStatement.setInt(3, cliente.getTelefone());
			pStatement.setLong(4, cliente.getCpf());

			if (pStatement.executeUpdate() == 1) {
				connection.commit();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			fecharConexao();
		}
		return false;
	}

	// remove o cliente de acordo com o cpf especificado

	public boolean removerPorCPF(long cpf) {
		abrirConexao();
		String sqlRemover = "DELETE FROM Clientes WHERE cpf = ?";

		try {
			connection.setAutoCommit(false);
			PreparedStatement pStatement = connection.prepareStatement(sqlRemover);
			pStatement.setLong(1, cpf);

			if (pStatement.executeUpdate() == 1) {
				connection.commit();
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
}
