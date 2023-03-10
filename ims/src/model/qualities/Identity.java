package model.qualities;

import java.util.UUID;

public class Identity implements Quality {
	
	private static QualityType qualityType =
			QualityType.from("identity");
	
	private UUID identity = UUID.randomUUID();
	
	private Identity() {}
	
	public static Identity newIdentifier() {
		return new Identity();
	}
	
	public UUID getIdentifier() {
		return identity;
	}
	
	public QualityType getQualityType() {
		return qualityType;
	}
	
	public String toString() {
		return identity.toString();
	}
}