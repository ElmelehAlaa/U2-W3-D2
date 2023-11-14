package AlaaElmeleh.U2W2D5.security;

import AlaaElmeleh.U2W2D5.entities.Utente;
import AlaaElmeleh.U2W2D5.exceptions.UnauthorizedException;
import AlaaElmeleh.U2W2D5.services.UtenteService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private UtenteService utenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException{
        //  per ogni request che richiede di essere autenticati
        //  Verifico l header ed estraggo il token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Per favore passa il Bearer Token nell'Authorization header");
        }else{
            String token = authHeader.substring(7);
            System.out.println("Token ->"+ token);
            //  Verifico token valido
            jwtTools.verifyToken(token);

            //  Cerco l'utente nel database tramite id (l'id sta nel payload del token, quindi devo estrarlo da lì)
            String id = jwtTools.extractIdFromToken(token);
            Utente currentUtente = utenteService.findById(Integer.parseInt(id));
            // 3.2 Segnalo a Spring Security che l'utente ha il permesso di procedere
            // Se non facciamo questa procedura, ci verrà comunque tornato 403
            Authentication authentication = new UsernamePasswordAuthenticationToken(currentUtente, null, currentUtente.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // 3.3 Procediamo (vuol dire andare al prossimo blocco della filter chain)
            filterChain.doFilter(request, response);
            // 4. Se non è OK -> 401
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // Questo metodo serve per specificare quando il filtro JWTAuthFilter non debba entrare in azione
        // Ad es tutte le richieste al controller /auth/** non devono essere filtrate
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
