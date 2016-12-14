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
package com.whatlookingfor.modules.sys.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whatlookingfor.common.mapper.JsonMapper;
import com.whatlookingfor.common.utils.CacheUtils;
import com.whatlookingfor.common.utils.SpringContextHolder;
import com.whatlookingfor.modules.sys.dao.DictDao;
import com.whatlookingfor.modules.sys.dao.ParamsDao;
import com.whatlookingfor.modules.sys.entity.Dict;
import com.whatlookingfor.modules.sys.entity.Params;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 字典工具类
 * @author Jonathan
 * @version 2016-5-29
 */
public class DictUtils {
	
	private static DictDao dictDao = SpringContextHolder.getBean(DictDao.class);

	private static ParamsDao paramsDao = SpringContextHolder.getBean(ParamsDao.class);

	public static final String CACHE_DICT_MAP = "dictMap";

	public static final String CACHE_PARAM_MAP = "paramMap";

	/**
	 * 获取系统中所有的参数信息
	 * @return
	 */
	public static Map<String,Params> getParams(){
		Map<String,Params> result = (Map<String,Params>)CacheUtils.get(CACHE_PARAM_MAP);
		if(result == null){
			List<Params> list = paramsDao.findList(new Params());
			if(list !=null && list.size()>0 ){
				result = Maps.newHashMap();
				for (Params params :list){
					result.put(params.getParamName(),params);
				}
				CacheUtils.put(CACHE_PARAM_MAP,result);
			}
		}
		return result;
	}


	/**
	 * 获取系统参数对应的值
	 * @param name
	 * @return
	 */
	public static String getParamsByName(String name){
		Map<String,Params> paramsResult = getParams();
		if(paramsResult ==null){
			return null;
		}else{
			Params params = paramsResult.get(name);
			return params!=null ?params.getParamValue():"";
		}
	}

	/**
	 * 根据数据字典的type 以及value获取信息
	 * @param value
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && value != null){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 根据数据字典的type以及多个value获取数据字典的信息
	 * @param values
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}

	/**
	 * 根据数据字典的type 以及默认的label值 获取value值
	 * @param label
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public static String getDictValue(String label, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultValue;
	}

	/**
	 * 根据数据字典的type获取数据字典的列表
	 * @param type
	 * @return
	 */
	public static List<Dict> getDictList(String type){
		@SuppressWarnings("unchecked")
		Map<String, List<Dict>> dictMap = (Map<String, List<Dict>>)CacheUtils.get(CACHE_DICT_MAP);
		if (dictMap==null){
			dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList(new Dict())){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
			CacheUtils.put(CACHE_DICT_MAP, dictMap);
		}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
	/**
	 * 返回字典列表（JSON）
	 * @param type
	 * @return
	 */
	public static String getDictListJson(String type){
		return JsonMapper.toJsonString(getDictList(type));
	}

}
