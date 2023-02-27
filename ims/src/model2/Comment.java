package model2;

import model.real.CommentValue;

public interface Comment {
	public default String getName() {
		return "Comment";
	}
	
	public CommentValue getValue();
}
