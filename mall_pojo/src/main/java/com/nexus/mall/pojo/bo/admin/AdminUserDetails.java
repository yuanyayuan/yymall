package com.nexus.mall.pojo.bo.admin;

import com.nexus.mall.pojo.BackendAdmin;
import com.nexus.mall.pojo.BackendResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**

* @Description:    SpringSecurity需要的用户详情

* @Author:         Nexus

* @CreateDate:     2020/9/10 22:58

* @UpdateUser:     Nexus

* @UpdateDate:     2020/9/10 22:58

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public class AdminUserDetails implements UserDetails {
    private BackendAdmin admin;
    private List<BackendResource> resourceList;
    public AdminUserDetails(BackendAdmin admin,List<BackendResource> resourceList) {
        this.admin = admin;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return resourceList.stream()
                .map(role ->new SimpleGrantedAuthority(role.getId()+":"+role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return admin.getStatus().equals(1);
    }
}
