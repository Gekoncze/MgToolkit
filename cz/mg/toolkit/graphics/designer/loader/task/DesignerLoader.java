package cz.mg.toolkit.graphics.designer.loader.task;

import cz.mg.parser.entity.Page;
import cz.mg.parser.task.PageParser;
import cz.mg.parser.utilities.Substring;
import cz.mg.toolkit.graphics.designer.Designer;
import cz.mg.toolkit.graphics.designer.loader.entity.DesignerRoot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DesignerLoader {
    private final InputStream stream;

    public DesignerLoader(InputStream stream) {
        this.stream = stream;
    }

    public Designer load(){
        Page page = new Page(new Substring(readText()));
        PageParser pageParser = new PageParser();
        pageParser.parse(page);
        DesignerComposer designerComposer = new DesignerComposer();
        DesignerRoot designerFile = designerComposer.compose(page);

        DesignerResolver designerResolver = new DesignerResolver();
        return designerResolver.resolve(designerFile);
    }

    private String readText(){
        StringBuilder text = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
            String line;
            while((line = reader.readLine()) != null){
                text.append(line);
                text.append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
        return text.toString();
    }
}
