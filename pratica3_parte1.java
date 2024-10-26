//Classe Pessoa

package cadastrobd.model;

public class Pessoa {
    protected int id;
    protected String nome, logradouro, cidade, estado, telefone, email;

    public Pessoa() {}

    public Pessoa(int id, String nome, String logradouro, String cidade, 
                  String estado, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone = telefone;
        this.email = email;
    }

    public void exibir() {
        System.out.println("ID: " + id + ", Nome: " + nome);
    }
}


//Classe PessoaFisica

package cadastrobd.model;

public class PessoaFisica extends Pessoa {
    private String cpf;

    public PessoaFisica(int id, String nome, String logradouro, String cidade, 
                        String estado, String telefone, String email, String cpf) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cpf = cpf;
    }

    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CPF: " + cpf);
    }
}

//Classe PessoaJuridica
package cadastrobd.model;

public class PessoaJuridica extends Pessoa {
    private String cnpj;

    public PessoaJuridica(int id, String nome, String logradouro, String cidade, 
                          String estado, String telefone, String email, String cnpj) {
        super(id, nome, logradouro, cidade, estado, telefone, email);
        this.cnpj = cnpj;
    }

    @Override
    public void exibir() {
        super.exibir();
        System.out.println("CNPJ: " + cnpj);
    }
}



//Criação de Classes Utilitárias (Pacote cadastrobd.model.util)
//Classe ConectorBD

package cadastrobd.model.util;

import java.sql.*;

public class ConectorBD {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost:1433;databaseName=loja;encrypt=true;trustServerCertificate=true;",
                "user", "password");
    }

    public static PreparedStatement getPrepared(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }

    public static void close(AutoCloseable obj) {
        try {
            if (obj != null) obj.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//Classe PessoaFisicaDAO

package cadastrobd.model;

import cadastrobd.model.util.ConectorBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO {
    public PessoaFisica getPessoa(int id) throws SQLException {
        String sql = "SELECT * FROM PessoaFisica WHERE id = ?";
        PreparedStatement stmt = ConectorBD.getPrepared(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new PessoaFisica(rs.getInt("id"), rs.getString("nome"), rs.getString("logradouro"),
                    rs.getString("cidade"), rs.getString("estado"), rs.getString("telefone"),
                    rs.getString("email"), rs.getString("cpf"));
        }
        return null;
    }
    
    public List<PessoaFisica> getPessoas() throws SQLException {
        List<PessoaFisica> pessoas = new ArrayList<>();
        String sql = "SELECT * FROM PessoaFisica";
        ResultSet rs = ConectorBD.getPrepared(sql).executeQuery();

        while (rs.next()) {
            pessoas.add(new PessoaFisica(rs.getInt("id"), rs.getString("nome"), rs.getString("logradouro"),
                    rs.getString("cidade"), rs.getString("estado"), rs.getString("telefone"),
                    rs.getString("email"), rs.getString("cpf")));
        }
        return pessoas;
    }
}

//Criação da Classe Principal de Testes (CadastroBDTeste)

package cadastrobd;

import cadastrobd.model.*;
import cadastrobd.model.util.ConectorBD;

public class CadastroBDTeste {
    public static void main(String[] args) {
        try {
            PessoaFisica pessoa = new PessoaFisica(1, "Bernardo Ramos", "Rua A", "Cidade X", 
                                                   "Estado Y", "123456789", "Bernardo@email.com", "123.456.789-00");

            PessoaFisicaDAO dao = new PessoaFisicaDAO();
            dao.incluir(pessoa);
            pessoa.exibir();

            pessoa = dao.getPessoa(1);
            if (pessoa != null) pessoa.exibir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
