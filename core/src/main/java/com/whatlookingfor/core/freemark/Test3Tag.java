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

package com.whatlookingfor.core.freemark;

import com.google.common.collect.Maps;
import com.whatlookingfor.modules.sys.entity.User;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Jonathan
 * @version 2016/6/7 17:00
 * @since JDK 7.0+
 */
public class Test3Tag implements TemplateMethodModelEx{
	@Override
	public TemplateModel exec(List args) throws TemplateModelException {

//		Map<String,User> map = Maps.newHashMap();
//		User user1 = new User();
//		user1.setName("12321");
//
//		User user2 = new User();
//		user2.setName("2222");
//
//		map.put("user1",user1);
//		map.put("user2",user2);
//
//		SimpleHash simpleHash = new SimpleHash(new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_23).build());
//		simpleHash.put("user1",user1);
//		simpleHash.put("user2",user2);
//
//		return simpleHash;

		return TemplateBooleanModel.TRUE;
	}
}
