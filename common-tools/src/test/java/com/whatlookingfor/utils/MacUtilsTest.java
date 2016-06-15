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

package com.whatlookingfor.utils;

import com.whatlookingfor.common.utils.MacUtils;
import org.junit.Test;

/**
 * TODO
 *
 * @author Jonathan
 * @version 2016/6/2 11:24
 * @since JDK 7.0+
 */
public class MacUtilsTest {

	@Test
	public void getMac(){
		System.out.println(MacUtils.getMac());
	}


	@Test
	public void getOSName(){
		System.out.println(MacUtils.getOSName());
	}


	@Test
	public void getUnixMac(){
		System.out.println(MacUtils.getUnixMACAddress());
	}
}
