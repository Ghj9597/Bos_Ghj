package cn.itcast.bos.web.action.image.action;

import cn.itcast.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.aspectj.util.FileUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 犹良 on 2017/8/3 0003.
 */

@ParentPackage("json-default")
@Controller
@Scope(value = "prototype")
@Namespace("/img")
public class ImgAction extends BaseAction<Object> {

    private File imgFile;
    private String imgFileFileName;
    private String imgFileContentType;



    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    public void setImgFileFileName(String imgFileFileName) {

        this.imgFileFileName = imgFileFileName;
    }


    @Action(value = "uploadImg", results = {@Result(name = "success", type = "json")})
    public String uploadImg() {
        //获取绝对路径;

        String path = ServletActionContext.getServletContext().getRealPath("/upload");
        //获取相对路径;
        String url = ServletActionContext.getRequest().getContextPath() + "/upload";
        //随机生成文件名;
        UUID uuid = UUID.randomUUID();
        //获取文件后缀名称;
        String substring = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
        String fileName = uuid + substring;
        File file = new File(path,fileName);
        System.out.println(file.toString());
        try {
            //复制文件;
            FileUtil.copyFile(imgFile, file);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("error", 0);
            map.put("url", url +"/"+ fileName);
            System.out.println(url +"/"+ fileName);
            ActionContext.getContext().getValueStack().push(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Action(value = "findImg", results = {@Result(name = "success", type = "json")})
    public String findImg(){
        //获取绝对路径;
        String path = ServletActionContext.getServletContext().getRealPath("/upload");
        //获取相对路径;
        String url = ServletActionContext.getRequest().getContextPath() + "/upload";
        // 遍历目录取的文件信息
        List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
        // 当前上传目录
        File currentPathFile = new File(path);
        // 图片扩展名
        String[] fileTypes = new String[] { "gif", "jpg", "jpeg", "png", "bmp" };

        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Map<String, Object> hash = new HashMap<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt = fileName.substring(
                            fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String> asList(fileTypes)
                            .contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename","/"+fileName);
                hash.put("datetime",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file
                                .lastModified()));
                fileList.add(hash);
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("moveup_dir_path", "");
        result.put("current_dir_path", path);
        result.put("current_url",url);
        result.put("total_count", fileList.size());
        result.put("file_list", fileList);
        ActionContext.getContext().getValueStack().push(result);
        return SUCCESS;
    }


}
