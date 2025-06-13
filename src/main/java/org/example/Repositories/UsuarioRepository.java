package org.example.Repositories;


import org.example.Entities.Enum.Rol;
import org.example.Entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends BaseRepository<Usuario,Long> {
    Optional<Usuario> findByMail(String mail);
    List<Usuario> findAllByRol(Rol rol);
}
