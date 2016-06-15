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

import freemarker.template.SimpleHash;

/**
 * TODO
 *
 * @author Jonathan
 * @version 2016/6/7 16:30
 * @since JDK 7.0+
 */
public class SysTags extends SimpleHash{

	public SysTags(){
		put("test",new Test1Tag());
	}
}
