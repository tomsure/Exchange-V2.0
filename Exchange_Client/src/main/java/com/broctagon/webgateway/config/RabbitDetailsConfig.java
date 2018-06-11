package com.broctagon.webgateway.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.core.env.Environment;


@Configuration
public class RabbitDetailsConfig implements BeanDefinitionRegistryPostProcessor, EnvironmentAware{

	private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
	
	private Map<String, String> queueInfos = new ConcurrentHashMap<>();
	
	private Map<String, Object> marketQueue = new ConcurrentHashMap<>();
	
	private String id = null;
	
	private String exchange = null;
	
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		queueBeanGenerator(beanFactory);
		bindingBeanGenerator(beanFactory);
	}

	@Override
	public void setEnvironment(Environment environment) {
		id = environment.getProperty("id");
		RelaxedPropertyResolver signalResolver = new RelaxedPropertyResolver(environment, "mq.");
		exchange = signalResolver.getProperty("exchange");
		marketQueue = signalResolver.getSubProperties("market.");
		Map<String, Object> queues = signalResolver.getSubProperties("queue.");
		queues.entrySet().parallelStream().forEach(entry -> {
			queueInfos.put(entry.getKey(), String.valueOf(entry.getValue()));
		});
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		registerQueue(registry);
		registerBinding(registry);
	}

	private void queueBeanGenerator(ConfigurableListableBeanFactory beanFactory) {
		for(String queueName : queueInfos.keySet()) {
			BeanDefinition queue = beanFactory.getBeanDefinition(queueName);
			ConstructorArgumentValues queueConstructor = queue.getConstructorArgumentValues();
			queueConstructor.addGenericArgumentValue(queueName + "." + id);
			queueConstructor.addGenericArgumentValue(false);
			queueConstructor.addGenericArgumentValue(false);
			queueConstructor.addGenericArgumentValue(true);
		}
		for(String queueName : marketQueue.keySet()) {
			BeanDefinition queue = beanFactory.getBeanDefinition(queueName);
			ConstructorArgumentValues queueConstructor = queue.getConstructorArgumentValues();
			queueConstructor.addGenericArgumentValue(queueName + "." + id);
			queueConstructor.addGenericArgumentValue(false);
			queueConstructor.addGenericArgumentValue(false);
			queueConstructor.addGenericArgumentValue(true);
		}
	}
	
	private void bindingBeanGenerator(ConfigurableListableBeanFactory beanFactory) {
		for(Entry<String, String> entry : queueInfos.entrySet()) {
			BeanDefinition binding = beanFactory.getBeanDefinition(entry.getKey() + "Binding");
			ConstructorArgumentValues bindingConstructor = binding.getConstructorArgumentValues();
			bindingConstructor.addGenericArgumentValue(entry.getKey() + "." + id);
			bindingConstructor.addGenericArgumentValue(DestinationType.QUEUE);
			bindingConstructor.addGenericArgumentValue(exchange);
			bindingConstructor.addGenericArgumentValue(entry.getValue() + "." + id);
			bindingConstructor.addGenericArgumentValue(new HashMap<String,Object>());
		}
		for(Entry<String, Object> entry : marketQueue.entrySet()) {
			BeanDefinition binding = beanFactory.getBeanDefinition(entry.getKey() + "Binding");
			ConstructorArgumentValues bindingConstructor = binding.getConstructorArgumentValues();
			bindingConstructor.addGenericArgumentValue(entry.getKey() + "." + id);
			bindingConstructor.addGenericArgumentValue(DestinationType.QUEUE);
			bindingConstructor.addGenericArgumentValue(exchange);
			bindingConstructor.addGenericArgumentValue(entry.getValue());
			bindingConstructor.addGenericArgumentValue(new HashMap<String,Object>());
		}
	}
	
	private void registerQueue(BeanDefinitionRegistry registry) {
		for(String queueName : marketQueue.keySet()) {
			registerBean(registry, queueName, Queue.class);
		}
		for(String queueName : queueInfos.keySet()) {
			registerBean(registry, queueName, Queue.class);
		}
	}
	

	private void registerBinding(BeanDefinitionRegistry registry) {
		for(String queueName : queueInfos.keySet()) {
			registerBean(registry, queueName + "Binding", Binding.class);
		}
		for(String queueName : marketQueue.keySet()) {
			registerBean(registry, queueName + "Binding", Binding.class);
		}
	}

	private void registerBean(BeanDefinitionRegistry registry, String name, Class<?> clazz) {
		AnnotatedGenericBeanDefinition annotatedGenericBeanDefinition = new AnnotatedGenericBeanDefinition(clazz);
		ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(annotatedGenericBeanDefinition);
		annotatedGenericBeanDefinition.setScope(scopeMetadata.getScopeName());
		
		AnnotationConfigUtils.processCommonDefinitionAnnotations(annotatedGenericBeanDefinition);
		BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(annotatedGenericBeanDefinition, name);
		BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
	}
}
