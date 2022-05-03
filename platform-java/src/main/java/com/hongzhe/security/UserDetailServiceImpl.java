package com.hongzhe.security;

import com.hongzhe.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.hongzhe.entity.SysUser;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUsername(s);
        if(sysUser == null){
            throw new UsernameNotFoundException("username or passwd not correct");
        }

        return new AccountUser(sysUser.getId(),sysUser.getUsername(),sysUser.getPassword(),getUserAuthority(sysUser.getId()));
    }

    //get user authorization
    public List<GrantedAuthority> getUserAuthority(long userId){

        String authority = sysUserService.getUserAuthorityInfo(userId);

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
