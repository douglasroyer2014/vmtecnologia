package br.com.vmtecnologia.usuario.controller;

import br.com.vmtecnologia.usuario.dto.UsuarioDTO;
import br.com.vmtecnologia.usuario.entity.UsuarioEntity;
import br.com.vmtecnologia.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/usuario")
@Setter(onMethod_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UsuarioController {

    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<String> cadastro(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.cadastro(usuarioDTO);
        return ResponseEntity.ok("Cadastrado com sucesso!");
    }

    @GetMapping
    public Iterable<UsuarioEntity> consultaTodos(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(required = false) String nome) {
        return usuarioService.consultaTodos(page, size, nome);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> consulta(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.consulta(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editar(@PathVariable UUID id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.editar(id, usuarioDTO);
    }

}
