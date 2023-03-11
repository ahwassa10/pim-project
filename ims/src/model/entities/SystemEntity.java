package model.entities;

import model.qualities.Identity;

public abstract class SystemEntity implements Entity {
	private Identity identity;
	
	public Identity getIdentity() {
		return identity;
	}
}