package isel.sisinf.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "posicao")
public class Posicao {

    @EmbeddedId
    private PosicaoId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("portefolio")
    @JoinColumn(name = "portefolio")
    private Portefolio portefolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("instrumentoIsin")
    @JoinColumn(name = "instrumento_isin")
    private Instrumento instrumento;

    @Column(nullable = false, precision = 15, scale = 4)
    private BigDecimal quantidade;

    public Posicao() {}

    public PosicaoId getId() {
        return id;
    }

    public void setId(PosicaoId id) {
        this.id = id;
    }

    public Portefolio getPortefolio() {
        return portefolio;
    }

    public void setPortefolio(Portefolio portefolio) {
        this.portefolio = portefolio;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
}