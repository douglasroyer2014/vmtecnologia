package br.com.vmtecnologia.usuario.service;

import br.com.vmtecnologia.usuario.dto.UsuarioDTO;
import br.com.vmtecnologia.usuario.entity.UsuarioEntity;
import br.com.vmtecnologia.usuario.repository.UsuarioRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import java.awt.print.Pageable;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveSalvarUsuarioEEnviarEmail() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("João");
        usuarioDTO.setEmail("joao@example.com");
        usuarioDTO.setSenha("senha123");

        usuarioService.cadastro(usuarioDTO);

        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
        verify(emailService, times(1)).sendEmail(
                eq("joao@example.com"),
                eq("Conta cadastrada"),
                eq("Conta cadastrada com sucesso!")
        );
    }
    @Test
    void deveConsultarTodosUsuariosSemNome() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(page, size);

        when(usuarioRepository.findAll(pageRequest)).thenReturn(Page.empty());

        Iterable<UsuarioEntity> result = usuarioService.consultaTodos(page, size, null);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).findAll(PageRequest.of(page, size));
        verify(usuarioRepository, never()).findByNomeContainingIgnoreCase(anyString(), any());
    }

    @Test
    void deveConsultarUsuariosPorNome() {
        int page = 0;
        int size = 10;
        String nome = "João";
        PageRequest pageRequest = PageRequest.of(page, size);

        when(usuarioRepository.findByNomeContainingIgnoreCase(nome, pageRequest)).thenReturn(Page.empty());

        Iterable<UsuarioEntity> result = usuarioService.consultaTodos(page, size, nome);

        assertNotNull(result);
        verify(usuarioRepository, times(1)).findByNomeContainingIgnoreCase(nome, PageRequest.of(page, size));
        verify(usuarioRepository, never()).findAll(pageRequest);
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        UUID id = UUID.randomUUID();
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        UsuarioEntity result = usuarioService.consulta(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExistir() {
        UUID id = UUID.randomUUID();

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> usuarioService.consulta(id));
        verify(usuarioRepository, times(1)).findById(id);
    }

    @Test
    void deveEditarUsuarioComSucesso() {
        UUID id = UUID.randomUUID();
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome("Novo Nome");
        usuarioDTO.setEmail("novoemail@example.com");
        usuarioDTO.setSenha("novaSenha");

        UsuarioEntity existingEntity = new UsuarioEntity();
        existingEntity.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(existingEntity));

        ResponseEntity<String> response = usuarioService.editar(id, usuarioDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario editado com sucesso!", response.getBody());
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, times(1)).save(any(UsuarioEntity.class));
        verify(emailService, times(1)).sendEmail(
                eq("novoemail@example.com"),
                eq("Conta alterada"),
                eq("Conta alterada com sucesso!")
        );
    }

    @Test
    void deveRetornarErroQuandoUsuarioNaoExistir() {
        UUID id = UUID.randomUUID();
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<String> response = usuarioService.editar(id, usuarioDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuario não encontrado", response.getBody());
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, never()).save(any(UsuarioEntity.class));
        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
    }
}