package model.information;

import model.real.NoteValue;
import model.real.InformationName;

public interface Note extends InformationType {
	default InformationName getName() {
		return InformationName.from("note");
	}
	
	static NoteValue instance(String nv) {
		return NoteValue.from(nv);
	}
}
