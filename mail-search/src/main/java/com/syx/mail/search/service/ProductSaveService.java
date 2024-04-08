package com.syx.mail.search.service;

import com.syx.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * ClassName: ProductSaveService <br/>
 * Description: <br/>
 * date: 2024/4/2 4:15 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
