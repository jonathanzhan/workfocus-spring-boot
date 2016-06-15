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

import com.whatlookingfor.modules.sys.entity.Menu;
import com.whatlookingfor.modules.sys.utils.UserUtils;
import com.whatlookingfor.tag.freemarker.base.BaseTemplateMethodModel;
import freemarker.template.*;

import java.util.List;

/**
 * TODO
 *
 * @author Jonathan
 * @version 2016/6/15 18:31
 * @since JDK 7.0+
 */
public class GetMenuListByLevelTag extends BaseTemplateMethodModel{
	@Override
	public TemplateModel exec(List args) throws TemplateModelException {
		SimpleNumber start = (SimpleNumber)args.get(0);
		SimpleNumber end = (SimpleNumber)args.get(1);

		List<Menu> list = UserUtils.getMenuListByLevel(start.getAsNumber().intValue(),end.getAsNumber().intValue());
		SimpleCollection simpleCollection = getSimpleCollection(list);
		return simpleCollection;
	}
}
