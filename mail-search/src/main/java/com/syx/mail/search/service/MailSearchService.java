package com.syx.mail.search.service;

import com.syx.mail.search.vo.SearchParam;
import com.syx.mail.search.vo.SearchResult;

/**
 * ClassName: MailSearchService <br/>
 * Description: <br/>
 * date: 2024/4/7 20:48 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
public interface MailSearchService {
    SearchResult search(SearchParam param);
}
