package model2;

import model.real.CommentValue;
import model.real.PropertyName;

public interface Comment extends Metadata<CommentValue>{
	public default PropertyName getName() {
		return new PropertyName("comment");
	}
	
	public CommentValue getValue();
}
