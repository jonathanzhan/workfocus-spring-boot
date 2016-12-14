/**
 * Copyright  2014-2016 whatlookingfor@gmail.com(Jonathan)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.whatlookingfor.core.config;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Swagger ui配置
 *
 * @author Jonathan
 * @version 2016/5/17 18:39
 * @since JDK 7.0+
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration implements EnvironmentAware {

	private RelaxedPropertyResolver propertyResolver;

	private Contact contact;

	private List<Predicate<String>> components = Lists.newArrayList();

	@Override
	public void setEnvironment(Environment environment) {
		this.propertyResolver = new RelaxedPropertyResolver(environment, "swagger.");
		this.contact = new Contact(propertyResolver.getProperty("author"), propertyResolver.getProperty("url"), propertyResolver.getProperty("email"));
		String[] regex = propertyResolver.getProperty("apiPath").split(",");
		for (String str : regex) {
			components.add(regex(str));
		}
	}


	@Bean
	public Docket testApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("test")
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(true)
				.pathMapping("/")// base，最终调用接口后会和paths拼接在一起
				.select()
				.paths(or(components))//过滤的接口
				.build()
				.apiInfo(apiInfo());
	}

	@Bean
	public Docket normalApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("api")
				.genericModelSubstitutes(DeferredResult.class)
				.useDefaultResponseMessages(false)
				.forCodeGeneration(true)
				.pathMapping("/")
				.select()
				.paths(or(components))//过滤的接口
				.build()
				.apiInfo(demoApiInfo());
	}

	private ApiInfo demoApiInfo() {
		return new ApiInfo(
				propertyResolver.getProperty("title"),
				propertyResolver.getProperty("description"),
				propertyResolver.getProperty("version"),
				propertyResolver.getProperty("termsOfServiceUrl"),
				contact,
				propertyResolver.getProperty("license"),
				propertyResolver.getProperty("licenseUrl")
		);
	}


	private ApiInfo apiInfo() {
		return new ApiInfo(
				propertyResolver.getProperty("title"),
				propertyResolver.getProperty("description"),
				propertyResolver.getProperty("version"),
				propertyResolver.getProperty("termsOfServiceUrl"),
				contact,
				propertyResolver.getProperty("license"),
				propertyResolver.getProperty("licenseUrl")
		);
	}
}
