package isel.sisinf.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contacto_cliente")
public class ContactoCliente {

    @Id
    private String nif;

    @Column(name = "carta_cidadao")
    private String cartaCidadao;

    private String nome;

    @Column(name = "tipo_contacto")
    private String tipoContacto;

    private String contacto;

    private String descricao;

    public ContactoCliente() {}

    public ContactoCliente(String nif,
                           String cartaCidadao,
                           String nome,
                           String tipoContacto,
                           String contacto,
                           String descricao) {
        this.nif = nif;
        this.cartaCidadao = cartaCidadao;
        this.nome = nome;
        this.tipoContacto = tipoContacto;
        this.contacto = contacto;
        this.descricao = descricao;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCartaoCidadao() {
        return cartaCidadao;
    }

    public void setCartaoCidadao(String cartaoCidadao) {
        this.cartaCidadao = cartaoCidadao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}