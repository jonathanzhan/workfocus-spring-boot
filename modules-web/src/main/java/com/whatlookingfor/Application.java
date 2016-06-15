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

package com.whatlookingfor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.request.RequestContextListener;

import javax.sql.DataSource;

/**
 * 项目的Application配置类
 *
 * @author Jonathan
 * @version 2016/5/25 17:59
 * @since JDK 7.0+
 */
@EnableTransactionManagement
@SpringBootApplication
public class Application {


	public static void main(String[] args) {
//		SpringApplication springApplication = new SpringApplication(Application.class);
//		//添加项目启动的监听类
//		springApplication.addListeners(new StartUpListener());
//		springApplication.run(args);

		SpringApplication.run(Application.class, args);
	}


//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(Application.class);
//	}

	@Bean
	@ConditionalOnMissingBean(RequestContextListener.class)
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}


	@Bean
	public LocalValidatorFactoryBean validator(){
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		return validatorFactoryBean;
	}

	/**
	 * 增加事物管理
	 *
	 * @param dataSource 数据库连接对象
	 * @return 事物管理
	 */
	@Bean
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}




}
