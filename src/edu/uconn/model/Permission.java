package edu.uconn.model;

public class Permission {
	private String name;
	private boolean read;
	private boolean write;
	
	public Permission(String name, boolean read, boolean write) {
		this.name = name;
		this.read = read;
		this.write = write;
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
	 * @return read privilege
	 */
	public boolean canRead() {
		return read;
	}
	/**
	 * @param read the read privilege to set
	 */
	public void setRead(boolean read) {
		this.read = read;
	}
	
	/**
	 * @return write privilege
	 */
	public boolean canWrite() {
		return write;
	}
	
	/**
	 * @param write the write privilege to set
	 */
	public void setWrite(boolean write) {
		this.write = write;
	}
	

}
