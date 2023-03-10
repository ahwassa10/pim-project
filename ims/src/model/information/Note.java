package model.information;

import model.real.NoteValue;
import model.real.QualityName;

public interface Note extends InformationType {
	default QualityName getName() {
		return QualityName.from("note");
	}
	
	static NoteValue instance(String nv) {
		return NoteValue.from(nv);
	}
}
