package com.sfac.javaSpringBoot.modules.account.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 角色类
 * @author: HymanHu
 * @date: 2019年11月28日
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	//注解成id，
	@Id
	//并且注解成主键
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;


	private String roleName;
    //@Transient实体bean的属性不参与实际的列表生成，例如引用数据的注入（分页的使用）
	@Transient
	private List<User> users;
	@Transient
	private List<Resource> resources;

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
}
