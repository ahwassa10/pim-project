package model.information;

import model.real.CommentValue;
import model.real.InformationName;

public interface Comment extends InformationType {
	static InformationName getName() {
		return InformationName.from("comment");
	}
	
	static CommentValue instance(String cv) {
		return CommentValue.from(cv);
	}
}
