package estacionamento;

import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    private static Estacionamento estacionamento = new Estacionamento();
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static void main(String[] args) {

        int opcao = -1; // inicializado para evitar erro de variável não atribuída

        do {
            System.out.println("\n===== ESTACIONAMENTO =====");
            System.out.println("1 - Entrada de veículo");
            System.out.println("2 - Saída de veículo");
            System.out.println("3 - Vagas disponíveis");
            System.out.println("4 - Listar veículos");
            System.out.println("5 - Buscar por placa");
            System.out.println("6 - Faturamento total");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Digite apenas números.");
                scanner.nextLine();
                continue;
            }

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    registrarEntrada();
                    break;
                case 2:
                    registrarSaida();
                    break;
                case 3:
                    System.out.println("Vagas livres: " + estacionamento.getVagasTotais());
                    break;
                case 4:
                    System.out.println(estacionamento.listarVeiculos());
                    break;
                case 5:
                    buscarVeiculo();
                    break;
                case 6:
                    System.out.printf("Faturamento total: R$ %.2f\n", estacionamento.getfaturamentoTotal());
                    break;
                case 0:
                    System.out.println("Encerrando sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private static void registrarEntrada() {

        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().toUpperCase();

        if (estacionamento.buscarVeiculo(placa) != null) {
            System.out.println("⚠️ Este veículo já está estacionado.");
            return;
        }

        System.out.println("1 - Usar hora atual");
        System.out.println("2 - Informar manualmente");
        System.out.print("Escolha: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida.");
            scanner.nextLine();
            return;
        }

        int opcaoHora = scanner.nextInt();
        scanner.nextLine();

        LocalDateTime entrada;

        if (opcaoHora == 1) {
            entrada = LocalDateTime.now();
        } else if (opcaoHora == 2) {
            System.out.print("Digite (dd/MM/yyyy HH:mm): ");
            String texto = scanner.nextLine();
            try {
                entrada = LocalDateTime.parse(texto, FORMATO);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido.");
                return;
            }
        } else {
            System.out.println("Opção inválida.");
            return;
        }

        Veiculo v = new Veiculo(placa, entrada);

        if (estacionamento.registrarVeiculo(v)) {
            System.out.println("✅ Entrada registrada com sucesso.");
        } else {
            System.out.println("🚫 Estacionamento lotado!");
        }
    }

    private static void registrarSaida() {

        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().toUpperCase();

        String resultado = estacionamento.registrarSaida(placa, LocalDateTime.now());
        System.out.println("\n--- RECIBO ---");
        System.out.println(resultado);
        System.out.println("-------------");
    }

    private static void buscarVeiculo() {

        System.out.print("Placa: ");
        String placa = scanner.nextLine().toUpperCase();

        Veiculo v = estacionamento.buscarVeiculo(placa);

        if (v != null) {
            System.out.println("✅ Encontrado:");
            System.out.println(v);
        } else {
            System.out.println("❌ Veículo não está no estacionamento.");
        }
    }
}
