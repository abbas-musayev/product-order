package az.abbas.product.order.search.impl;

import az.abbas.product.order.db.entity.Product;
import az.abbas.product.order.db.repo.RepoProduct;
import az.abbas.product.order.exception.ThrowNotFoundExpHandle;
import az.abbas.product.order.search.services.ProductSearchServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSearchServicesImpl implements ProductSearchServices {


    private final RepoProduct repoProduct;
    private final EntityManager entityManager;

    public List<Product> getAllProductSortAndPagination(Integer pageNumber, Integer pageSize, String name){

        if (name.equals("ASC")){
            Sort sort = Sort.by(Sort.Direction.ASC);
            PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
            Page<Product> all = repoProduct.findAll(pageable);
            return all.getContent();
        }
        if (name.equals("DESC")){
            Sort sort = Sort.by(Sort.Direction.DESC);
            PageRequest pageable = PageRequest.of(pageNumber, pageSize, sort);
            Page<Product> all = repoProduct.findAll(pageable);
            return all.getContent();
        }
        else {
            throw  new ThrowNotFoundExpHandle("GetAllProduct ucun axtarisda Xeta Bas verdi");
        }
    }

    //•	Məhsulların adına görə dinamik axtarış
    public List<Product> getDinamikSearchCriteria(Product request){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        List<Predicate> predicateList = new ArrayList<>();

        if (request.getProductName()!=null){
            Predicate productName = criteriaBuilder.equal(root.get("productName"), request.getProductName());
            predicateList.add(productName);
        }

        criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        List<Product> resultList = entityManager.createQuery(criteriaQuery).getResultList();
        return resultList;
    }

}
