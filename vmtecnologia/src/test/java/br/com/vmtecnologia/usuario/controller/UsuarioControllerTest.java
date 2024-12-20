package br.com.vmtecnologia.usuario.controller;

import br.com.vmtecnologia.usuario.dto.UsuarioDTO;
import br.com.vmtecnologia.usuario.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {
    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveChamarServicoDeCadastro() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioController.cadastro(usuarioDTO);

        verify(usuarioService, times(1)).cadastro(usuarioDTO);
    }

    @Test
    void deveChamarServicoDeConsultaTodos() {
        usuarioController.consultaTodos(0, 10, "nome");

        verify(usuarioService, times(1)).consultaTodos(0, 10, "nome");
    }

    @Test
    void deveChamarServicoDeConsultaPorId() {
        UUID id = UUID.randomUUID();

        usuarioController.consulta(id);

        verify(usuarioService, times(1)).consulta(id);
    }

    @Test
    void deveChamarServicoDeEditar() {
        UUID id = UUID.randomUUID();
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        usuarioController.editar(id, usuarioDTO);

        verify(usuarioService, times(1)).editar(id, usuarioDTO);
    }
}