/**
 * 
 */
package com.ym.iadpush.manage.services.product.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ym.common.utils.QueryResult;
import com.ym.iadpush.manage.entity.Product;
import com.ym.iadpush.manage.entity.Product_property;
import com.ym.iadpush.manage.mapper.ProductMapper;
import com.ym.iadpush.manage.mapper.Product_propertyMapper;
import com.ym.iadpush.manage.services.product.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private Product_propertyMapper product_propertyMapper;

    @Override
    public Product getTopOneProduct() {
        return productMapper.getTopOneProduct();
    }

    @Override
    public QueryResult getProducts(Map<String, Object> params) {
        QueryResult result = new QueryResult();
        List<Product> list = productMapper.query(params);
        if (list != null && list.size() > 0) {
            Integer count = productMapper.countByQuery(params);
            result.setData(list);
            result.setCount(count);
        }
        return result;
    }

    @Override
    public int insertProduct(Product p) {
        return productMapper.insertProduct(p);
    }

    public int delById(int id) {
        return productMapper.delByPid(id);
    }

    public Product findById(int id) {
        return productMapper.findById(id);
    }

    public int updateById(Product p) {
        return productMapper.updateById(p);
    }

    @Override
    public List<Product_property> getProduct_propertys(Integer productId, String type) {
        List<Product_property> list = product_propertyMapper.getProduct_propertys(productId, type);
        return list;
    }

    @Override
    public int insertProduct_property(Product_property product_property) {
        return product_propertyMapper.insertProduct_property(product_property);
    }

    @Override
    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }

    @Override
    public List<Product> getAllProductsUse() {
        return productMapper.getAllProductsUse();
    }
}
