package edu.uconn.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Policy extends HealthItem {
	private List<Role> roles = new ArrayList<Role>();

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = super.toJSONObject();
		JSONArray jroles = new JSONArray();
		for(int i = 0; i < roles.size(); i++) {
			jroles.put(roles.get(i).toJSONObject());
		}
		jo.put("Roles",jroles);
		return jo;
	}
	
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
	
	public void setPermission(int role, int permission, 
							  boolean read, boolean write) {
		roles.get(role).setPermission(permission, read, write);
	}
	
	public void setPermissionRead(int r, int p, boolean set) {
		roles.get(r).setPermissionRead(p, set);
	}

	public void setPermissionWrite(int r, int p, boolean set) {
		roles.get(r).setPermissionWrite(p, set);
	}

	public int numRoles() {
		return roles.size();
	}

	public Role getRole(int position) {
		return roles.get(position);
	}
}
