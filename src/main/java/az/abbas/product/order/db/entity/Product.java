package az.abbas.product.order.db.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Where(clause = "is_delete=false")
@SQLDelete(sql = "update product set is_delete=true where id_product=?")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long idProduct;

    @Column(unique = true)
    private String productName;

    private Float productPrice;

    private Boolean isDelete;

    private Integer miqdar;

    @ToString.Exclude
    @JsonBackReference()
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private ProductAll productAll;

    @PrePersist
    public void setIsDelete(){
        this.isDelete=false;
    }
}
