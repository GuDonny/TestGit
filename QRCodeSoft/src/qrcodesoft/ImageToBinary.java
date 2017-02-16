package qrcodesoft;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Donny on 2017/2/15.
 */
public class ImageToBinary {
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();


    public static void main(String[] args) {
        createThumbnail("C:\\Users\\SkyTech\\Desktop\\1.jpeg", "C:\\Users\\SkyTech\\Desktop\\2.jpeg", 5,5);

        // System.out.println(getImageBinary());
        base64StringToImage(getImageBinary());
    }

    static String getImageBinary() {
        File f = new File("E:\\43724628.jpg");
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpeg", baos);
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    static void base64StringToImage(String base64String) {
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File w2 = new File("E:\\2.jpg");//可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void createThumbnail(String src, String dist, float width,
                                       float height) {
        try {
            File srcfile = new File(src);
            if (!srcfile.exists()) {
                System.out.println("文件不存在");
                return;
            }
            BufferedImage image = ImageIO.read(srcfile);

            // 获得缩放的比例
            double ratio = 1.0;
            // 判断如果高、宽都不大于设定值，则不处理
            if (image.getHeight() > height || image.getWidth() > width) {
                if (image.getHeight() > image.getWidth()) {
                    ratio = height / image.getHeight();
                } else {
                    ratio = width / image.getWidth();
                }
            }
            // 计算新的图面宽度和高度
            int newWidth = (int) (image.getWidth() * ratio);
            int newHeight = (int) (image.getHeight() * ratio);

            BufferedImage bfImage = new BufferedImage(newWidth, newHeight,
                    BufferedImage.TYPE_INT_RGB);
            bfImage.getGraphics().drawImage(
                    image.getScaledInstance(newWidth, newHeight,
                            Image.SCALE_AREA_AVERAGING), 0, 0, null);

            FileOutputStream os = new FileOutputStream(dist);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
            encoder.encode(bfImage);
            os.close();
            System.out.println("创建缩略图成功");
        } catch (Exception e) {
            System.out.println("创建缩略图发生异常" + e.getMessage());
        }
    }
}
