package information.note;

import information.InfoPair;
import information.InfoType;
import information.InfoTypeName;

public interface Note {
	String getNote();
	
	InfoPair<Note> asInfoPair();
	
	static InfoType asInfoType() {
		return new InfoType() {
			private static final InfoTypeName infoTypeName =
					InfoTypeName.from("Note");
			
			public InfoTypeName getInfoTypeName() {
				return infoTypeName;
			}
		};
	}
}
