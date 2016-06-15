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

import freemarker.template.SimpleHash;

/**
 *
 * fns标签库的集合,作用是初始化fns标签库的集合有哪些
 * @author Jonathan
 * @version 2016/6/12 12:58
 * @since JDK 7.0+
 */
public class FnsTags extends SimpleHash {
	public FnsTags(){
		put("config",new GlobalConfigTag());
		put("abbr",new AbbrTag());
		put("dictList",new DictListTag());
	}

}
