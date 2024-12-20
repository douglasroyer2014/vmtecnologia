package br.com.vmtecnologia.usuario.service;

import br.com.vmtecnologia.usuario.dto.UsuarioDTO;
import br.com.vmtecnologia.usuario.entity.UsuarioEntity;
import br.com.vmtecnologia.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioService {

    UsuarioRepository usuarioRepository;
    EmailService emailService;

    /**
     * Cadastra o usuario de acordo com as informações do usuario.
     *
     * @param usuarioDTO informações do usuario.
     */
    @Transactional
    public void cadastro(UsuarioDTO usuarioDTO) {
        usuarioRepository.save(usuarioDTO.convertToEntity());
        emailService.sendEmail(usuarioDTO.getEmail(), "Conta cadastrada", "Conta cadastrada com sucesso!");
    }

    /**
     * Consulta todos os usuario disponivel de acordo com a paginação e caso tenha informado algum nome.
     *
     * @param page pagina.
     * @param size tamanho da pagina.
     * @param nome nome.
     * @return lista de usuarios.
     */
    public Iterable<UsuarioEntity> consultaTodos(int page, int size, String nome) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (nome == null) {
            return usuarioRepository.findAll(pageRequest);
        }
        return usuarioRepository.findByNomeContainingIgnoreCase(nome, pageRequest);
    }

    /**
     * Busca usuario de acordo com o id.
     *
     * @param id identificador único do usuario.
     * @return um {@link UsuarioEntity}.
     */
    public UsuarioEntity consulta(UUID id) {
        return usuarioRepository.findById(id).get();
    }

    /**
     * Altera as informações do usuario de acordo com as iformações do usuario, caso o id seja valido..
     *
     * @param id identificador único.
     * @param usuarioDTO informações do usuario
     * @return resposta do servidor se deu certo ou não encontrado o usuario.
     */
    @Transactional
    public ResponseEntity<String> editar(UUID id, UsuarioDTO usuarioDTO) {
        Optional<UsuarioEntity> entityOptional = usuarioRepository.findById(id);
        if (entityOptional.isEmpty()) {
            return new ResponseEntity<>("Usuario não encontrado", HttpStatus.BAD_REQUEST);
        }
        UsuarioEntity entity = usuarioDTO.convertToEntity();
        entity.setId(id);
        usuarioRepository.save(entity);
        emailService.sendEmail(usuarioDTO.getEmail(), "Conta alterada", "Conta alterada com sucesso!");
        return ResponseEntity.ok("Usuario editado com sucesso!");
    }
}
