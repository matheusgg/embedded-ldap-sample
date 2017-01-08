package br.com.ldap.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.directory.server.core.DefaultDirectoryService;
import org.apache.directory.server.core.interceptor.Interceptor;
import org.apache.directory.server.core.schema.SchemaInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.ldap.server.ApacheDSContainer;
import org.springframework.stereotype.Component;

@Component
public class ApacheDSContainerPostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		if (bean instanceof ApacheDSContainer) {
			final ApacheDSContainer dsContainer = ((ApacheDSContainer) bean);
			final DefaultDirectoryService service = dsContainer.getService();
			final List<Interceptor> interceptors = new ArrayList<>(service.getInterceptors());
			interceptors.add(new SchemaInterceptor());
			service.setInterceptors(interceptors);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		return bean;
	}
}
