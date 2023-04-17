package qualitygardens.selectiontag;

import entity.Identifier;

public interface SelectionTagValueID extends Identifier {
    Identifier getSelectionTagID();
    String getSelectionTagValue();
}
