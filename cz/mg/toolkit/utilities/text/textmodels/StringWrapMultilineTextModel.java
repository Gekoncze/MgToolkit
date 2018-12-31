//package cz.mg.toolkit.utilities.text.textmodels.qb;

//package cz.mg.toolkit.utilities.text.textmodels;
//
//import cz.mg.collections.list.ListItem;
//import cz.mg.collections.list.chainlist.ChainList;
//import cz.mg.toolkit.graphics.Font;
//import cz.mg.toolkit.utilities.text.WrapableTextModel;
//
//
//public class StringWrapMultilineTextModel extends StringMultilineTextModel implements WrapableTextModel {
//    @Override
//    public boolean isWrapable(Font font, double width){
//        boolean wrapable = false;
//        for(String line : lines) wrapable |= isWrapable(font, width, line);
//        return wrapable;
//    }
//    
//    @Override
//    public void wrap(Font font, double width){
//        updateLines();
//        ChainList<String> newLines = new ChainList<>();
//        ChainList<String> oldLines = new ChainList<>(lines);
//        for(ListItem<String> item = oldLines.getFirstItem(); item != null; item = item.getNextItem()) wrapLine(font, width, item, newLines);
//        lines = newLines;
//    }
//    
////    private void wrapLine(Font font, double width, ListItem<String> item, ChainList<String> newLines){
////        String oldLine = item.getData();
////        if(isWrapable(font, width, oldLine)){
////            String[] parts = wrap(font, width, oldLine);
////            newLines.addLast(parts[0]);
////            item.addNext(parts[1]);
////        } else {
////            newLines.addLast(oldLine);
////        }
////    }
////    
////    private static boolean isWrapable(Font font, double width, String line){
////        if(line.length() < 2) return false;
////        return font.getWidth(line) > width;
////    }
////    
////    private static String[] wrap(Font font, double width, String line){
////        if(line.length() > 1) throw new RuntimeException();
////        for(int i = 2; i <= line.length(); i++){
////            String currentSubstring = line.substring(0, i);
////            if(font.getWidth(currentSubstring) > width){
////                return new String[]{
////                    line.substring(0, i-1),
////                    line.substring(i)
////                };
////            }
////        }
////        throw new RuntimeException();
////    }
//    
//    @Override
//    public int caretsToCaret(int ix, int iy) {
//        todo begin;
//        int caret = 0;
//        for(int l = 0; l < iy; l++){
//            caret += getLine(l).length() + 1; // +1 might depend on last character, if it WAS \n, then add, otherwise not
//        }
//        return caret + ix;
//        todo end;
//    }
//}
