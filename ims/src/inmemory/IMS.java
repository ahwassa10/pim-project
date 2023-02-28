package inmemory;

import java.util.List;
import java.util.UUID;

import model.entities.SystemEntity;
import model2.Identity;
import model2.Metadata;
import model2.Tag;

public class IMS {
	private IdentityTable identityTable = new IdentityTable();
	
	public SystemEntity createEntity() {
		Identity i = new Identity() {
			private UUID uuid = UUID.randomUUID();
			public UUID getValue() {
				return this.uuid;
			}
		};
		
		SystemEntity se = new SystemEntity() {
			public Identity getIdentity() {
				return i;
			}
			public List<Tag> getTags() {
				return null;
			}
			public List<Metadata<Object>> getAttributes() {
				return null;
			}
		};
		
		identityTable.set(i, se);
		return se;
	}
	
}
