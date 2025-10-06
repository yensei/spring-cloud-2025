package py.com.yensei.mcs.customers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import py.com.yensei.mcs.customers.entities.CustomerEntity;
import py.com.yensei.mcs.customers.models.CustomerModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {

    // MapStruct will generate the implementation
    // CustomerModel to CustomerEntity
    CustomerEntity toEntity(CustomerModel model);

    // CustomerEntity to CustomerModel
    CustomerModel toModel(CustomerEntity entity);
}
