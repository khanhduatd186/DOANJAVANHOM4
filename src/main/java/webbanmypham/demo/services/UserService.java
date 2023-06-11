package webbanmypham.demo.services;

import webbanmypham.demo.enity.User;
import webbanmypham.demo.repository.IRoleRepository;
import webbanmypham.demo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;

    public void save(User user){
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if (roleId != 0 && userId != 0){
            userRepository.addRoleToUser(userId,roleId);
        }
    }
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
    }
    public List<User> getAllUser(){
        return userRepository.findAll();
    }
    public void editUser(User newUser){
        userRepository.save(newUser);
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
