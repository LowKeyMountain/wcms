package net.itw.wcms.x27.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.itw.wcms.x27.entity.User;
import net.itw.wcms.x27.repository.UserRepository;


@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public String add(User user){
        userRepository.save(user);
        return "添加成功！";
    }
}
