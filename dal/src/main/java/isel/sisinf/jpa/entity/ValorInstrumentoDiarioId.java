package isel.sisinf.jpa.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class ValorInstrumentoDiarioId implements Serializable {

    private String instrumentoIsin;

    private LocalDate data;

    public ValorInstrumentoDiarioId() {}

    public ValorInstrumentoDiarioId(String instrumentoIsin, LocalDate data) {
        this.instrumentoIsin = instrumentoIsin;
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValorInstrumentoDiarioId)) return false;
        ValorInstrumentoDiarioId that = (ValorInstrumentoDiarioId) o;
        return Objects.equals(instrumentoIsin, that.instrumentoIsin)
                && Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instrumentoIsin, data);
    }
}