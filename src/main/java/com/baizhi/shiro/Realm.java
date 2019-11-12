package com.baizhi.shiro;

import com.baizhi.dao.*;
import com.baizhi.entity.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Realm extends AuthorizingRealm {
    @Autowired
    private AdminDAO adminDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private ZiyuanDAO ziyuanDAO;
    @Autowired
    private ARDAO ardao;
    @Autowired
    private ZRDAO zrdao;

    @Override //权限
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        Admin admin = new Admin();
        admin.setAdminName(username);
        ArrayList<String> rs = new ArrayList<>();
        ArrayList<String> zs = new ArrayList<>();
        Admin admin1 = adminDAO.selectOne(admin);
        AR ar = new AR();
        ar.setAdminId(admin1.getId());
        List<AR> ars = ardao.select(ar);
        for (AR ar1 : ars) {
            Role role = new Role();
            role.setId(ar1.getRoleId());
            Role role2 = roleDAO.selectByPrimaryKey(role);
            rs.add(role2.getRole());
            ZR zr = new ZR();
            zr.setRoleId(ar1.getRoleId());
            List<ZR> zrs = zrdao.select(zr);
            for (ZR zr1 : zrs) {
                Ziyuan ziyuan = new Ziyuan();
                ziyuan.setId(zr1.getId());
                Ziyuan ziyuan1 = ziyuanDAO.selectByPrimaryKey(ziyuan);
                zs.add(ziyuan1.getZiyuan());
            }
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(rs);
        simpleAuthorizationInfo.addStringPermissions(zs);
        return simpleAuthorizationInfo;
    }

    @Override //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        Admin admin = new Admin();
        admin.setAdminName(username);
        Admin admin1 = adminDAO.selectOne(admin);
        Md5Hash md5Hash = new Md5Hash(admin1.getPassword(), admin1.getSalt(), 1024);
        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        if (admin1 != null) {
            if (username.equals(admin1.getAdminName())) {
                simpleAuthenticationInfo = new SimpleAuthenticationInfo(admin1.getAdminName(), md5Hash, ByteSource.Util.bytes(admin1.getSalt()), this.getName());
            }
        }

        return simpleAuthenticationInfo;
    }
}
