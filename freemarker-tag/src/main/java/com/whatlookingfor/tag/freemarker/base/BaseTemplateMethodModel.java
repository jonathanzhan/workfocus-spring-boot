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

package com.whatlookingfor.tag.freemarker.base;

import freemarker.template.*;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 针对 TemplateMethodModelEx的封装
 * @see freemarker.template.TemplateMethodModelEx
 * @author Jonathan
 * @version 2016/6/8 11:25
 * @since JDK 7.0+
 */
public abstract class BaseTemplateMethodModel implements TemplateMethodModelEx{

	/**
	 * 获取SimpleHash对象,用于传Map的集合,使用一个java.util.Hash类型的对象存储子变量
	 * SimpleHash类的方法可以添加和移除子变量。这些方法应该用来在变量被创建之后直接初始化。
	 * @return SimpleHash对象
	 */
	protected SimpleHash getSimpleHash(){
		SimpleHash simpleHash = new SimpleHash(new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23).build());
		return simpleHash;
	}


	/**
	 * 获取SimpleSequence对象,用于传序列的集合数据,使用一个java.util.List类型的对象存储它的子变量
	 * SimpleSequence有添加子元素的方法。在序列创建之后应该使用这些方法来填充序列。
	 * @return SimpleSequence对象
	 */
	protected SimpleSequence getSimpleSequence(){
		SimpleSequence simpleSequence = new SimpleSequence(new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23).build());
		return simpleSequence;
	}


	/**
	 * 获取SimpleScalar对象,也就是字符串标量对象
	 * @param str 需要生成的字符串标量
	 * @return 字符串标量对象 SimpleScalar
	 */
	protected SimpleScalar getSimpleScalar(String str){
		SimpleScalar simpleScalar = new SimpleScalar(str);
		return simpleScalar;
	}

	/**
	 * 获取SimpleDate对象,也就是日期的标量对象,第二个参数为日期类型,time,date,datetime或者未知
	 * @see freemarker.template.SimpleDate
	 * @param date 日期对象
	 * @param dateType  日期的格式(0=unKnow,1 = time, 2 = date, 3 = datetime)
	 * @return SimpleDate对象
	 */
	protected SimpleDate getSimpleDate(Date date,int dateType){
		SimpleDate simpleDate = new SimpleDate(date,dateType);
		return simpleDate;
	}


	/**
	 * 该方法展示如何返回boolean类型的对象
	 * @deprecated 示例方法
	 * @return TemplateModel
	 */
	private TemplateModel getBooleanResult(){
		return TemplateBooleanModel.FALSE;
	}


	/**
	 * 获取数字类型的标量对象SimpleNumber
	 * @param number 数字对象
	 * @return SimpleNumber
	 */
	protected SimpleNumber getSimpleNumber(Number number){
		SimpleNumber simpleNumber = new SimpleNumber(number);
		return simpleNumber;
	}


	/**
	 * 获取集合类型的对象 simpleCollection
	 * @param collection 集合Collection
	 * @return SimpleCollection
	 */
	protected SimpleCollection getSimpleCollection(Collection collection){
		SimpleCollection simpleCollection = new SimpleCollection(collection,new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23).build());
		return simpleCollection;
	}




}
