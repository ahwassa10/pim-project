package qualitygardens.selectiontag;

import entity.Identifier;

public interface SelectionTagValueID extends Identifier {
    SelectionTagID getSelectionTagID();
    String getSelectionTagValue();
}
