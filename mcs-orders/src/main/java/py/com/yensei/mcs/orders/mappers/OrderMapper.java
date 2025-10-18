package py.com.yensei.mcs.orders.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;

import py.com.yensei.mcs.orders.entities.OrderEntity;
import py.com.yensei.mcs.orders.models.OrderModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    // MapStruct will generate the implementation
    // OrderModel to OrderEntity
    OrderEntity toEntity(OrderModel model);

    // OrderEntity to OrderModel
    OrderModel toModel(OrderEntity entity);

    // Actualiza una entidad existente desde un modelo, ignorando el ID del modelo
    @Mapping(target = "id", ignore = true)
    void updateEntityFromModel(OrderModel model, @MappingTarget OrderEntity entity);
}
