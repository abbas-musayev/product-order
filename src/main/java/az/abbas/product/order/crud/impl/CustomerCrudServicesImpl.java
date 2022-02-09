package az.abbas.product.order.crud.impl;

import az.abbas.product.order.crud.services.CustomerCrudServices;
import az.abbas.product.order.db.dto.CustomerDTO;
import az.abbas.product.order.db.entity.Address;
import az.abbas.product.order.db.entity.Customer;
import az.abbas.product.order.db.repo.RepoAddress;
import az.abbas.product.order.db.repo.RepoCustomer;
import az.abbas.product.order.exception.ThrowNotFoundExpHandle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerCrudServicesImpl implements CustomerCrudServices {

    private final RepoCustomer repoCustomer;
    private final RepoAddress repoAddress;

    public CustomerDTO saveCustomer(CustomerDTO dto){

        Customer customerBySurnameAndName = repoCustomer.getCustomerBySurnameAndName(dto.getSurname(), dto.getName());
        if (customerBySurnameAndName!=null){
            throw new ThrowNotFoundExpHandle("Hal Hazirda Ad("+dto.getName()+") Ve Soyadda "+dto.getSurname()+") Istifadeci var");
        }

        Customer customer = Customer.builder()
                .idCustomer(dto.getIdCustomer())
                .name(dto.getName())
                .surname(dto.getSurname())
                .username(dto.getUsername())
                .address(dto.getAddress())
                .balance(dto.getBalance())
                .password(dto.getPassword())
                .orders(dto.getOrders())
                .build();

        Address address = new Address();
        List<Address> address1 = dto.getAddress();
        address1.forEach(item->{
            address.setAddressName(item.getAddressName());
        });
        address.setCustomer(customer);

        repoAddress.save(address);

        Customer save = repoCustomer.save(customer);

        return CustomerDTO.builder()
                .idCustomer(save.getIdCustomer())
                .name(save.getName())
                .surname(save.getSurname())
                .username(save.getUsername())
                .address(save.getAddress())
                .balance(save.getBalance())
                .createdTimeCustomer(save.getCreatedTimeCustomer())
                .orders(save.getOrders())
                .password(save.getPassword())
                .build();

    }

    public String deleteCustomer(CustomerDTO dto){

        Customer customer = repoCustomer.getCustomerBySurnameAndName(dto.getSurname(), dto.getName());

        if (customer==null){
            throw new ThrowNotFoundExpHandle("Silmek ucun Musteri Tapilmadi");
        }else{

        List<Address> address = customer.getAddress();

        repoAddress.deleteAll(address);
        log.info("Customerin("+customer.getName()+") AddressListi silindi");

        repoCustomer.delete(customer);
        log.info(customer.getName()+" Adli Musteri Silindi");
        return customer.getName()+" Adli Musteri Silindi";
    }
    }


    public CustomerDTO updateCustomer(CustomerDTO dto){
        Customer customer = repoCustomer.findById(dto.getIdCustomer()).get();
        if (customer != null){
        customer.setIdCustomer(customer.getIdCustomer());
        customer.setName(dto.getName());
        customer.setSurname(dto.getSurname());
        customer.setUsername(dto.getUsername());
        customer.setAddress(dto.getAddress());
        customer.setBalance(customer.getBalance());
        customer.setOrders(dto.getOrders());
        customer.setPassword(dto.getPassword());

        Address address = new Address();
        address.setCustomer(customer);

        repoAddress.save(address);

        Customer save = repoCustomer.save(customer);

        return CustomerDTO.builder()
                .idCustomer(save.getIdCustomer())
                .name(save.getName())
                .surname(save.getSurname())
                .username(save.getUsername())
                .address(save.getAddress())
                .balance(save.getBalance())
                .createdTimeCustomer(save.getCreatedTimeCustomer())
                .orders(save.getOrders())
                .password(save.getPassword())
                .build();
        }else {
            throw new ThrowNotFoundExpHandle("Update Etmek istediyiniz Musteri Tapilmadi");
        }
    }

    //customer balans artimi
    public CustomerDTO balansArtimi(Long idCustomer,Float newbalance){

        Customer customer = repoCustomer.findById(idCustomer).get();

        Float balance = customer.getBalance();
        customer.setBalance(balance+newbalance);

        Customer save = repoCustomer.save(customer);

        return CustomerDTO.builder()
                .idCustomer(save.getIdCustomer())
                .name(save.getName())
                .surname(save.getSurname())
                .username(save.getUsername())
                .address(save.getAddress())
                .balance(save.getBalance())
                .orders(save.getOrders())
                .createdTimeCustomer(save.getCreatedTimeCustomer())
                .password(save.getPassword())
                .build();
    }

    //address elave etmek
    public CustomerDTO addAddress(CustomerDTO customerDTO){

        Customer customer = repoCustomer.findById(customerDTO.getIdCustomer()).get();

        Address address = new Address();
        List<Address> address1 = customerDTO.getAddress();
        for (Address item : address1) {
            address.setAddressName(item.getAddressName());
        }
        address.setCustomer(customer);

        repoAddress.save(address);

        Customer save = repoCustomer.save(customer);

        return CustomerDTO.builder()
                .idCustomer(save.getIdCustomer())
                .name(save.getName())
                .surname(save.getSurname())
                .username(save.getUsername())
                .address(save.getAddress())
                .balance(save.getBalance())
                .orders(save.getOrders())
                .createdTimeCustomer(save.getCreatedTimeCustomer())
                .password(save.getPassword())
                .build();

    }

    public String deleteAddress( CustomerDTO customerDTO){

        Customer customer = repoCustomer.findById(customerDTO.getIdCustomer()).get();


        return "";


    }
}
