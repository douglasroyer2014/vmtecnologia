package br.com.vmtecnologia.usuario.dto;

import br.com.vmtecnologia.usuario.entity.UsuarioEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UsuarioDTOTest {
    @Test
    void deveConverterUsuarioDTOParaUsuarioEntity() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setEmail("joao@example.com");
        usuarioDTO.setSenha("senha123");

        UsuarioEntity usuarioEntity = usuarioDTO.convertToEntity();

        assertNotNull(usuarioEntity);
        assertEquals("João", usuarioEntity.getNome());
        assertEquals("joao@example.com", usuarioEntity.getEmail());
        assertEquals("senha123", usuarioEntity.getSenha());
    }
}