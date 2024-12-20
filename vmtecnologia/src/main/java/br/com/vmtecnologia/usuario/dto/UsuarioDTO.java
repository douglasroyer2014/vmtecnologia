package br.com.vmtecnologia.usuario.dto;

import br.com.vmtecnologia.usuario.entity.UsuarioEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDTO {

    /**
     * Nome.
     */
    String nome;
    /**
     * E-mail.
     */
    String email;
    /**
     * Senha.
     */
    @NotBlank(message = "Senha não pode ser vazio")
    String senha;

    /**
     * Converte as informações constantes para um {@link UsuarioEntity}.
     *
     * @return um {@link UsuarioEntity}.
     */
    public UsuarioEntity convertToEntity() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setNome(this.getNome());
        entity.setEmail(this.getEmail());
        entity.setSenha(this.getSenha());

        return entity;
    }
}
