package top.goodz.future.pdf;

import com.lowagie.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import top.goodz.future.utils.ChkUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * pdf生成工具类
 *
 * @author yajun.zhang
 */
public class PdfGenerator {

    private static final Logger logger = LoggerFactory.getLogger(PdfGenerator.class);

    public static boolean generate(String htmlStr, OutputStream out) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(htmlStr.getBytes("UTF-8")));
            ITextRenderer2 renderer = new ITextRenderer2(new ITextRenderer());
            String systemPath = System.getProperty("user.dir") + "/";
            renderer.getFontResolver().addFont(systemPath + "ttl/" + "simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.getFontResolver().addFont(systemPath + "ttl/" + "fangsong.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.getFontResolver().addFont(systemPath + "ttl/" + "times.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.getFontResolver().addFont(systemPath + "ttl/" + "simhei.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.getFontResolver().addFont(systemPath + "ttl/" + "simkai.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocument(doc, null);
            renderer.setPdfPageEvent(new PdfBuilder());
            // 解决图片的相对路径问题
            renderer.layout();
            renderer.createPDF(out);
            return true;
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
            return false;
        } finally {
            try {
                if (!ChkUtil.isEmpty(out)) {
                    out.close();
                }
            } catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }
}
