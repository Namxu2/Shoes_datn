package com.fpoly.datn.repository;

import com.fpoly.datn.entity.ProductColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductColorRepository extends JpaRepository<ProductColor, Long> {
    Optional<ProductColor> findByProductIdAndColor(String productId, String color);

    // Lấy màu của sản phẩm
    @Query(nativeQuery = true, value = "SELECT pc.color FROM product_color pc WHERE pc.product_id = ?1 AND pc.quantity > 0")
    List<String> findAllColorOfProduct(String id);

    List<ProductColor> findByProductId(String id);

    // Kiểm tra màu sản phẩm
    @Query(value = "SELECT * FROM product_color WHERE product_id = ?1 AND color = ?2 AND quantity > 0", nativeQuery = true)
    ProductColor checkProductAndColorAvailable(String id, String color);

    // Trừ 1 sản phẩm theo màu
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE product_color SET quantity = quantity - 1 WHERE product_id = ?1 AND color = ?2")
    void minusOneProductByColor(String id, String color);

    // Cộng 1 sản phẩm theo màu
    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "UPDATE product_color SET quantity = quantity + 1 WHERE product_id = ?1 AND color = ?2")
    public void plusOneProductByColor(String id, String color);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM product_color WHERE product_id = ?1")
     public void deleteByProductId(String id);
}
