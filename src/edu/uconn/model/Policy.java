package edu.uconn.model;

import java.util.ArrayList;
import java.util.List;

public class Policy extends HealthItem {
	private List<Role> roles = new ArrayList<Role>();

	/**
	 * @return the providers
	 */
	public List<Role> getProviders() {
		return roles;
	}

	/**
	 * @param roles the providers to set
	 */
	public void setProviders(List<Role> roles) {
		this.roles = roles;
	}

	public int addProvider(String name) {
		roles.add(new Role(name));
		return ( roles.size() - 1);
	}

	public void addPermission(int id, String permissionName, boolean read, boolean write) {
		roles.get(id).addPermission(permissionName,read,write);
	}

	public int numRoles() {
		return roles.size();
	}

	public Role getRole(int position) {
		return roles.get(position);
	}
}
