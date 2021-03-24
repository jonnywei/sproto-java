package org.sproto;

import java.util.*;

/**
 * parse schema file
 */
public class SprotoSchemaParser {

 public static    List<String> generateToken(String schema){
        List<String> tokenList = new ArrayList<>();
        int length = schema.length();
        int index = 0;
        int model = 0;
        do{
            char c = schema.charAt(index);
            switch (model){
                case 0: // normal
                    if(isWhiteSpaceChar(c) || isNewLineChar(c) ){
                        index ++;
                    }else if (isCommitStartChar(c)) {
                        model = 2;
                        break;
                    }else {
                        model = 1;
                    }
                    break;
                case 1: //token model
                    StringBuilder sb = new StringBuilder();
                    if(isDoteChar(c) || isBraceChar(c) ||isStarChar(c) || isColonChar(c)){
                        sb.append(c);
                        tokenList.add(sb.toString());
                        model = 0;
                        index ++;
                        break;
                    }
                    do{
                        c = schema.charAt(index);
                        if(isValidateChar(c)){
                            sb.append(c);
                            index++;
                        }else if (isCommitStartChar(c)){
                            model = 2;
                            break;
                        }else {
                            model = 0 ;
                            break;
                        }
                    }while (index < length);
                    tokenList.add(sb.toString());
                    break;
                case 2: // commit model
                    do{
                        c = schema.charAt(index);
                        if(isCommitStartChar(c)){
                            index++;
                            continue;
                        }
                        if(isNewLineChar(c)){
                            index++;
                            model = 0;
                            break;
                        }else {
                            index++ ;
                        }
                    }while (index < length);
                    break;
            }

        }while (index < length);
        return tokenList;

    }

    public static boolean isValidateChar(char a){
        if (a >='a' && a <='z' || a >='A' && a <='Z' || a>='0'&& a<='9' ){
            return true;
        }
        return false;
    }



    public static boolean isDoteChar(char a){
        if ( a=='.'){
            return true;
        }
        return false;
    }


    public static boolean isBraceChar(char a){
        if ( a=='{' || a== '}' || a=='(' || a== ')'){
            return true;
        }
        return false;
    }

    public static boolean isStarChar(char a){
        if ( a=='*'){
            return true;
        }
        return false;
    }


    public static boolean isColonChar(char a){
        if ( a==':'){
            return true;
        }
        return false;
    }

    public static boolean isNewLineChar(char a){
        if (  a=='\n' ){
            return true;
        }
        return false;
    }

    public static boolean isWhiteSpaceChar(char a){
        if ( a== '\r'|| a=='\n' || a==' ' || a =='\t'){
            return true;
        }
        return false;
    }
    public static boolean isCommitStartChar(char a){
        if ( a== '#'){
            return true;
        }
        return false;
    }

    private static class TokenSequence {
        private List<String> tokenList;
        private int size;
        private int index;
        private static String EOT = "EOT";
        public TokenSequence(List<String> tokenList) {

            this.tokenList = tokenList;
            this.size = this.tokenList.size();
        }
        public String getNextToken(){
            if(index < size){
                return tokenList.get(index++);
            }
            return EOT;
        }
        public String peekNextToken(){
            if(index < size){
                return tokenList.get(index);
            }
            return EOT;
        }
        public void back(){
            this.index--;
        }
    }


    public static SprotoSchema parse(String schemaFile){
        List<String> t = SprotoSchemaParser.generateToken(schemaFile);
        SprotoSchema sprotoSchema =   SprotoSchemaParser.parse(t);
        return sprotoSchema;
    }



    public static SprotoSchema parse(List<String> tokenList){
        SprotoSchema sprotoSchema = new SprotoSchema();
        List<SprotoStruct> schemas = sprotoSchema.getTypes();

        TokenSequence tokenSequence = new TokenSequence(tokenList);
        do{
             parseStruct(tokenSequence, sprotoSchema);
             parseProtocol(tokenSequence, sprotoSchema);
            if(tokenSequence.peekNextToken() == TokenSequence.EOT){
                break;
            }
        }while (true);


        Map<String,SprotoStruct> structMap = new HashMap<>();
        for(SprotoStruct struct : schemas){
            structMap.put(struct.getName(),struct);
        }
        for(SprotoStruct struct : schemas){
            fillStructInfo(struct,structMap);
        }

        for(SprotoProtocol protocol : sprotoSchema.getProtocols()){
            fillStructProtocol(protocol,structMap);
        }
        return sprotoSchema;
    }

    private static void fillStructInfo(SprotoStruct struct , Map<String, SprotoStruct> structMap){
        struct.getFields().sort(Comparator.comparingInt(SprotoField::getNumber));
        for(SprotoField field : struct.getFields()){
            if (field.getType() == SprotoType.STRUCT ){
                if (field.getSprotoStruct() == null ){
                    field.setSprotoStruct(structMap.get(field.getStructName()));
                }
            }
        }
    }


    private static void fillStructProtocol(SprotoProtocol protocol , Map<String, SprotoStruct> structMap){
        if(protocol.getRequest() == null && protocol.getRequestName() != null){
            protocol.setRequest(structMap.get(protocol.getRequestName()));
        }
        if(protocol.getResponse() == null && protocol.getResponseName() != null){
            protocol.setResponse(structMap.get(protocol.getResponseName()));
        }
    }

    public static boolean match(String token, String btoken){
        if (token.equals(btoken)){
            return true;
        }
        return false;
    }
    public static   void parseStruct(TokenSequence tokenSequence,  SprotoSchema sprotoSchema){
        parseStruct(null,tokenSequence,sprotoSchema);
    }


    public static   void parseStruct(String parentStructName, TokenSequence tokenSequence,  SprotoSchema sprotoSchema){
        String token = tokenSequence.peekNextToken();
        if(!match(".", token)){
            return;
        }
        token = tokenSequence.getNextToken();
        String structName = tokenSequence.getNextToken();
        String dotName = structName;
        if(parentStructName != null){
            dotName = parentStructName +"."+ dotName;
        }
        SprotoStruct sprotoStruct = new SprotoStruct(structName,dotName);
        match("{",tokenSequence.getNextToken());
        token = tokenSequence.getNextToken();
        while (!match("}",token)){
            String filedName = token;
            if(match(".",filedName)){
                tokenSequence.back();
                parseStruct(structName,tokenSequence, sprotoSchema);
                token = tokenSequence.getNextToken();
                continue;
            }
            int number = Integer.valueOf(tokenSequence.getNextToken());
            match(":",tokenSequence.getNextToken());
            String array = tokenSequence.getNextToken();
            boolean isArray = false;
            if(match("*",array)){
                isArray = true;
                array = tokenSequence.getNextToken();
            }
            String type = array;
            SprotoType sprotoType = SprotoType.typeOf(type);
            SprotoField sprotoField = new SprotoField(filedName,number,sprotoType);
            if(isArray){
                 sprotoField.setArray(true);
            }
            if(sprotoType == SprotoType.STRUCT){
                sprotoField.setStructName(type);
            }
            sprotoStruct.addField(sprotoField);
            token = tokenSequence.getNextToken();
            if(match("(",token)){
                token = tokenSequence.getNextToken();
                match(")",tokenSequence.getNextToken());
                token = tokenSequence.getNextToken();
            }
        }
        match("}",token);
        sprotoSchema.addType( sprotoStruct);
    }



    public static  void parseProtocol(TokenSequence tokenSequence, SprotoSchema schema){
        String token = tokenSequence.peekNextToken();
        if(token == TokenSequence.EOT || match(".", token)){
            return;
        }
        token = tokenSequence.getNextToken();
        String protocolName = token;
        int number = Integer.valueOf(tokenSequence.getNextToken());
        SprotoProtocol protocol = new SprotoProtocol(protocolName,number);
        match("{",tokenSequence.getNextToken());
        token = tokenSequence.getNextToken();
        while (!match("}",token)){
            String filedName = token;
            if(match("request",filedName)){
                token = tokenSequence.getNextToken();
                if(match("{",token)){
                    SprotoStruct request = new SprotoStruct(protocolName+".request");
                    tokenSequence.back();
                    parseStructBody(tokenSequence,request, schema);
                    schema.addType(request);
                    protocol.setRequest(request);
                }else {
                    protocol.setRequestName(token);
                }
                token = tokenSequence.getNextToken();

            }
            if(match("response",filedName)){
                token = tokenSequence.getNextToken();
                if(match("{",token)){
                    SprotoStruct response = new SprotoStruct(protocolName+".response");
                    tokenSequence.back();
                    parseStructBody(tokenSequence,response, schema);
                    schema.addType(response);
                    protocol.setResponse(response);
                }else {
                    protocol.setResponseName(token);
                }
                token = tokenSequence.getNextToken();

            }

        }
        match("}",token);
        schema.addProtocol(protocol);
    }

    public static void parseStructBody(TokenSequence tokenSequence ,SprotoStruct sprotoStruct,
            SprotoSchema sprotoSchema){
        match("{",tokenSequence.getNextToken());
        String token = tokenSequence.getNextToken();
        while (!match("}",token)){
            String filedName = token;
            if(match(".",filedName)){
                tokenSequence.back();
                parseStruct(tokenSequence, sprotoSchema);
                token = tokenSequence.getNextToken();
                continue;
            }
            int number = Integer.valueOf(tokenSequence.getNextToken());
            match(":",tokenSequence.getNextToken());
            String array = tokenSequence.getNextToken();
            boolean isArray = false;
            if(match("*",array)){
                isArray = true;
                array = tokenSequence.getNextToken();
            }
            String type = array;
            SprotoType sprotoType = SprotoType.typeOf(type);
            if(isArray){
                SprotoField sprotoField = new SprotoField(filedName,number,sprotoType, true);
                if(sprotoType == SprotoType.STRUCT){
                    sprotoField.setStructName(type);
                }
                sprotoStruct.addField(sprotoField);
            }else {
                SprotoField sprotoField = new SprotoField(filedName,number,sprotoType);
                if(sprotoType == SprotoType.STRUCT){
                    sprotoField.setStructName(type);
                }
                sprotoStruct.addField(sprotoField);
            }
            token = tokenSequence.getNextToken();
            if(match("(",token)){
                token = tokenSequence.getNextToken();
                match(")",tokenSequence.getNextToken());
                token = tokenSequence.getNextToken();
            }
        }
        match("}",token);
    }

}
