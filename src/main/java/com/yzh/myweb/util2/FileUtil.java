package com.yzh.myweb.util2;

import com.aspose.words.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件相关工具类
 */
public class FileUtil {

    private static final boolean FALSE = false;

    private static final boolean TRUE = true;

    public static void convertImageToPdf(InputStream ins, OutputStream out) throws Exception {
        Document doc = new Document();
        DocumentBuilder builder = new DocumentBuilder(doc);

        ImageInputStream iis = ImageIO.createImageInputStream(ins);
        ImageReader reader = ImageIO.getImageReaders(iis).next();
        reader.setInput(iis, FALSE);

        try {
            int framesCount = reader.getNumImages(TRUE);

            for (int frameIdx = 0; frameIdx < framesCount; frameIdx++) {
                if (frameIdx != 0) {
                    builder.insertBreak(BreakType.SECTION_BREAK_NEW_PAGE);
                }

                BufferedImage image = reader.read(frameIdx);

                PageSetup ps = builder.getPageSetup();

                ps.setPageWidth(ConvertUtil.pixelToPoint(image.getWidth()));
                ps.setPageHeight(ConvertUtil.pixelToPoint(image.getHeight()));

                builder.insertImage(image, RelativeHorizontalPosition.PAGE, 0, RelativeVerticalPosition.PAGE, 0,
                        ps.getPageWidth(), ps.getPageHeight(), WrapType.NONE);
            }
        } finally {
            if (iis != null) {
                iis.close();
                reader.dispose();
            }
        }
        // Save the document to PDF.
        doc.save(out, new PdfSaveOptions());
    }
}
