package com.muchenski.api.seed;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import com.muchenski.api.domain.Categoria;
import com.muchenski.api.domain.Contato;
import com.muchenski.api.domain.Endereco;
import com.muchenski.api.domain.Lancamento;
import com.muchenski.api.domain.Permissao;
import com.muchenski.api.domain.Pessoa;
import com.muchenski.api.domain.Usuario;
import com.muchenski.api.domain.enums.TipoLancamento;
import com.muchenski.api.repositories.CategoriaRepository;
import com.muchenski.api.repositories.LancamentoRepository;
import com.muchenski.api.repositories.PermissaoRepository;
import com.muchenski.api.repositories.PessoaRepository;
import com.muchenski.api.repositories.UsuarioRepository;

@Profile(value = { "test", "dev" })
@Configuration
public class Seeding implements CommandLineRunner {

	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private LancamentoRepository lancamentoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private PermissaoRepository permissaoRepository;

	@Override
	public void run(String... args) throws Exception {

		RestTemplate restTemplate = new RestTemplate();

		lancamentoRepository.deleteAll();
		pessoaRepository.deleteAll();
		categoriaRepository.deleteAll();

		// <Categoria>

		Categoria c1 = new Categoria(null, "Luz e água");
		Categoria c2 = new Categoria(null, "Mercado");
		Categoria c3 = new Categoria(null, "Aluguel");
		Categoria c4 = new Categoria(null, "Salário");
		Categoria c5 = new Categoria(null, "Hora extra");

		categoriaRepository.saveAll(List.of(c1, c2, c3, c4, c5));

		// </Categoria>
		
		// <Pessoa>

		Pessoa p1 = new Pessoa(null, "Henrique", "776.433.430-80", true);
		Pessoa p2 = new Pessoa(null, "Bruna", "255.709.630-06", true);
		Pessoa p3 = new Pessoa(null, "João", "807.117.120-42", true);
		Pessoa p4 = new Pessoa(null, "Ana", "807.117.120-42", true);
		Pessoa p5 = new Pessoa(null, "Paulo", "807.117.120-42", true);
		Pessoa p6 = new Pessoa(null, "Joana", "807.117.120-42", true);
		Pessoa p7 = new Pessoa(null, "Rafaela", "807.117.120-42", true);
		Pessoa p8 = new Pessoa(null, "Letícia", "807.117.120-42", true);
		Pessoa p9 = new Pessoa(null, "Henrique", "776.433.430-80", true);
		Pessoa p10 = new Pessoa(null, "Bruna", "255.709.630-06", true);
		Pessoa p11 = new Pessoa(null, "João", "807.117.120-42", true);
		Pessoa p12 = new Pessoa(null, "Ana", "807.117.120-42", true);
		Pessoa p13 = new Pessoa(null, "Paulo", "807.117.120-42", true);
		Pessoa p14 = new Pessoa(null, "Joana", "807.117.120-42", true);
		Pessoa p15 = new Pessoa(null, "Rafaela", "807.117.120-42", true);

		boolean setarEnderecos = false;
		if (setarEnderecos) {
			String cep1 = "01001000";
			Endereco e1 = restTemplate.getForObject("https://viacep.com.br/ws/" + cep1 + "/json/", Endereco.class);
			p1.setEndereco(e1);

			String cep2 = "82600280";
			Endereco e2 = restTemplate.getForObject("https://viacep.com.br/ws/" + cep2 + "/json/", Endereco.class);
			p2.setEndereco(e2);

			String cep3 = "28772970";
			Endereco e3 = restTemplate.getForObject("https://viacep.com.br/ws/" + cep3 + "/json/", Endereco.class);
			p3.setEndereco(e3);
		}

		Contato ctt1 = new Contato(null, "Contato1", "contato1@gmail.com", "3333-3333", p1);
		Contato ctt2 = new Contato(null, "Contato2", "contato2@gmail.com", "4444-4444", p1);
		
		p1.getContatos().addAll(List.of(ctt1, ctt2));
	
		pessoaRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11, p12, p13, p14, p15));

		// </Pessoa>

		// <Lancamento>

		Lancamento l1 = new Lancamento(null, "Pagamento 01 descrição", LocalDate.now(), LocalDate.now(), 3215.0, null,
				TipoLancamento.DESPESA, c1, p1);

		Lancamento l2 = new Lancamento(null, "Pagamento 02 descrição", LocalDate.now(), null, 6546.0, null,
				TipoLancamento.RECEITA, c2, p1);

		Lancamento l3 = new Lancamento(null, "Pagamento 03 descrição", LocalDate.now(), LocalDate.now(), 451.0, null,
				TipoLancamento.DESPESA, c3, p2);

		Lancamento l4 = new Lancamento(null, "Pagamento 04 descrição", LocalDate.now(), LocalDate.now(), 365.0, null,
				TipoLancamento.RECEITA, c4, p2);

		Lancamento l5 = new Lancamento(null, "Pagamento 05 descrição", LocalDate.now(), null, 845.0, null,
				TipoLancamento.DESPESA, c5, p3);

		Lancamento l6 = new Lancamento(null, "Pagamento 06 descrição", LocalDate.now(), LocalDate.now(), 9875.0, null,
				TipoLancamento.RECEITA, c1, p3);

		Lancamento l7 = new Lancamento(null, "Pagamento 07 descrição", LocalDate.now(), LocalDate.now(), 3215.0, null,
				TipoLancamento.DESPESA, c1, p1);

		Lancamento l8 = new Lancamento(null, "Pagamento 08 descrição", LocalDate.now(), null, 6546.0, null,
				TipoLancamento.RECEITA, c2, p1);

		Lancamento l9 = new Lancamento(null, "Pagamento 09 descrição", LocalDate.now(), LocalDate.now(), 451.0, null,
				TipoLancamento.DESPESA, c3, p2);

		Lancamento l10 = new Lancamento(null, "Pagamento 10 descrição", LocalDate.now(), LocalDate.now(), 365.0, null,
				TipoLancamento.RECEITA, c4, p2);

		Lancamento l11 = new Lancamento(null, "Pagamento 11 descrição", LocalDate.now(), LocalDate.now(), 845.0, null,
				TipoLancamento.DESPESA, c5, p3);

		Lancamento l12 = new Lancamento(null, "Pagamento 12 descrição", LocalDate.now(), LocalDate.now(), 9875.0, null,
				TipoLancamento.RECEITA, c1, p3);

		lancamentoRepository.saveAll(List.of(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12));

		// </Lancamento>

		// <Permissao>

		Permissao r1 = new Permissao(null, "ROLE_CADASTRAR_CATEGORIA");
		Permissao r2 = new Permissao(null, "ROLE_PESQUISAR_CATEGORIA");
		Permissao r3 = new Permissao(null, "ROLE_REMOVER_CATEGORIA");

		Permissao r4 = new Permissao(null, "ROLE_CADASTRAR_PESSOA");
		Permissao r5 = new Permissao(null, "ROLE_PESQUISAR_PESSOA");
		Permissao r6 = new Permissao(null, "ROLE_REMOVER_PESSOA");

		Permissao r7 = new Permissao(null, "ROLE_CADASTRAR_LANCAMENTO");
		Permissao r8 = new Permissao(null, "ROLE_PESQUISAR_LANCAMENTO");
		Permissao r9 = new Permissao(null, "ROLE_REMOVER_LANCAMENTO");

		permissaoRepository.saveAll(List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9));

		// </Permissao>

		// <Usuario>

		Usuario admin = new Usuario(null, "admin", "admin@admin.com", encoder.encode("admin!@"));
		admin.setPermissoes(List.of(r1, r2, r3, r4, r5, r6, r7, r8, r9));

		Usuario u1 = new Usuario(null, "Maria", "maria@maria.com", encoder.encode("maria!@"));
		u1.setPermissoes(List.of(r1, r2));

		Usuario u2 = new Usuario(null, "Henrique", "hjmuchenski@gmail.com", encoder.encode("henrique!@"));
		u2.setPermissoes(List.of(r2, r5, r8));;
		
		usuarioRepository.saveAll(List.of(admin, u1, u2));

		// </Usuario>

	}

}
