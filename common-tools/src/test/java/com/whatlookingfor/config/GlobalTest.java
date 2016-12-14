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

package com.whatlookingfor.config;

import com.whatlookingfor.core.config.Global;
import org.junit.Assert;
import org.junit.Test;

/**
 * Global Test
 *
 * @author Jonathan
 * @version 2016/6/2 10:31
 * @since JDK 7.0+
 */
public class GlobalTest {

	@Test
	public void getProjectPath(){
		System.out.println(Global.getProjectPath());
		Assert.assertNotNull(Global.getProjectPath());
	}

	@Test
	public void getConfig(){
		Assert.assertEquals(Global.getConfig("whatlookingfor.author"),"Jonathan");
		Assert.assertEquals(Global.getConfig("whatlookingfor.email"),"whatlookingfor@gmail.com");
		Assert.assertEquals(Global.getConfig("whatlookingfor.url"),"https://github.com/whatlookingfor");
		Assert.assertEquals(Global.getConfig("whatlookingfor.blog"),"http://blog.csdn.net/whatlookingfor");
	}


	@Test
	public void getConst(){
		Assert.assertNotNull(Global.getConst("YES_INT"));
	}
}
