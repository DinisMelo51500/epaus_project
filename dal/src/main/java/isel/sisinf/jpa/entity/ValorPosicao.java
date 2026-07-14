package isel.sisinf.jpa.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "valor_posicao")
public class ValorPosicao {

    @EmbeddedId
    private ValorPosicaoId id;

    @Column(nullable = false, precision = 15, scale = 4)
    private BigDecimal quantidade;

    @Column(name = "valor_actual", nullable = false)
    private BigDecimal valorActual;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    public ValorPosicao() {}

    public ValorPosicaoId getId() {
        return id;
    }

    public void setId(ValorPosicaoId id) {
        this.id = id;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValorActual() {
        return valorActual;
    }

    public void setValorActual(BigDecimal valorActual) {
        this.valorActual = valorActual;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}