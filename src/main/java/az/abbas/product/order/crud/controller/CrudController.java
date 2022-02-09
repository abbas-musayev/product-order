package az.abbas.product.order.crud.controller;

import az.abbas.product.order.crud.services.CustomerCrudServices;
import az.abbas.product.order.crud.services.OrderCrudServices;
import az.abbas.product.order.crud.services.ProductAllCrudServices;
import az.abbas.product.order.crud.services.ProductCrudServices;
import az.abbas.product.order.db.dto.CustomerDTO;
import az.abbas.product.order.db.dto.OrderDTO;
import az.abbas.product.order.db.dto.ProductDTO;
import az.abbas.product.order.db.entity.Orders;
import az.abbas.product.order.util.ViewsOrder;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crud")
public class CrudController {

    private final CustomerCrudServices customerServices;
    private final OrderCrudServices orderServices;
    private final ProductCrudServices productServices;
    private final ProductAllCrudServices productAllServices;

//*************************CUSTOMER**********************************************************************

    @PostMapping("/saveCustomer")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO dto){
        return new ResponseEntity<>(customerServices.saveCustomer(dto), HttpStatus.OK);
    }

    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<String> deleteCustomer (@RequestBody CustomerDTO dto){
        return new ResponseEntity<>(customerServices.deleteCustomer(dto),HttpStatus.OK);
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO dto){
        return new ResponseEntity<>(customerServices.updateCustomer(dto),HttpStatus.OK);
    }

    @PostMapping("/addAddress")
    public ResponseEntity<CustomerDTO> addAddress(@RequestBody CustomerDTO dto){
        return new ResponseEntity<>(customerServices.addAddress(dto),HttpStatus.OK);
    }

    @DeleteMapping("/deleteddress")
    public ResponseEntity<String> deleteAdrress(@RequestBody CustomerDTO customerDTO){
            return new ResponseEntity<>(customerServices.deleteAddress(customerDTO),HttpStatus.OK);
    }

    @PostMapping("/balansArtimi")
    public ResponseEntity<CustomerDTO> balansArtimi(@RequestParam Long idCostumer,@RequestParam Float balance){
        return new ResponseEntity<>(customerServices.balansArtimi(idCostumer, balance),HttpStatus.OK);
    }

//    **********************ORDER**************************************************************

    @JsonView(ViewsOrder.simple.class)
    @PostMapping("/saveOrder")
    public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(orderServices.saveOrder(orderDTO),HttpStatus.OK);
    }
    @PostMapping("/changeOrderStatus")
    public ResponseEntity<Orders> changeOrderStatus(@RequestBody OrderDTO dto){
        return new ResponseEntity<>(orderServices.changeOrderStatus(dto),HttpStatus.OK);
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO){
        return new ResponseEntity<>(orderServices.updateOrder(orderDTO),HttpStatus.OK);
    }

    //Delete etmek isteyende enumStatus Cancelled olacaq
    @DeleteMapping("/deleteOrder")
    public ResponseEntity<String> deleteOrder(@RequestParam Integer unique){
        return new ResponseEntity<>(orderServices.deleteOrder(unique),HttpStatus.OK);
    }



//    ***************PRODUCT*********************************************************************

    @PostMapping("/saveProduct")
    public ResponseEntity<ProductDTO>  saveProduct(@RequestBody ProductDTO productDTO){
        return new ResponseEntity<>(productServices.saveProduct(productDTO),HttpStatus.OK);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO dto){
        return new ResponseEntity<>(productServices.updateProduct(dto),HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestBody ProductDTO dto){
        return new ResponseEntity<>(productServices.deleteProduct(dto),HttpStatus.OK);
    }

//*********************PRODUCTALL********************************************************************

    @DeleteMapping("/deleteProductAll")
    public ResponseEntity<String> deleteProductAll(@RequestParam Long id){
        return new ResponseEntity<>(productAllServices.deleteProductAll(id),HttpStatus.OK);
    }

}
