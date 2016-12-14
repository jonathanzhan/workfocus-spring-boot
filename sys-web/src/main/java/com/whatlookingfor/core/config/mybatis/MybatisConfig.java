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

package com.whatlookingfor.core.config.mybatis;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * mybatis的基础配置
 *
 * @author Jonathan
 * @version 2016/5/19 11:01
 * @since JDK 7.0+
 */
@Configuration
@EnableConfigurationProperties({MybatisProperties.class, MybatisRefreshProperties.class})
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class})//允许特定的class出现来跳过配置
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class MybatisConfig {

	@Autowired
	private MybatisProperties properties;

	@Autowired(required = false)
	private Interceptor[] interceptors;

	@Autowired(required = false)
	private DatabaseIdProvider databaseIdProvider;

	@Autowired
	private ResourceLoader resourceLoader = new DefaultResourceLoader();

	@Autowired
	private MybatisRefreshProperties mybatisRefreshProperties;

	@PostConstruct
	public void checkConfigFileExists() {
		if (this.properties.isCheckConfigLocation() && StringUtils.hasText(this.properties.getConfigLocation())) {
			Resource resource = this.resourceLoader.getResource(this.properties.getConfigLocation());
			Assert.state(resource.exists(), "Cannot find config location: " + resource
					+ " (please add config file or check your Mybatis configuration)");
		}
	}


	/**
	 * 定义sqlSessionFactory的bean
	 *
	 * @param dataSource
	 * @return
	 * @throws Exception
	 */
	@Bean(name = "sqlSessionFactory")
	@ConditionalOnMissingBean
	//不允许特定的bean出现来跳过配置
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setVfs(SpringBootVFS.class);
		if (StringUtils.hasText(this.properties.getConfigLocation())) {
			sqlSessionFactoryBean.setConfigLocation(this.resourceLoader.getResource(this.properties.getConfigLocation()));
		}

		sqlSessionFactoryBean.setConfiguration(properties.getConfiguration());
		if (!ObjectUtils.isEmpty(this.interceptors)) {
			sqlSessionFactoryBean.setPlugins(this.interceptors);
		}
		if (this.databaseIdProvider != null) {
			sqlSessionFactoryBean.setDatabaseIdProvider(this.databaseIdProvider);
		}
		if (StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
			sqlSessionFactoryBean.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
		}
		if (StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
			sqlSessionFactoryBean.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
		}
		if (!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
			sqlSessionFactoryBean.setMapperLocations(this.properties.resolveMapperLocations());
		}
//		sqlSessionFactoryBean.setMybatisRefreshProperties(mybatisRefreshProperties);
		return sqlSessionFactoryBean.getObject();
	}

	@Bean
	@ConditionalOnMissingBean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		ExecutorType executorType = this.properties.getExecutorType();
		if (executorType != null) {
			return new SqlSessionTemplate(sqlSessionFactory, executorType);
		} else {
			return new SqlSessionTemplate(sqlSessionFactory);
		}
	}
}
