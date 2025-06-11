package br.com.tbt.lactino.repository;

import br.com.tbt.lactino.model.Notificacao;
import br.com.tbt.lactino.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUsuario(Usuario usuario);
}
