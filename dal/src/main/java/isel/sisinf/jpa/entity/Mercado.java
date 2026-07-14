package isel.sisinf.jpa.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "mercado")
public class Mercado {

    @Id
    @Column(name = "mercado_id", length = 20)
    private String id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "nome_curto", nullable = false, length = 50)
    private String nomeCurto;

    @OneToMany(mappedBy = "mercado")
    private List<Instrumento> instrumentos;

    public Mercado() {}

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

    public String getNomeCurto() {
        return nomeCurto;
    }

    public void setNomeCurto(String nomeCurto) {
        this.nomeCurto = nomeCurto;
    }
}