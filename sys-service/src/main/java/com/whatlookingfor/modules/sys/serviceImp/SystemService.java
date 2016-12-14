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

package com.whatlookingfor.modules.sys.serviceImp;

import com.whatlookingfor.common.utils.StringUtils;
import com.whatlookingfor.core.base.entity.Page;
import com.whatlookingfor.core.base.service.BaseService;
import com.whatlookingfor.core.base.web.Servlets;


import com.whatlookingfor.common.utils.CacheUtils;
import com.whatlookingfor.common.utils.Encodes;
import com.whatlookingfor.core.exception.BusinessException;
import com.whatlookingfor.core.security.Digests;
import com.whatlookingfor.core.security.shiro.session.SessionDAO;
import com.whatlookingfor.modules.sys.dao.EmployeeDao;
import com.whatlookingfor.modules.sys.dao.MenuDao;
import com.whatlookingfor.modules.sys.dao.RoleDao;
import com.whatlookingfor.modules.sys.dao.UserDao;
import com.whatlookingfor.modules.sys.entity.Employee;
import com.whatlookingfor.modules.sys.entity.Menu;
import com.whatlookingfor.modules.sys.entity.Role;
import com.whatlookingfor.modules.sys.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 *
 * @author Jonathan
 * @version 2015-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private SessionDAO sessionDao;
    @Autowired
    private EmployeeDao employeeDao;

    public SessionDAO getSessionDao() {
        return sessionDao;
    }


    /**
     * 获取用户
     *
     * @param id userId
     * @return User
     */
    public User getUser(String id) {
        return userDao.get(id);
    }

    /**
     * 根据登录名获取用户
     *
     * @param loginName 登录名
     * @return User
     */
    public User getUserByLoginName(String loginName) {
        return userDao.getByLoginName(new User("",loginName));
    }

    public Page<User> findUser(Page<User> page, User user) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        // 设置分页参数
        user.setPage(page);
        // 执行分页查询
        page.setList(userDao.findList(user));
        return page;
    }

    /**
     * 无分页查询人员列表
     *
     * @param user user的查询条件
     * @return List<User>
     */
    public List<User> findUser(User user) {
        // 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
        return userDao.findList(user);
    }


    @Transactional(readOnly = false)
    public void saveUser(User user) {
        if (StringUtils.isBlank(user.getId())) {
            user.preInsert();
            userDao.insert(user);
        } else {
            // 更新用户数据
            user.preUpdate();
            userDao.update(user);
        }
        if (StringUtils.isNotBlank(user.getId())) {
            // 更新用户与角色关联
            userDao.deleteUserRole(user);
            if (user.getRoleList() != null && user.getRoleList().size() > 0) {
                userDao.insertUserRole(user);
            } else {
                throw new BusinessException(user.getName() + "没有设置角色！");
            }

        }
    }

    /**
     * 个人信息保存
     *
     * @param user 用户信息
     */
    @Transactional(readOnly = false)
    public void updateUserInfo(User user) {
        user.preUpdate();
        userDao.updateUserInfo(user);//保存用户个人信息
        //判断员工ID是否为空，如果不为空那么修改员工信息
        if (user.getEmployee() != null && user.getEmployee().getId() != null && !user.getEmployee().getId().equals("")) {
            Employee employee = user.getEmployee();
            employee.preUpdate();
            employeeDao.updateEmployeeInfo(employee);//修改员工信息
        }

//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
    }

    @Transactional(readOnly = false)
    public void deleteUser(User user) {
        userDao.delete(user);

    }

    @Transactional(readOnly = false)
    public void updatePasswordById(String id, String loginName, String newPassword) {
        User user = new User(id);
        user.setPassword(entryptPassword(newPassword));
        userDao.updatePasswordById(user);
        // 清除用户缓存
        user.setLoginName(loginName);
    }

    @Transactional(readOnly = false)
    public void updateUserLoginInfo(User user) {
        // 保存上次登录信息
        user.setLastLoginIp(user.getThisLoginIp());
        user.setLastLoginAt(user.getThisLoginAt());
        // 更新本次登录信息
        user.setThisLoginIp(StringUtils.getHost(Servlets.getRequest()));
        user.setThisLoginAt(new Date());
        userDao.updateLoginInfo(user);
    }

    /**
     * 验证 登录名 与密码是否正确
     *
     * @param userName 登录名
     * @param password 密码
     * @return boolean
     */
    @SuppressWarnings("unused")
    public boolean checkLogin(String userName, String password) {
        User user = userDao.getByLoginName(new User("",userName));
        return (user != null) &&
                (StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) &&
                SystemService.validatePassword(password, user.getPassword());


    }

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     *
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        byte[] salt = Encodes.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
    }


    //-- Role Service --//

    public Role getRole(String id) {
        return roleDao.get(id);
    }

    public Role getRoleByName(String name) {
        Role r = new Role();
        r.setName(name);
        return roleDao.getByName(r);
    }

    public Role getRoleByEname(String name) {
        Role r = new Role();
        r.setEname(name);
        return roleDao.getByEname(r);
    }

    public List<Role> findRole(Role role) {
        return roleDao.findList(role);
    }

    public List<Role> findAllRole() {
        return roleDao.findList(new Role());
    }

    @Transactional(readOnly = false)
    public void saveRole(Role role) {
        if (StringUtils.isBlank(role.getId())) {
            role.preInsert();
            roleDao.insert(role);

        } else {
            role.preUpdate();
            roleDao.update(role);
        }
        // 更新角色与菜单关联
        roleDao.deleteRoleMenu(role);
        if (role.getMenuList().size() > 0) {
            roleDao.insertRoleMenu(role);
        }
        // 更新角色与部门关联
        roleDao.deleteRoleOrg(role);
        if(role.getDataScope()==5){
            if (role.getOrgList().size() > 0){
                roleDao.insertRoleOrg(role);
            }
        }

    }

    @Transactional(readOnly = false)
    public void deleteRole(Role role) {
        roleDao.delete(role);

        // 清除用户角色缓存
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
    }

    @Transactional(readOnly = false)
    public Boolean outUserInRole(Role role, User user) {
        List<Role> roles = user.getRoleList();
        for (Role e : roles) {
            if (e.getId().equals(role.getId())) {
                roles.remove(e);
                saveUser(user);
                return true;
            }
        }
        return false;
    }

    @Transactional(readOnly = false)
    public User assignUserToRole(Role role, User user) {
        if (user == null) {
            return null;
        }
        List<String> roleIds = user.getRoleIdList();
        if (roleIds.contains(role.getId())) {
            return null;
        }
        user.getRoleList().add(role);
        saveUser(user);
        return user;
    }

    //-- Menu Service --//

    public Menu getMenu(String id) {
        return menuDao.get(id);
    }

    public List<Menu> findAllMenu() {
        return menuDao.findAllList(new Menu());
    }


    /**
     * 菜单信息的修改新增操作
     * 1、修改或者新增菜单
     * 2、更新子节点的parent_ids字段信息
     * 3、删除当前用户菜单的缓存
     *
     * @param menu 菜单信息
     */
    @Transactional(readOnly = false)
    public void saveMenu(Menu menu) {

        // 获取父节点实体
        menu.setParent(this.getMenu(menu.getParent().getId()));
        //设置菜单的级别
        menu.setLevel(menu.getParent().getLevel() + 1);

        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = menu.getParentIds();

        // 设置新的父节点串
        menu.setParentIds(menu.getParent().getParentIds() + menu.getParent().getId() + ",");

        // 保存或更新实体
        if (StringUtils.isBlank(menu.getId())) {
            menu.preInsert();
            menuDao.insert(menu);
        } else {
            menu.preUpdate();
            menuDao.update(menu);
        }

        // 更新子节点 parentIds
        Menu m = new Menu();
        m.setParentIds("%," + menu.getId() + ",%");
        List<Menu> list = menuDao.findByParentIdsLike(m);
        for (Menu e : list) {
            e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
            menuDao.updateParentIds(e);
        }
        removeUserMenuCache();
    }

    @Transactional(readOnly = false)
    public void updateMenuSort(Menu menu) {
        menuDao.updateSort(menu);
    }

    public void removeUserMenuCache() {
        // 清除用户菜单缓存
        // 清除日志相关缓存
    }


    @Transactional(readOnly = false)
    public void deleteMenu(Menu menu) {
        menuDao.delete(menu);
        removeUserMenuCache();
    }


}
