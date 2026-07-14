package isel.sisinf.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @Column(length = 20)
    private String nif;

    @Column(name = "cartao_cidadao", nullable = false, unique = true, length = 20)
    private String cartaoCidadao;

    @Column(nullable = false, length = 256)
    private String nome;

    @OneToMany(mappedBy = "cliente")
    private List<Portefolio> portefolios;

    @OneToMany(mappedBy = "cliente")
    private List<ContactoTelefone> telefones;

    @OneToMany(mappedBy = "cliente")
    private List<ContactoEmail> emails;

    @Version
    @Column(name = "versao")
    private Integer versao;

    public Cliente() {}

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCartaoCidadao() {
        return cartaoCidadao;
    }

    public void setCartaoCidadao(String cartaoCidadao) {
        this.cartaoCidadao = cartaoCidadao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getVersao(){
        return versao;
    }

    public void setVersao(Integer versao){
        this.versao = versao;
    }
}