package es.uma.dns.dietasusuariosciklumbackend.security;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenDietaServicio {

    @Autowired
    private JwtUtil jwtUtil; //Para hacer modificaciones y cosas a los tokens

    private String token; //Usaré un token para todas las peticiones, realmente se debería pactar con los otros ms

    private static TokenDietaServicio instance;

    private TokenDietaServicio() {
        token = null;
        token = getToken();

    }

    public static TokenDietaServicio getInstancia() {
        if (instance == null) {
            instance = new TokenDietaServicio();
        }
        return instance;
    }

    @PostConstruct
    public String getToken() {

        if (token == null) {
            //Necesito una id para el token, que pactamos con otros ms en teoria
            String ID_PARA_TOKEN = "150";
            this.token = jwtUtil.generateToken(ID_PARA_TOKEN); //no debe hacerse new, para eso está el autowired, y ahora hago postConstruct porque se inyecta tras llamar al constructor y sino daba null
        }
        return token;
    }

}
