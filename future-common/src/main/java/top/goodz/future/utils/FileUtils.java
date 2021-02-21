package top.goodz.future.utils;

import org.springframework.util.Assert;

import java.io.*;

/**
 * @Description file utils
 * @Author Yajun.Zhang
 * @Date 2020/6/14 13:48
 */
public class FileUtils {

    public static byte[] toByteArray(InputStream input) throws IOException {
        Assert.notNull(input,"input can not be null");
        byte[] buffer = new byte[input.available()];
        input.read(buffer);
        return buffer;
    }

    public static void FileCopy(ByteArrayInputStream data, File newFileName) {
        //选择源
        File file = newFileName;
        //选择流
        FileOutputStream fos = null;
        ByteArrayInputStream bais = null;
        try {
            bais = data;
            fos = new FileOutputStream(file);
            int temp;
            byte[] bt = new byte[1024 * 10];
            while ((temp = bais.read(bt)) != -1) {
                fos.write(bt, 0, temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关流
            try {
                if (null != fos)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
