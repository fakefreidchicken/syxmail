package com.syx.mail.search.app;

import com.syx.mail.search.service.MailSearchService;
import com.syx.mail.search.vo.SearchParam;
import com.syx.mail.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ClassName: SearchController <br/>
 * Description: <br/>
 * date: 2024/4/7 16:19 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Controller
public class SearchController {

    @Autowired
    MailSearchService mailSearchService;

    @GetMapping("/list.html")
    public String listPage(SearchParam param, Model model){
        SearchResult result = mailSearchService.search(param);
        model.addAttribute("result",result);
        return "list";
    }
}
