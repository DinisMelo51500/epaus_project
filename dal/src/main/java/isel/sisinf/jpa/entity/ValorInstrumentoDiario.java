package isel.sisinf.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "valor_instrumento_diario")
public class ValorInstrumentoDiario {

    @EmbeddedId
    private ValorInstrumentoDiarioId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("instrumentoIsin")
    @JoinColumn(name = "instrumento_isin")
    private Instrumento instrumento;

    @Column(name = "valor_minimo", nullable = false)
    private BigDecimal valorMinimo;

    @Column(name = "valor_maximo", nullable = false)
    private BigDecimal valorMaximo;

    @Column(name = "valor_abertura", nullable = false)
    private BigDecimal valorAbertura;

    @Column(name = "valor_fecho", nullable = false)
    private BigDecimal valorFecho;

    public ValorInstrumentoDiario() {}
}