package force;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.function.Consumer;

/**
 */

public class NoteDemo {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteDemo.class);

    /**
     * 文件夹路径
     */
    private static final String PATH = "E:/odianyun/code/baseline/web/social";

    public static void main(String[] args) {
        File parentFile = new File(PATH);
        changeJavaFile(parentFile, file -> {
            StringBuilder buf = new StringBuilder();
            //默认无效
            boolean isUseless = true;
            //判断文件是否修改完了
            boolean isChange = false;
            //是否存在@Author
            boolean isAuthor = false;
            try (
                    FileReader fileReader = new FileReader(file);
                    BufferedReader reader = new BufferedReader(fileReader);
            ) {
                String line;
                while ((line = reader.readLine()) != null) {
                    //判断文件是否有效
                    if (isUseless && line.trim().length() > 0) {
                        if (line.contains("/*")) {
                            break;
                        } else {
                            //文件有效
                            isUseless = false;
                        }
                    }
                    if (!line.trim().equals("") && !isChange) {
                        if (line.contains("@Author")) {
                            //存在那么就不需要写文件操作了，直接退出
                            isAuthor = true;
                            break;
                        } else if (line.contains("public class") || line.contains("public interface")) {
                            buf.append("/**" + "\n");
                            buf.append(" * @Author xxx" + "\n");
                            buf.append(" **/" + "\n");
                            //代表文件已经修改过了
                            isChange = true;
                        }
                    }
                    // 将读到的内容添加到 buffer 中
                    buf.append(line).append("\n");
                    // 读取下一行
                }
                //写文件
                if (!isAuthor && !isUseless) {
                    writeJavaFile(file, buf);
                }
            } catch (IOException e) {
                LOGGER.debug("处理文件：{}失败，异常：{}", file.getName(), e.getMessage());
            }
        });
    }

    /**
     * 给java类添加注释
     *
     * @param parentFile 文件夹
     * @param fn         处理文件的闭包方法
     */
    private static void changeJavaFile(File parentFile, Consumer<File> fn) {
        //1、遍历所有的包，获取到所有的java后缀的文件
        File[] files = parentFile.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            //不是文件夹，是以.java为后缀的文件
            if (!file.isDirectory() && file.getName().contains(".java")) {
                fn.accept(file);
            } else if (file.isDirectory()) {
                changeJavaFile(file, fn);
            }
        }
        //2、遍历的同时判断类上是否有注释，遇到截取public之前的所有字符串
        //或者到整个文件的字符串，判断是否有@Author存在，不存在就获取public的位置，在之前插入注释
    }

    /**
     * 文件写操作,并覆盖
     *
     * @param file
     * @param buf
     * @throws FileNotFoundException
     */
    private static void writeJavaFile(File file, StringBuilder buf) throws FileNotFoundException {
        try (PrintStream stream = new PrintStream(file)) {
            stream.print(buf);
            stream.flush();
        }
    }
}
