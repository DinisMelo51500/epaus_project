package isel.sisinf.jpa.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ValorPosicaoId implements Serializable {

    private Long portefolio;

    @Column(name = "instrumento_isin")
    private String instrumentoIsin;

    public ValorPosicaoId() {}

    public ValorPosicaoId(Long portefolio, String instrumentoIsin) {
        this.portefolio = portefolio;
        this.instrumentoIsin = instrumentoIsin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValorPosicaoId)) return false;
        ValorPosicaoId that = (ValorPosicaoId) o;
        return Objects.equals(portefolio, that.portefolio)
                && Objects.equals(instrumentoIsin, that.instrumentoIsin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portefolio, instrumentoIsin);
    }

    public String getInstrumentoIsin() {
        return instrumentoIsin;
    }

    public void setInstrumentoIsin(String instrumentoIsin) {
        this.instrumentoIsin = instrumentoIsin;
    }

    public Long getPortefolio() {
        return portefolio;
    }

    public void setPortefolio(Long portefolio) {
        this.portefolio = portefolio;
    }
}