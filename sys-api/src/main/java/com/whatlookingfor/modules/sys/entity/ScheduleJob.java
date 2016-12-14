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

import java.io.Serializable;

/**
 * 定时任务的实体类
 * 
 * @author Jonathan
 * @version 2015/5/9 17:30
 * @since JDK 7.0+
 */
public class ScheduleJob implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;    //任务名
    private String group;   //任务组
    private String cronExpression;  //cron表达式
    private String status;  //状态
    private String description; //描述
    private String className;   //要执行的任务类路径名

    public ScheduleJob() {
        super();
    }

    public ScheduleJob(String name, String group, String cronExpression,
                       String status, String description, String className) {
        super();
        this.name = name;
        this.group = group;
        this.cronExpression = cronExpression;
        this.status = status;
        this.description = description;
        this.className=className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
