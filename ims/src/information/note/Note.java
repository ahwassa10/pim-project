package information.note;

import information.InfoPair;
import information.InfoType;
import information.InfoTypeName;
import information.SimpleInfoType;

public interface Note {
	String getNote();
	
	InfoPair<Note> asInfoPair();
	
	static InfoType asInfoType() {
		return new SimpleInfoType(InfoTypeName.from("Note"));
	}
}
