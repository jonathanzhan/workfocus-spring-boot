/*
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

package com.whatlookingfor.core.support;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:19:19
 */
public interface DataType {
	static final String STRING = "java.lang.String";
	static final String BOOLEAN = "java.lang.Boolean";
	static final String INTEGER = "java.lang.Integer";
	static final String DOUBLE = "java.lang.Double";
	static final String FLOAT = "java.lang.Float";
	static final String LONG = "java.lang.Long";
	static final String BIGDECIMAL = "java.math.BigDecimal";
	static final String DATE = "java.util.Date";
	static final String TIME = "java.sql.Time";
	static final String TIMESTAMP = "java.sql.Timestamp";
}
