package org.example.JWT;

import lombok.RequiredArgsConstructor;
import org.example.Entities.AuthResponse;
import org.example.Entities.DTO.DireccionDTO;
import org.example.Entities.Enum.Rol;
import org.example.Entities.UserLogin;
import org.example.Entities.Usuario;
import org.example.Repositories.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(UserLogin userLogin) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getMail(),
                userLogin.getPassword()));

        Usuario userDetails = usuarioRepository.findByMail(userLogin.getMail()).orElseThrow();
        String token = jwtService.getToken(userDetails);
        System.out.println(token);
      return new AuthResponse(userDetails.getId(), userDetails.getNombre(), userDetails.getDni(), userDetails.getMail(),
              userDetails.getRol(), userDetails.isEstado(),
              DireccionDTO.fromEntityList(userDetails.getDirecciones()), token);
    }

    public AuthResponse registrar(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return new AuthResponse(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getDni(),
                usuario.getMail(),
                usuario.getRol(), usuario.isEstado(),
                DireccionDTO.fromEntityList(usuario.getDirecciones()),
                jwtService.getToken(usuario)
        );
    }
}
