package isel.sisinf.jpa.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(
    name = "portefolio",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cliente_nif", "nome"})
    }
)
public class Portefolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portefolio_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_nif", nullable = false)
    private Cliente cliente;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(name = "valor_total", nullable = false)
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "portefolio")
    private List<Posicao> posicoes;

    public Portefolio() {}

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}