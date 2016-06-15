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
package com.whatlookingfor.modules.sys.entity;

import com.whatlookingfor.core.base.entity.DataEntity;

/**
 * 系统参数的实体类
 *
 * @author Jonathan
 * @version 2016/5/9 17:30
 * @since JDK 7.0+
 */
public class Params extends DataEntity<Params> {

    private static final long serialVersionUID = 1L;
    private String paramName;
    private String paramValue;
    private String paramLabel;

    private String paramName1;//判断参数名是否存在


    public String getParamName1() {
        return paramName1;
    }

    public void setParamName1(String paramName1) {
        this.paramName1 = paramName1;
    }

    public Params() {
        super();
    }

    public Params(String paramName){
        super(paramName);
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public String getParamLabel() {
        return paramLabel;
    }

    public void setParamLabel(String paramLabel) {
        this.paramLabel = paramLabel;
    }
}
