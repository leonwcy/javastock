package stock.biz.http;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParser {
    private Parser parser;
    public void HtmlParser(Parser parser){
        this.parser = parser;
    }

    public NodeList findByIds(String id) throws ParserException {
        if(parser ==null || parser.elements().nextNode() ==null) return null;

        while( parser.elements().hasMoreNodes()){
            Node d= parser.elements().nextNode();

        }
        return null;
    }
}
