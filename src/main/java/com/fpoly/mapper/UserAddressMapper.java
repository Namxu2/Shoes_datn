package com.fpoly.mapper;

import com.fpoly.dto.request.UserAdressRequest;
import com.fpoly.dto.response.UserAdressResponse;
import com.fpoly.entity.UserAddress;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserAddressMapper {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserMapper userMapper;

    public List<UserAdressResponse> listUserAddToListResponse(List<UserAddress> list){
        List<UserAdressResponse> dto = list.stream()
                .map(post -> mapper.map(post, UserAdressResponse.class))
                .collect(Collectors.toList());
        for(int i=0;i<list.size();i++){
            dto.get(i).setUser(userMapper.userToUserDto(list.get(i).getUser()));
        }
        return dto;
    }

    public UserAddress requestToUserAddress(UserAdressRequest request){
        UserAddress userAddress = mapper.map(request, UserAddress.class);
        return userAddress;
    }

    public UserAdressResponse userAdressToUserAddResponse(UserAddress userAddress){
        UserAdressResponse response = null;
        if(userAddress != null){
            response = mapper.map(userAddress, UserAdressResponse.class);
        }
        if(response != null){
            response.setUser(userMapper.userToUserDto(userAddress.getUser()));
        }
        return response;
    }
}