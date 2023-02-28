package model.metadata;

import model.real.CommentValue;
import model.real.MetadataName;

public interface Comment extends Metadata<CommentValue>{
	public default MetadataName getName() {
		return new MetadataName("comment");
	}
	
	public CommentValue getValue();
}
