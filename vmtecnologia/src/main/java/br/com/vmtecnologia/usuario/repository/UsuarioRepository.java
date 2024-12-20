package br.com.vmtecnologia.usuario.repository;

import br.com.vmtecnologia.usuario.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {

    Page<UsuarioEntity> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
