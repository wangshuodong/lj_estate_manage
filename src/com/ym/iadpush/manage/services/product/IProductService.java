/**
 * 
 */
package com.ym.iadpush.manage.services.product;

import java.util.List;
import java.util.Map;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.Product;
import com.ym.iadpush.manage.entity.Product_property;

public interface IProductService {

    Product getTopOneProduct();

    QueryResult getProducts(Map<String, Object> params);

    int insertProduct(Product p);

    int insertProduct_property(Product_property product_property);

    int delById(int id);

    Product findById(int id);

    int updateById(Product p);

    List<Product_property> getProduct_propertys(Integer productId, String type);

    List<Product> getAllProducts();
    
    List<Product> getAllProductsUse();
    

}
