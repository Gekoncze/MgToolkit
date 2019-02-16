package cz.mg.toolkit.graphics.designer.loader.task;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.parser.entity.Book;
import cz.mg.parser.entity.Line;
import cz.mg.parser.entity.Page;
import cz.mg.parser.entity.Token;
import cz.mg.parser.utilities.Substring;
import cz.mg.toolkit.graphics.designer.loader.entity.*;
import cz.mg.toolkit.graphics.designer.loader.utilities.ComposerException;
import cz.mg.toolkit.graphics.designer.loader.utilities.LineReader;
import cz.mg.toolkit.graphics.designer.loader.utilities.TokenReader;


public class DesignerComposer {
    public DesignerComposer() {
    }

    public Logic compose(Book book){
        Logic logic = new Logic();
        for(Page page : book.getChildren()){
            logic.getChildren().addLast(composePage(page));
        }
        return logic;
    }

    private LogicalDesigner composePage(Page page){
        LogicalDesigner logicalDesigner = new LogicalDesigner();
        LineReader lineReader = new LineReader((ChainList<Line>)page.getChildren());
        while(lineReader.canTake()){
            Line line = lineReader.take();
            if(line.getChildren().count() <= 0) continue;
            TokenReader tokenReader = new TokenReader((ChainList<Token>)line.getChildren());
            Token token = tokenReader.takeRequired(Token.Type.KEYWORD);
            switch(token.getContent().toString()){
                case "USING":
                    token = tokenReader.takeRequired(Token.Type.KEYWORD);
                    switch(token.getContent().toString()){
                        case "DECORATIONS":
                            LogicalDecorations logicalDecorations = composeUsingDecorations(lineReader, tokenReader);
                            logicalDecorations.setParent(logicalDesigner);
                            break;
                        case "PROPERTIES":
                            LogicalProperties logicalProperties = composeUsingProperties(lineReader, tokenReader);
                            logicalProperties.setParent(logicalDesigner);
                            break;
                        default:
                            throw new ComposerException("Expected DECORATIONS or INTERFACE, but got " + token.getContent().toString());
                    }
                    break;
                case "DEFINE":
                    token = tokenReader.takeRequired(Token.Type.KEYWORD);
                    switch(token.getContent().toString()){
                        case "DESIGN":
                            LogicalDesign logicalDesign = composeDefineDesign(lineReader, tokenReader);
                            logicalDesign.setParent(logicalDesigner);
                            break;
                        case "CONSTANT":
                            LogicalConstant logicalConstant = composeDefineConstant(lineReader, tokenReader);
                            logicalConstant.setParent(logicalDesigner);
                            break;
                        default:
                            throw new ComposerException("Expected DESIGN or CONSTANT, but got " + token.getContent().toString());
                    }
                    break;
                default:
                    throw new ComposerException("Expected USING or DEFINE, but got " + token.getContent().toString());
            }
        }
        return logicalDesigner;
    }

    private LogicalDecorations composeUsingDecorations(LineReader lineReader, TokenReader tokenReader){
        return new LogicalDecorations(composeClassPath(lineReader, tokenReader));
    }

    private LogicalProperties composeUsingProperties(LineReader lineReader, TokenReader tokenReader){

        return new LogicalProperties(composeClassPath(lineReader, tokenReader));
    }

    private Substring composeClassPath(LineReader lineReader, TokenReader tokenReader){
        ChainList<Substring> classPath = new ChainList<>();
        Token token = tokenReader.takeRequired(Token.Type.NAME);
        classPath.addLast(token.getContent());
        while(tokenReader.takeOptional(Token.Type.SPECIAL, ".") != null){
            token = tokenReader.takeRequired(Token.Type.NAME);
            classPath.addLast(token.getContent());
        }
        tokenReader.readNoMore();
        lineReader.readNoMoreChildren();
        Substring cp = Substring.union(classPath.getFirst(), classPath.getLast());
        if(cp.toString().contains(" ")) throw new ComposerException("Class path cannot contain spaces.");
        return cp;
    }

    private LogicalDesign composeDefineDesign(LineReader lineReader, TokenReader tokenReader){
        Substring name = composeName(tokenReader);
        Substring parentName = null;
        if(tokenReader.takeOptional(Token.Type.KEYWORD, "BASED") != null){
            tokenReader.takeRequired(Token.Type.KEYWORD, "ON");
            parentName = composeName(tokenReader);
        }
        tokenReader.readNoMore();
        LogicalDesign logicalDesign = new LogicalDesign(name, parentName);

        Line line;
        int childLevel = lineReader.getLastIndentation() + 1;
        while((line = lineReader.takeLevelOptional(childLevel)) != null){
            if(line.getChildren().count() <= 0) continue;
            tokenReader = new TokenReader((ChainList<Token>)line.getChildren());
            LogicalSetter setter = composeSetter(lineReader, tokenReader);
            setter.setParent(logicalDesign);
        }

        return logicalDesign;
    }

    private LogicalSetter composeSetter(LineReader lineReader, TokenReader tokenReader){
        Substring name = composeName(tokenReader);
        tokenReader.takeRequired(Token.Type.SPECIAL, "=");

        Token token;
        boolean literal;
        Substring value;
        if((token = tokenReader.takeOptional(Token.Type.LITERAL)) != null){
            value = token.getContent();
            literal = true;
        } else {
            value = composeName(tokenReader);
            literal = false;
        }
        tokenReader.readNoMore();
        lineReader.readNoMoreChildren();
        return new LogicalSetter(name, new ChainList<>(new Value(value, literal)));
    }

    private LogicalConstant composeDefineConstant(LineReader lineReader, TokenReader tokenReader){
        Substring name = composeName(tokenReader);
        tokenReader.takeRequired(Token.Type.SPECIAL, "=");
        Substring value = tokenReader.takeRequired(Token.Type.LITERAL).getContent();
        tokenReader.readNoMore();
        lineReader.readNoMoreChildren();
        return new LogicalConstant(name, value);
    }

    private Substring composeName(TokenReader tokenReader){
        ChainList<Substring> name = new ChainList<>();
        Token token = tokenReader.takeRequired(Token.Type.NAME);
        name.addLast(token.getContent());
        while((token = tokenReader.takeOptional(Token.Type.NAME)) != null){
            name.addLast(token.getContent());
        }
        Substring n = Substring.union(name.getFirst(), name.getLast());
        if(n.toString().contains("  ")) throw new ComposerException("Name parts can be separated only by one space.");
        return n;
    }
}
