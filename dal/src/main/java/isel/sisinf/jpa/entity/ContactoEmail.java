package isel.sisinf.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contacto_email")
public class ContactoEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contacto_email_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_nif", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 50)
    private String descricao;

    @Column(nullable = false, length = 254)
    private String email;

    @Version
    @Column(name = "versao")
    private Integer versao;

    public ContactoEmail() {}

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}