package edu.uconn.model;

import java.util.ArrayList;
import java.util.List;

public class Role extends HealthItem {
	private String name = "";
	private List<Permission> permissions = new ArrayList<Permission>();
	
	public Role(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the permissions
	 */
	public List<Permission> getPermissions() {
		return permissions;
	}
	
	public int numPermissions() {
		return permissions.size();
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public void addPermission(String permissionName, boolean read, boolean write) {
		permissions.add(new Permission(permissionName, read, write));
	}

	public Permission getPermission(int location) {
		return permissions.get(location);
	}

}
