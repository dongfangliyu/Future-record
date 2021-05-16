package top.goodz.future.assess.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description index api
 * @Author Yajun.Zhang
 * @Date 2020/8/9 19:54
 */
@Controller
public class SysIndexController {

    // 系统首页
    @GetMapping("/index")
    public String index(ModelMap mmap) {

        mmap.put("menus", "menus");
        mmap.put("user", "user");
        mmap.put("sideTheme", "");
        mmap.put("skinName", "");
        mmap.put("copyrightYear", "");
        mmap.put("demoEnabled", "");
        return "index";
    }

}
