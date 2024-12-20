package br.com.vmtecnologia.usuario.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "usuario")
public class UsuarioEntity {

    /**
     * Identificador único.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;
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
    String senha;
}
