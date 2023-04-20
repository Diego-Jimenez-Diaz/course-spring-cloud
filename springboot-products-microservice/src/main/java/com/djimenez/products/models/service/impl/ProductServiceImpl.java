package com.djimenez.products.models.service.impl;

import com.djimenez.products.models.dao.ProductDao;
import com.djimenez.products.models.entity.Product;
import com.djimenez.products.models.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public List<Product> getAll() {
        return productDao.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productDao.findById(id).orElse(null);
    }

	@Override
	@Transactional
	public Product save(Product product) {
		return productDao.save(product);
	}

	@Override
	public void deleteById(Long id) {
		productDao.deleteById(id);
		
	}

	@Override
	public Product update(Long id, Product product) {
		Product productUpdated = findById(id);
		productUpdated.setName(product.getName());
		productUpdated.setPrice(product.getPrice());
		productUpdated.setCreateAt(product.getCreateAt());
		
		return productDao.save(productUpdated);
	}
}
