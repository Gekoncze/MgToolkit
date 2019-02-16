package cz.mg.toolkit.graphics.designer.loader.utilities;

import cz.mg.collections.list.chainlist.ChainList;
import cz.mg.collections.list.chainlist.ChainListItem;
import cz.mg.parser.entity.Token;


public class TokenReader {
    private final ChainList<Token> tokens;
    private ChainListItem<Token> position;

    public TokenReader(ChainList<Token> tokens) {
        this.tokens = tokens;
        position = tokens.getFirstItem();
    }

    public boolean canTake(){
        return position != null;
    }

    private Token take(){
        if(position == null) return null;
        Token line = position.getData();
        position = position.getNextItem();
        return line;
    }

    public Token takeRequired(){
        Token token = take();
        if(token == null) throw new ComposeException(tokens.getLast().getContent(), "Missing token.");
        return token;
    }

    public Token takeRequired(Token.Type type){
        Token token = takeRequired();
        if(token.getType() != type) throw new ComposeException(token.getContent(), "Expected " + type.name() + " token, but got " + token.getType().name() + ".");
        return token;
    }

    public Token takeRequired(Token.Type type, String content){
        Token token = takeRequired(type);
        if(!token.getContent().toString().equals(content)) throw new ComposeException(token.getContent(), "Expected " + content + ", but got " + token.getContent().toString() + ".");
        return token;
    }

    public Token takeOptional(){
        if(position == null) return null;
        Token token = take();
        if(token == null){
            backoff();
            return null;
        }
        return token;
    }

    public Token takeOptional(Token.Type type){
        if(position == null) return null;
        Token token = takeOptional();
        if(token == null || token.getType() != type){
            backoff();
            return null;
        }
        return token;
    }

    public Token takeOptional(Token.Type type, String content){
        if(position == null) return null;
        Token token = takeOptional(type);
        if(token == null || !token.getContent().toString().equals(content)){
            backoff();
            return null;
        }
        return token;
    }

    public void backoff(){
        if(position == null) position = tokens.getLastItem();
        else position = position.getPreviousItem();
    }

    public void readNoMore(){
        while(canTake()){
            Token token = take();
            if(token.getType() != Token.Type.COMMENT) throw new ComposeException(token.getContent(), "Unexpected token " + token.getType() + ": " + token.getContent().toString() + ".");
        }
    }
}
