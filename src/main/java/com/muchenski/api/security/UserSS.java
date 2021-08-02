package com.muchenski.api.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.muchenski.api.domain.Usuario;

public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String nome;
	private String password;
	private String username;
	private Collection<? extends GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();

	public UserSS(Usuario usuario) {
		this.password = usuario.getSenha();
		this.username = usuario.getEmail();
		this.grantedAuthorities = usuario.getPermissoes().stream()
				.map(p -> new SimpleGrantedAuthority(p.getDescricao().toUpperCase())).collect(Collectors.toSet());
		this.nome = usuario.getNome();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
