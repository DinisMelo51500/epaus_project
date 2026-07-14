package isel.sisinf.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "instrumento")
public class Instrumento {

    @Id
    @Column(name = "instrumento_id", length = 12)
    private String id;

    @Column(nullable = false, length = 256)
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mercado", nullable = false)
    private Mercado mercado;

    @OneToOne(mappedBy = "instrumento")
    private DadosFundamentais dadosFundamentais;

    public Instrumento() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Mercado getMercado() {
        return mercado;
    }

    public void setMercado(Mercado mercado) {
        this.mercado = mercado;
    }

    public DadosFundamentais getDadosFundamentais() {
        return dadosFundamentais;
    }

    public void setDadosFundamentais(DadosFundamentais dadosFundamentais) {
        this.dadosFundamentais = dadosFundamentais;
    }
}