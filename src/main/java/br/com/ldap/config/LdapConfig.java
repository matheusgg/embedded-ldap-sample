package br.com.ldap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

@Configuration
public class LdapConfig {

	@Bean
	public LdapTemplate ldapTemplate() {
		return new LdapTemplate(this.ldapContextSource());
	}

	@Bean
	public LdapContextSource ldapContextSource() {
		final LdapContextSource ldapContextSource = new LdapContextSource();
		ldapContextSource.setUrl("ldap://127.0.0.1:33389");
		ldapContextSource.setBase("dc=springframework,dc=org");
		ldapContextSource.setUserDn("cn=admin,dc=springframework,dc=org");
		ldapContextSource.setPassword("admin");
		return ldapContextSource;
	}

	@Bean
	public LdapAuthoritiesPopulator ldapAuthoritiesPopulator() {
		final DefaultLdapAuthoritiesPopulator populator = new DefaultLdapAuthoritiesPopulator(this.ldapContextSource(), "ou=groups");
		populator.setGroupSearchFilter("(&(member={0}*)(objectClass=group))");
		populator.setGroupRoleAttribute("ou");
		populator.setConvertToUpperCase(true);
		return populator;
	}

}
