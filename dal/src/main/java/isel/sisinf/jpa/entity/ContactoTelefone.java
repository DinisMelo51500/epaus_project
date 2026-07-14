package isel.sisinf.jpa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contacto_telefone")
public class ContactoTelefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contacto_telefone_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_nif", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 50)
    private String descricao;

    @Column(nullable = false, length = 30)
    private String telefone;

    @Version
    @Column(name = "versao")
    private Integer versao;

    public ContactoTelefone() {}

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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}