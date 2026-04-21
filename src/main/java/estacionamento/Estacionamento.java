package estacionamento;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Estacionamento {

    private static final int vagasTotais = 20;
    private static final double custoprimeiraHora = 12.00;
    private static final double custoHoraadicional = 8.00;

    private final ArrayList<Veiculo> carrosEstacionados;
    private double faturamentoTotal;

    public Estacionamento() {
        this.carrosEstacionados = new ArrayList<>();
        this.faturamentoTotal = 0;
    }
    public Veiculo buscarVeiculo(String placa) {
        for (Veiculo v : carrosEstacionados) {
            if (v.getPlaca().equalsIgnoreCase(placa)) {
                return v;
            }
        }
        return null;
    }
    public int getVagasTotais() {
        return vagasTotais - this.carrosEstacionados.size();
    }
    public double getfaturamentoTotal() {
        return faturamentoTotal;
    }
    public boolean registrarVeiculo(Veiculo veiculo) {
        if(getVagasTotais() > 0) {
        this.carrosEstacionados.add(veiculo);
        return true;
    }
    return false;
        }

        //parte copiada do chat gpt:

    public String registrarSaida(String placa, LocalDateTime horaSaida){

        Veiculo veiculoAsair = buscarVeiculo(placa);

        if (veiculoAsair == null){
            return "Erro: O carro com placa " + placa + " não foi encontrado.";
        }

        long minutos = veiculoAsair.getHoradeentrada().until(horaSaida, ChronoUnit.MINUTES);

        long horas = (long) Math.ceil(minutos / 60.0);
        if (horas == 0) horas = 1;

        double valor = calcularValor(horas);

        faturamentoTotal += valor;
        carrosEstacionados.remove(veiculoAsair);

        return "Saída registrada!\n" +
                "Placa: " + placa + "\n" +
                "Tempo: " + horas + " hora(s)\n" +
                "Valor: R$ " + valor;
    }
//adaptado com chatgpt

    private double calcularValor(long horas){
        if (horas <= 1) {
            return custoprimeiraHora;
        }
        return custoprimeiraHora + (horas - 1) * custoHoraadicional;
    }

public String listarVeiculos(){
    if (carrosEstacionados.isEmpty()){
        return "Nenhum veiculo encontrado.";
    }
    StringBuilder lista = new StringBuilder("--- VEÍCULOS ATUAIS ---\n");
    for (Veiculo v : carrosEstacionados) {
        lista.append("- ").append(v.toString()).append("\n");
    }
    return lista.toString();
}
}









