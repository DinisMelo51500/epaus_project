package isel.sisinf.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dados_fundamentais")
public class DadosFundamentais {

    @Id
    @OneToOne
    @JoinColumn(name = "instrumento_isin")
    private Instrumento instrumento;

    @Column(name = "variacao_diaria", nullable = false)
    private BigDecimal variacaoDiaria;

    @Column(name = "valor_actual", nullable = false)
    private BigDecimal valorActual;

    @Column(name = "media_6_meses", nullable = false)
    private BigDecimal media6Meses;

    @Column(name = "variacao_6_meses", nullable = false)
    private BigDecimal variacao6Meses;

    @Column(name = "percentagem_variacao_diaria", nullable = false)
    private BigDecimal percentagemVariacaoDiaria;

    @Column(name = "percentagem_variacao_6_meses", nullable = false)
    private BigDecimal percentagemVariacao6Meses;

    public DadosFundamentais() {}

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }
}