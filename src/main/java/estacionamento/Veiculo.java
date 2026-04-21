package estacionamento;

import java.time.LocalDateTime;


public class Veiculo {


    private String placa;
    private LocalDateTime horadeEntrada;

    public Veiculo (String placa, LocalDateTime horadeentrada){
     this.placa = placa;
        this.horadeEntrada = horadeentrada;
}
        public String getPlaca() {
            return this.placa;
}
             public LocalDateTime getHoradeentrada() {
                 return this.horadeEntrada;
    }
    @Override
    public String toString() {
        // Formata a data para remover o 'T'
        return "Placa: " + this.placa + ", Entrou em: " + this.horadeEntrada.toString().replace('T', ' ').substring(0, 16);
    }

}
