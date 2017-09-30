/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Product;


public interface ProductMapper {

    Product getTopOneProduct();

    List<Product> query(Map<String, Object> params);

    Integer countByQuery(Map<String, Object> params);
    
    int insertProduct(Product p);
    
    int delByPid(@Param("pid")int id);
    
    Product findById(@Param("pid")int id);
    
    int updateById(Product p);
    
    List<Product> getAllProducts();
    
    List<Product> getAllProductsUse();
}
