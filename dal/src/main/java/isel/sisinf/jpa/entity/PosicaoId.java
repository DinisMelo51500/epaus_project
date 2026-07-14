package isel.sisinf.jpa.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PosicaoId implements Serializable {

    private Long portefolio;

    private String instrumentoIsin;

    public PosicaoId() {}

    public PosicaoId(Long portefolio, String instrumentoIsin) {
        this.portefolio = portefolio;
        this.instrumentoIsin = instrumentoIsin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PosicaoId)) return false;
        PosicaoId that = (PosicaoId) o;
        return Objects.equals(portefolio, that.portefolio)
                && Objects.equals(instrumentoIsin, that.instrumentoIsin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(portefolio, instrumentoIsin);
    }
}