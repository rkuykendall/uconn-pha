package edu.uconn.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Role extends HealthItem {
	private String name = "";
	private List<Permission> permissions = new ArrayList<Permission>();
	
	public Role(String name) {
		this.name = name;
	}

	public JSONObject toJSONObject() throws JSONException {
		JSONObject jo = super.toJSONObject();
		jo.put("Name", name);
		
		// Add each permissions individually
		JSONArray jpermissions = new JSONArray();
		for(int i = 0; i < permissions.size(); i++) {
			jpermissions.put(permissions.get(i).toJSONObject());
		}
		jo.put("Permissions",jpermissions);

		return jo;
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
	
	public void setPermission(int id, boolean read, boolean write) {
		permissions.get(id).setRead(read);
		permissions.get(id).setWrite(write);
	}

	public void setPermissionRead(int id, boolean read) {
		permissions.get(id).setRead(read);
	}

	public void setPermissionWrite(int id, boolean write) {
		permissions.get(id).setWrite(write);
	}

	public Permission getPermission(int location) {
		return permissions.get(location);
	}

}
