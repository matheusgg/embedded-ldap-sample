package br.com.ldap.web;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NamingEnumeration;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ldap.model.User;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

	private final LdapTemplate ldapTemplate;
	private final LdapAuthoritiesPopulator authoritiesPopulator;

	@RequestMapping
	public List<User> user(@RequestParam final String username) throws Exception {
		final AttributesMapper<User> mapper = attributes -> {
			final String account = (String) attributes.get("account").get();
			final String sn = (String) attributes.get("sn").get();
			final String sAMAccountName = (String) attributes.get("sAMAccountName").get();
			final NamingEnumeration<?> objectclasses = attributes.get("objectclass").getAll();

			final Set<String> types = new HashSet<>();
			while (objectclasses.hasMoreElements()) {
				types.add((String) objectclasses.next());
			}

			final Collection<? extends GrantedAuthority> authorities = this.authoritiesPopulator.getGrantedAuthorities(new DirContextAdapter(account), sAMAccountName);

			return new User(sAMAccountName, sn, types, authorities);
		};

		final LdapQuery query = LdapQueryBuilder.query()
				.attributes("objectclass", "cn", "sn", "sAMAccountName", "account")
				.filter("(&(sAMAccountName={0})(objectClass=people))", username);

		return this.ldapTemplate.search(query, mapper);
	}

}
