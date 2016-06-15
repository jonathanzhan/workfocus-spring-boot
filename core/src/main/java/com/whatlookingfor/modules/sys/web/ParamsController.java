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
package com.whatlookingfor.modules.sys.web;

import com.whatlookingfor.common.config.Global;
import com.whatlookingfor.core.persistence.Page;
import com.whatlookingfor.core.base.web.BaseController;
import com.whatlookingfor.modules.sys.entity.Params;
import com.whatlookingfor.modules.sys.service.ParamsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统参数controller
 *
 * @author Jonathan
 * @version 2016/5/9 17:35
 * @since JDK 7.0+
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/params")
public class ParamsController extends BaseController {
    @Autowired
    private ParamsService paramsService;

    @RequiresPermissions("sys:params:view")
    @RequestMapping(value = {"list", ""})
    public String list(Params params, HttpServletRequest request, HttpServletResponse response, Model model) {
        List<String> paramNameList = paramsService.findParamNameList();
        model.addAttribute("paramNameList", paramNameList);
        Page<Params> page = paramsService.findPage(new Page<Params>(request, response), params);
        model.addAttribute("page", page);
        return "modules/sys/paramsList";
    }
    @RequiresPermissions("sys:params:view")
    @RequestMapping(value = "form")
    public String form(Params params, Model model) {
        params = paramsService.getParamById(params.getParamName());
        if(params==null){
            params = new Params();
            model.addAttribute("params", params);
            return "modules/sys/paramsForm";
        }
        model.addAttribute("params", params);
        return "modules/sys/paramsForm";
    }

    @RequiresPermissions("sys:params:edit")
    @RequestMapping(value = "save")
    public String save(Params params, RedirectAttributes redirectAttributes) {
        Params params1 = paramsService.getParamById(params.getParamName());
        if(params1==null) {
            paramsService.save(params);
            addMessage(redirectAttributes, "保存系统参数成功");
        } else{
            paramsService.update(params);
            addMessage(redirectAttributes, "修改系统参数成功");
        }
        return "redirect:"+ Global.getAdminPath()+"/sys/params/?repage";
    }

   /**
     * 参数名是否重复检查
     * @param oldParamName
     * @param paramName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkParamName")
    public String checkParamName(String oldParamName, String paramName) {
        if (paramName!=null && paramName.equals(oldParamName)) {
            return "true";
        } else if (paramName!=null && paramsService.getParamById(paramName) == null) {
            return "true";
        }
        return "false";
    }
}
