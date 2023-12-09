package com.synback.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.synback.models.AuthenticationUser;
import com.synback.repositories.AuthenticationRepository;
import java.util.Date;

@Service
public class AuthenticationService {

    @PostConstruct
    public void init() {
        // System.out.println("Expiration time: " + expirationTime);
    }

    @Value("${jwt.secret}")
    private String secretKey;
    
    @Value("${jwt.expiration}")
    private long expirationTime;
    
    @Autowired
    private AuthenticationRepository userRepository;
    
    public String login(String email, String password) {
        // Verifica se o e-mail foi fornecido
        if (email == null || email.isEmpty()) {
            throw new RuntimeException("E-mail não fornecido.");
        }

        // Verifica se a senha foi fornecida
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Senha não fornecida.");
        }

        AuthenticationUser userInDb = userRepository.findByEmail(email);
        // Verifica se um usuário com o e-mail fornecido foi encontrado
        if (userInDb == null) {
            throw new RuntimeException("E-mail não encontrado.");
        }

        // Verifica se a senha está correta
        if (!BCrypt.checkpw(password, userInDb.getPassword())) {
            throw new RuntimeException("Senha incorreta.");
        }

        try {
            // Gera o token para o usuário
            return generateToken(userInDb.getEmail());
        } catch (Exception e) {
            // Loga a exceção para investigação futura e lança uma exceção mais amigável
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar o token de autenticação.");
        }
    }

    public String generateToken(String email) {
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
    return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(new Date(nowMillis + expirationTime))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
}

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Objetivo: Extrai o e-mail do usuário

    // Como Funciona: Utiliza a biblioteca JWT para decodificar o token,
    // verificando-o com a chave secreta (secretKey). Após a decodificação, extrai o
    // assunto (subject) do corpo do token, que neste caso é o e-mail do usuário.
    public String extractEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    // Objetivo: Obtém a data de expiração do token JWT.

    // Como Funciona: Assim como no método anterior, decodifica o token JWT e extrai
    // a data de expiração do seu corpo.
    public Date extractExpirationDateFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
    }

    // Objetivo: Verifica se o token JWT expirou.

    // Como Funciona: Usa extractExpirationDateFromToken para obter a data de
    // expiração do token e compara-a com a data e hora atual. Se a data de
    // expiração for anterior à data atual, significa que o token expirou.
    public Boolean isTokenExpired(String token) {
        final Date expiration = extractExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Objetivo: Valida o token JWT para um determinado usuário.

    // Como Funciona: Extrai o nome de usuário (e-mail) do token usando
    // extractUsernameFromToken.

    // Compara o nome de usuário extraído do token com o e-mail do objeto user.
    // Verifica se o token não expirou usando isTokenExpired.

    // Retorna true se o nome de usuário corresponder e o token ainda for válido
    // (não expirado); caso contrário, retorna false.
    public Boolean validateToken(String token, AuthenticationUser user) {
        final String username = extractEmailFromToken(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
    }
}
