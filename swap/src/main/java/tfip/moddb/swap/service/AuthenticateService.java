package tfip.moddb.swap.service;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import com.google.gson.JsonObject;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import static tfip.moddb.swap.Constants.*;

@Service
public class AuthenticateService {
    private SecretKey signKey;

    @PostConstruct
    public void init() {
        String passphrase = SECRET_KEY; //System.getenv("JWT_SIGNKEY");
		
        try {
			signKey = Keys.hmacShaKeyFor(passphrase.getBytes("UTF-8"));
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new IllegalArgumentException("Creating HMAC Sign key", ex);
		}
	}


	public boolean validate(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(signKey).build()
				.parseClaimsJws(token);
			return true;
		} catch (JwtException ex) {
			ex.printStackTrace();
		}
		return false;
	}


	public Optional<JsonObject> authenticate(String username, String password) {

		// if (!userRepo.validateUser(username, password))
		// 	return Optional.empty();

		String token = Jwts.builder()
			.setSubject(username)
			.signWith(signKey)
			.compact();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("subject", username);
        jsonObject.addProperty("token", token);


		return Optional.of(jsonObject);
	}

}

