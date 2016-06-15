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

import com.whatlookingfor.common.utils.StringUtils;
import com.whatlookingfor.tag.freemarker.base.BaseTemplateMethodModel;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * 缩写字符串，超过最大宽度用“...”表示
 *
 * @author Jonathan
 * @version 2016/6/12 14:13
 * @since JDK 7.0+
 */
public class AbbrTag extends BaseTemplateMethodModel{
	@Override
	public TemplateModel exec(List args) throws TemplateModelException {
		String str = args.get(0).toString();
		SimpleNumber length = (SimpleNumber)args.get(1);
		SimpleScalar simpleScalar = getSimpleScalar(StringUtils.abbr(str,length.getAsNumber().intValue()));
		return simpleScalar;
	}
}
