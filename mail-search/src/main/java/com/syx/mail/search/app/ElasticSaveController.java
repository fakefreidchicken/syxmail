package com.syx.mail.search.app;

import com.syx.common.exception.BizCodeEnum;
import com.syx.common.to.es.SkuEsModel;
import com.syx.common.utils.R;
import com.syx.mail.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * ClassName: ElasticSaveController <br/>
 * Description: <br/>
 * date: 2024/4/2 4:12 <br/>
 *
 * @author Mr.Shen <b/r>
 * @since JDK 8
 */
@Slf4j
@RequestMapping("search/save")
@RestController
public class ElasticSaveController {

    @Autowired
    ProductSaveService productSaveService;

    /**
     * 上架商品
     * @param skuEsModels
     * @return
     */
    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) {
        boolean b = false;
        try {
             b = productSaveService.productStatusUp(skuEsModels);
        } catch (IOException e) {
            log.error("ElasticSaveController商品上架错误，原因{}",e);
             return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTIOM.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTIOM.getMsg());

        }
        if (!b) {
            return R.ok();
        }else {
            return R.error(BizCodeEnum.PRODUCT_UP_EXCEPTIOM.getCode(), BizCodeEnum.PRODUCT_UP_EXCEPTIOM.getMsg());
        }

    }
}
