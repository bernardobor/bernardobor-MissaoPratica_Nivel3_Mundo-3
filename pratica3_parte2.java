//Código Atualizado da Classe CadastroBDTeste

package cadastrobd;

import cadastrobd.model.*;
import cadastrobd.model.util.ConectorBD;
import java.sql.SQLException;
import java.util.Scanner;

public class CadastroBDTeste {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PessoaFisicaDAO pfDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pjDAO = new PessoaJuridicaDAO();

        int opcao;

        do {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("1 - Incluir");
            System.out.println("2 - Alterar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Exibir pelo ID");
            System.out.println("5 - Exibir Todos");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer do scanner

            try {
                switch (opcao) {
                    case 1 -> incluir(scanner, pfDAO, pjDAO);
                    case 2 -> alterar(scanner, pfDAO, pjDAO);
                    case 3 -> excluir(scanner, pfDAO, pjDAO);
                    case 4 -> exibirPorId(scanner, pfDAO, pjDAO);
                    case 5 -> exibirTodos(scanner, pfDAO, pjDAO);
                    case 0 -> System.out.println("Encerrando o sistema...");
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }

        } while (opcao != 0);

        scanner.close();
    }

    private static void incluir(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws SQLException {
        System.out.println("Incluir Pessoa Física (1) ou Jurídica (2)?");
        int tipo = scanner.nextInt();
        scanner.nextLine(); // Limpa o buffer

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        if (tipo == 1) {
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            PessoaFisica pf = new PessoaFisica(0, nome, logradouro, cidade, estado, telefone, email, cpf);
            pfDAO.incluir(pf);
            System.out.println("Pessoa Física incluída com sucesso!");
        } else {
            System.out.print("CNPJ: ");
            String cnpj = scanner.nextLine();
            PessoaJuridica pj = new PessoaJuridica(0, nome, logradouro, cidade, estado, telefone, email, cnpj);
            pjDAO.incluir(pj);
            System.out.println("Pessoa Jurídica incluída com sucesso!");
        }
    }

    private static void alterar(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws SQLException {
        System.out.println("Alterar Pessoa Física (1) ou Jurídica (2)?");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            PessoaFisica pf = pfDAO.getPessoa(id);
            if (pf != null) {
                System.out.println("Dados atuais:");
                pf.exibir();
                System.out.println("Digite os novos dados:");
                incluir(scanner, pfDAO, pjDAO);
                System.out.println("Pessoa Física alterada com sucesso!");
            } else {
                System.out.println("Pessoa Física não encontrada.");
            }
        } else {
            PessoaJuridica pj = pjDAO.getPessoa(id);
            if (pj != null) {
                System.out.println("Dados atuais:");
                pj.exibir();
                System.out.println("Digite os novos dados:");
                incluir(scanner, pfDAO, pjDAO);
                System.out.println("Pessoa Jurídica alterada com sucesso!");
            } else {
                System.out.println("Pessoa Jurídica não encontrada.");
            }
        }
    }

    private static void excluir(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws SQLException {
        System.out.println("Excluir Pessoa Física (1) ou Jurídica (2)?");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            pfDAO.excluir(id);
            System.out.println("Pessoa Física excluída com sucesso!");
        } else {
            pjDAO.excluir(id);
            System.out.println("Pessoa Jurídica excluída com sucesso!");
        }
    }

    private static void exibirPorId(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws SQLException {
        System.out.println("Exibir Pessoa Física (1) ou Jurídica (2)?");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            PessoaFisica pf = pfDAO.getPessoa(id);
            if (pf != null) {
                pf.exibir();
            } else {
                System.out.println("Pessoa Física não encontrada.");
            }
        } else {
            PessoaJuridica pj = pjDAO.getPessoa(id);
            if (pj != null) {
                pj.exibir();
            } else {
                System.out.println("Pessoa Jurídica não encontrada.");
            }
        }
    }

    private static void exibirTodos(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws SQLException {
        System.out.println("Exibir todas as Pessoas Físicas (1) ou Jurídicas (2)?");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        if (tipo == 1) {
            for (PessoaFisica pf : pfDAO.getPessoas()) {
                pf.exibir();
            }
        } else {
            for (PessoaJuridica pj : pjDAO.getPessoas()) {
                pj.exibir();
            }
        }
    }
}
