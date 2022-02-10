/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package top.goodz.future.domain.model.ldap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.Mac;
import javax.naming.CompositeName;
import javax.naming.Name;
import java.util.Collection;
import java.util.Date;

/**
 * 支持LDAP 用户扩展类
 *
 * @author zhangyajun
 */

@Getter
@Entry(objectClasses = {"inetOrgPerson", "organizationalPersonm", "person", "top"})
public class LDAPUserDetails extends User {

 /*   @Id
    @JsonIgnore
    private Name id;*/
    @Attribute(name = "uid")
    private String userName;
    @Attribute(name = "userPassword")
    private String password;


    @SneakyThrows
    public LDAPUserDetails(String id, String username, String password,
                           boolean enabled, boolean accountNonExpired,
                           boolean credentialsNonExpired, boolean accountNonLocked,
                           Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userName = username;
        this.password = password;
    }
}
