package br.com.apireembolso.security;

import br.com.apireembolso.models.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					usuario.getEmail(), usuario.getPassword(), new ArrayList<>());
			Authentication auth = authenticationManager.authenticate(authenticationToken);
			return auth;
		} catch (IOException e) {
			throw new RuntimeException("Falha ao autenticar usuário");
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((UsuarioDetalhe) authResult.getPrincipal()).getUsername();
		String status = ((UsuarioDetalhe) authResult.getPrincipal()).getStatus();
		String token = jwtUtil.generateToken(username);
		if(status.equals("Ativo")) {
		response.addHeader("Authorization","Bearer " + token);
		response.getWriter().write(token);
		response.getWriter().flush();
		response.addHeader("access-control-expose-headers", "Authorization");
		}
	}
}
