package top.goodz.future.infra.feigin.model;

import java.io.Serializable;

/**
 * 发送email文件数据
 *
 * @author Yajun.Zhang
 */
public class FutureEmailFileData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 附件地址 必须是物理地址
     */
    private String filPath;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 待删除的文件名称
     */
    private String delFileName;

    public String getFilPath() {
        return filPath;
    }

    public void setFilPath(String filPath) {
        this.filPath = filPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDelFileName() {
        return delFileName;
    }

    public void setDelFileName(String delFileName) {
        this.delFileName = delFileName;
    }

    @Override
    public String toString() {
        return "EmailFileData [filPath=" + filPath + ", fileName=" + fileName + ", delFileName=" + delFileName + "]";
    }
}
