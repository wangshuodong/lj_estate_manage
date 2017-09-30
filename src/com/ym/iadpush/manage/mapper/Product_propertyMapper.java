/**
 * 
 */
package com.ym.iadpush.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ym.iadpush.manage.entity.Product_property;


public interface Product_propertyMapper {

    List<Product_property> getProduct_propertyByProductId(@Param("productId") Integer productId);

    List<Product_property> getProduct_propertys(@Param("productId") Integer productId, String type);

    int insertProduct_property(Product_property product_property);
}
