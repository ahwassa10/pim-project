package program;

import java.io.IOException;
import java.util.Set;

import entity.EntitySystem;
import entity.EntitySystemBuilder;
import entity.Identifier;
import entity.TagIdentifier;
import entity.TagSystem;
import qualitygardens.selectiontag.SelectionTagGarden;
import qualitygardens.taggarden.TagGarden;
import qualitygardens.taggarden.TagGardenBuilder;
import source.FileSource;
import source.FileSourceBuilder;
import statement.StatementStore;

public class Testing {
    public static void main(String[] args) throws IOException {
        EntitySystem es = EntitySystemBuilder.test_entitysystem();
        StatementStore ss = es.getStatementStore();
        
        TagGarden tg = new TagGardenBuilder(ss, "general").build();
    }
}
