package org.example.Repositories;


import org.example.Entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends BaseRepository<Usuario,Long> {
    Optional<Usuario> findByMail(String mail);
}
