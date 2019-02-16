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
        ChainList<Substring> classPath = new ChainList<>();
        Token token = tokenReader.takeRequired(Token.Type.NAME);
        classPath.addLast(token.getContent());
        while(tokenReader.takeOptional(Token.Type.SPECIAL, ".") != null){
            token = tokenReader.takeRequired(Token.Type.NAME);
            classPath.addLast(token.getContent());
        }
        tokenReader.readNoMore();
        lineReader.readNoMoreChildren();
        return new LogicalDecorations(Substring.union(classPath.getFirst(), classPath.getLast()));
    }

    private LogicalProperties composeUsingProperties(LineReader lineReader, TokenReader tokenReader){
        ChainList<Substring> classPath = new ChainList<>();
        Token token = tokenReader.takeRequired(Token.Type.NAME);
        classPath.addLast(token.getContent());
        while(tokenReader.takeOptional(Token.Type.SPECIAL, ".") != null){
            token = tokenReader.takeRequired(Token.Type.NAME);
            classPath.addLast(token.getContent());
        }
        tokenReader.readNoMore();
        lineReader.readNoMoreChildren();
        return new LogicalProperties(Substring.union(classPath.getFirst(), classPath.getLast()));
    }

    private LogicalDesign composeDefineDesign(LineReader lineReader, TokenReader tokenReader){
        ChainList<Substring> name = new ChainList<>();
        ChainList<Substring> parentName = new ChainList<>();

        Token token = tokenReader.takeRequired(Token.Type.NAME);
        name.addLast(token.getContent());
        while((token = tokenReader.takeOptional(Token.Type.NAME)) != null){
            name.addLast(token.getContent());
        }

        if(tokenReader.takeOptional(Token.Type.KEYWORD, "BASED") != null){
            tokenReader.takeRequired(Token.Type.KEYWORD, "ON");

            token = tokenReader.takeRequired(Token.Type.NAME);
            parentName.addLast(token.getContent());
            while((token = tokenReader.takeOptional(Token.Type.NAME)) != null){
                parentName.addLast(token.getContent());
            }
        }

        tokenReader.readNoMore();

        LogicalDesign logicalDesign = new LogicalDesign(
                Substring.union(name.getFirst(), name.getLast()),
                parentName.count() > 0 ? Substring.union(parentName.getFirst(), parentName.getLast()) : null
        );

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
        ChainList<Substring> name = new ChainList<>();
        ChainList<Substring> value = new ChainList<>();
        boolean literal;

        Token token = tokenReader.takeRequired(Token.Type.NAME);
        name.addLast(token.getContent());
        while((token = tokenReader.takeOptional(Token.Type.NAME)) != null){
            name.addLast(token.getContent());
        }
        tokenReader.takeRequired(Token.Type.SPECIAL, "=");
        if((token = tokenReader.takeOptional(Token.Type.LITERAL)) != null){
            value.addLast(token.getContent());
            literal = true;
        } else {
            token = tokenReader.takeRequired(Token.Type.NAME);
            value.addLast(token.getContent());
            while((token = tokenReader.takeOptional(Token.Type.NAME)) != null){
                value.addLast(token.getContent());
            }
            literal = false;
        }
        tokenReader.readNoMore();
        lineReader.readNoMoreChildren();
        return new LogicalSetter(
                Substring.union(name.getFirst(), name.getLast()),
                new ChainList<>(new Value(Substring.union(value.getFirst(), value.getLast()), literal))
        );
    }

    private LogicalConstant composeDefineConstant(LineReader lineReader, TokenReader tokenReader){
        ChainList<Substring> name = new ChainList<>();
        Substring value;

        Token token = tokenReader.takeRequired(Token.Type.NAME);
        name.addLast(token.getContent());
        while((token = tokenReader.takeOptional(Token.Type.NAME)) != null){
            name.addLast(token.getContent());
        }
        tokenReader.takeRequired(Token.Type.SPECIAL, "=");
        value = tokenReader.takeRequired(Token.Type.LITERAL).getContent();
        tokenReader.readNoMore();
        lineReader.readNoMoreChildren();
        return new LogicalConstant(
                Substring.union(name.getFirst(), name.getLast()),
                value
        );
    }
}
