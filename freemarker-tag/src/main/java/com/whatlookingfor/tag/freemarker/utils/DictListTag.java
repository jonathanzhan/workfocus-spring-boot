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

package com.whatlookingfor.tag.freemarker.utils;

import com.google.common.collect.Lists;
import com.whatlookingfor.common.utils.Demo;
import com.whatlookingfor.tag.freemarker.base.BaseTemplateMethodModel;
import freemarker.template.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 根据数据字典类型获取数据字典的数据
 *
 * @author Jonathan
 * @version 2016/6/12 14:25
 * @since JDK 7.0+
 */
public class DictListTag extends BaseTemplateMethodModel{

	@Override
	public TemplateModel exec(List args) throws TemplateModelException {
		String type = args.get(0).toString();
		SimpleSequence simpleSequence = getSimpleSequence();
		List<Demo> list = Lists.newArrayList();
		list.add(new Demo("1","张三"));
		list.add(new Demo("2","李四"));

		SimpleCollection simpleCollection = getSimpleCollection(list);

		simpleSequence.add(list);
		return simpleCollection;
	}
}
