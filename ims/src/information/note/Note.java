package information.note;

import information.AbstractInfoPair;
import information.InfoType;
import information.InfoTypeName;

public final class Note extends AbstractInfoPair<StringNote> {
	private static InfoType infoType = new InfoType() {
		private static InfoTypeName infoTypeName =
				InfoTypeName.from("note");
		
		public InfoTypeName getInfoTypeName() {
			return infoTypeName;
		}
	};
	
	private Note(StringNote note) {
		super(note);
	}
	
	public Note from(String note) {
		return new Note(StringNote.from(note));
	}
	
	public InfoType getInfoType() {
		return infoType;
	}
	
	public String toString() {
		return String.format("Note<%s>", this.getValue());
	}
}
