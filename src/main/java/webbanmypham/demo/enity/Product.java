package webbanmypham.demo.enity;

import webbanmypham.demo.validator.annotation.ValidCategoryId;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private int currentQuantity;
    private String origin;
    private double costPrice;
    private double salePrice;
    private String image;
    private boolean is_activated;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @ValidCategoryId
    private Category category;
}
