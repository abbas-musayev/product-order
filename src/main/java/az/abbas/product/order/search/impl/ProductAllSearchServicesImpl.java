package az.abbas.product.order.search.impl;

import az.abbas.product.order.db.repo.RepoProductAll;
import az.abbas.product.order.search.services.ProductAllSearchServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAllSearchServicesImpl implements ProductAllSearchServices {

    private final RepoProductAll repoProductAll;


    public String countDailyAllProductPrice(){
        List<Float> productAllPrice = repoProductAll.countAllProductAllPrice();
        float umumi=0;
        for (Float price : productAllPrice) {
            if (price == null){
                continue;
            }else {
                umumi+=price;
            }

        }
        return (" Umumi qiymet"+umumi);
    }
}
