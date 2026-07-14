package isel.sisinf.jpa.service;

import isel.sisinf.jpa.repo.PosicaoRepository;
import isel.sisinf.jpa.entity.Posicao;
import isel.sisinf.jpa.entity.ValorPosicao;
import java.util.List;


public class PosicaoService {

    private final PosicaoRepository repo = new PosicaoRepository();

    public List<ValorPosicao> listByClientNif(String nif) {
        return repo.findByClienteNif(nif);
    }
}