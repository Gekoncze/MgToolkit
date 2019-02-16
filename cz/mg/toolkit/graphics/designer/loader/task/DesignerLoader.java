package cz.mg.toolkit.graphics.designer.loader.task;

import cz.mg.parser.entity.Book;
import cz.mg.parser.task.Parser;
import cz.mg.toolkit.graphics.designer.Designer;
import cz.mg.toolkit.graphics.designer.loader.entity.Logic;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DesignerLoader {
    public DesignerLoader() {
    }

    public Designer load(InputStream... streams){
        Parser parser = new Parser();
        Book book = parser.parse(readTexts(streams));

        DesignerComposer designerComposer = new DesignerComposer();
        Logic logic = designerComposer.compose(book);

        DesignerResolver designerResolver = new DesignerResolver();
        return designerResolver.resolve(logic);
    }

    private String[] readTexts(InputStream[] streams){
        String[] texts = new String[streams.length];
        for(int i = 0; i < streams.length; i++) texts[i] = readText(streams[i]);
        return texts;
    }

    private String readText(InputStream stream){
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
