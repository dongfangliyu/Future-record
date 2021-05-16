package top.goodz.future.request.email;

import java.io.Serializable;
import java.util.List;

/**
 * 发送email请求参数
 *
 * @author Yajun.Zhang
 */
public class EmailData implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * 收件人的邮箱
     */
    private String toMail;

    /**
     * 邮箱内容
     */
    private String content;

    /**
     * 邮箱标题
     */
    private String subject;

    /**
     * 静态资源id
     */
    private String rscId;

    /**
     * 静态资源路径和文件名
     */
    private String rscPath;

    /**
     * 案件ID
     */
    private String arbitralInfoId;

    /**
     * 发送email文件数据
     */
    private List<EmailFileData> files;

    public String getToMail() {
        return toMail;
    }

    public void setToMail(String toMail) {
        this.toMail = toMail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRscId() {
        return rscId;
    }

    public void setRscId(String rscId) {
        this.rscId = rscId;
    }

    public String getRscPath() {
        return rscPath;
    }

    public void setRscPath(String rscPath) {
        this.rscPath = rscPath;
    }

    public String getArbitralInfoId() {
        return arbitralInfoId;
    }

    public void setArbitralInfoId(String arbitralInfoId) {
        this.arbitralInfoId = arbitralInfoId;
    }

    public List<EmailFileData> getFiles() {
        return files;
    }

    public void setFiles(List<EmailFileData> files) {
        this.files = files;
    }

    @Override
    public String toString() {
        return "EmailData [toMail=" + toMail + ", content=" + content + ", subject=" + subject + ", rscId=" + rscId
                + ", rscPath=" + rscPath + ", arbitralInfoId=" + arbitralInfoId + ", files=" + files + "]";
    }
}
