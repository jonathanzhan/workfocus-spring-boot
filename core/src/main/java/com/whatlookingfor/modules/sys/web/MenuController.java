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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.whatlookingfor.common.config.Global;
import com.whatlookingfor.common.utils.StringUtils;
import com.whatlookingfor.core.base.web.BaseController;
import com.whatlookingfor.modules.sys.entity.Menu;
import com.whatlookingfor.modules.sys.service.SystemService;
import com.whatlookingfor.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

/**
 * 菜单信息管理的Controller
 *
 * @author Jonathan
 * @version 2016/3/24 09:53
 * @since JDK 7.0+
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/menu")
public class MenuController extends BaseController {

    @Autowired
    private SystemService systemService;

    @ModelAttribute("menu")
    public Menu get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return systemService.getMenu(id);
        } else {
            return new Menu();
        }
    }

    @RequiresPermissions("sys:menu:view")
    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        List<Menu> list = Lists.newArrayList();
        List<Menu> sourceList = systemService.findAllMenu();
        Menu.sortList(list, sourceList, Menu.getRootId(), true);
        model.addAttribute("list", list);
        return "modules/sys/menuList";
    }

    @RequiresPermissions("sys:menu:view")
    @RequestMapping(value = "form")
    public String form(Menu menu, Model model) {
        if (menu.getParent() == null || menu.getParent().getId() == null) {
            menu.setParent(new Menu(Menu.getRootId()));
        }
        menu.setParent(systemService.getMenu(menu.getParent().getId()));
        // 获取排序号，最末节点排序号+10
        if (StringUtils.isBlank(menu.getId())) {
            List<Menu> list = Lists.newArrayList();
            List<Menu> sourceList = systemService.findAllMenu();
            Menu.sortList(list, sourceList, menu.getParentId(), false);
            if (list.size() > 0) {
                menu.setSeq(list.get(list.size() - 1).getSeq() + 10);
            }
        }
        model.addAttribute("menu", menu);
        return "modules/sys/menuForm";
    }


    /**
     * 菜单的新增保存
     *
     * @param menu
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "save")
    public String save(Menu menu, Model model, RedirectAttributes redirectAttributes) {
        if (!UserUtils.getUser().isAdmin()) {
            addMessage(redirectAttributes, "越权操作，只有超级管理员才能添加或修改数据！");
            return "redirect:" + adminPath + "/sys/menu/?repage";
        }
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/menu/";
        }
        if (!beanValidator(model, menu)) {
            return form(menu, model);
        }
        systemService.saveMenu(menu);
        addMessage(redirectAttributes, "保存菜单'" + menu.getName() + "'成功");
        return "redirect:" + adminPath + "/sys/menu/";
    }


    /**
     * 菜单的删除
     * todo  权限的删除  menuRole的删除
     *
     * @param menu
     * @param redirectAttributes
     * @return
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "delete")
    public String delete(Menu menu, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/menu/";
        }
        if (Menu.isRoot(menu.getId())) {
            addMessage(redirectAttributes, "删除菜单失败, 不允许删除顶级菜单或编号为空");
        } else {
            systemService.deleteMenu(menu);
            addMessage(redirectAttributes, "删除菜单成功");
        }
        return "redirect:" + adminPath + "/sys/menu/";
    }


    /**
     * 批量修改菜单排序
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "updateSort")
    public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/menu/";
        }
        for (int i = 0; i < ids.length; i++) {
            Menu menu = new Menu(ids[i]);
            menu.setSeq(sorts[i]);
            systemService.updateMenuSort(menu);
        }
        systemService.removeUserMenuCache();
        addMessage(redirectAttributes, "保存菜单排序成功!");
        return "redirect:" + adminPath + "/sys/menu/";
    }

    /**
     * 获取zTree的JSON数据，无序的
     *
     * @param extId
     * @param isShowHide
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String isShowHide) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Menu> list = systemService.findAllMenu();
        for (int i = 0; i < list.size(); i++) {
            Menu e = list.get(i);
            if (StringUtils.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIds().indexOf("," + extId + ",") == -1)) {
                if (isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")) {
                    continue;
                }
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", e.getId());
                map.put("pId", e.getParentId());
                map.put("name", e.getName());
                mapList.add(map);
            }
        }
        return mapList;
    }
}
