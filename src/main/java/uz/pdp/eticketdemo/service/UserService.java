package uz.pdp.eticketdemo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.pdp.eticketdemo.dto.UserDto;
import uz.pdp.eticketdemo.entity.UserEntity;
import uz.pdp.eticketdemo.repository.UserRepository;
import uz.pdp.eticketdemo.response.ApiResponse;
import uz.pdp.eticketdemo.response.BaseResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService extends BaseResponse implements BaseService<UserDto> {
    private final UserRepository userRepository;

    @Override
    public ApiResponse getList() {
        List<UserEntity> allUsers = userRepository.findAll();
        SUCCESS.setData(allUsers);
        return SUCCESS;
    }

    @Override
    public ApiResponse getById(@PathVariable Long id) {
        Optional<UserEntity> getUserById = userRepository.findById(id);
        if(getUserById.isPresent()) {
            SUCCESS.setData(getUserById);
            return SUCCESS;
        }
        return NOT_FOUND;
    }

    @Override
    public ApiResponse delete(@PathVariable Long id) {
        Optional<UserEntity> findUserById = userRepository.findById(id);
        if(findUserById.isPresent()){
            UserEntity userEntity = findUserById.get();
            userEntity.setUserStatus(false);
            userRepository.save(userEntity);
            return SUCCESS;
        }
        return FAILED;
    }

    @Override
    public ApiResponse edit(@PathVariable Long id, @RequestBody UserDto userDto) {
        Optional<UserEntity> findUserById = userRepository.findById(id);
        if(findUserById.isPresent()){
            UserEntity userEntity = findUserById.get();
            userEntity.setPassword(userDto.getPassword());
            userEntity.setEmail(userDto.getEmail());
            userEntity.setPhoneNumber(userDto.getPhoneNumber());
                userRepository.save(userEntity);
            return SUCCESS ;
        }
        return FAILED;
    }

    @Override
    public ApiResponse add(@RequestBody UserDto userDto) {
        userRepository.save(userDto);
        return null;
    }

    // TODO: 2/17/2022 Check user before adding him  
}
