package model.metadata;

import model.real.CommentValue;
import model.real.InformationType;

public interface Comment extends Information {
	public default InformationType getName() {
		return InformationType.from("comment");
	}
	
	public CommentValue getValue();
}
