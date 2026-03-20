package mz.orcamento.backend.service;

import mz.orcamento.backend.dto.UserDTO;
import mz.orcamento.backend.model.User;
import mz.orcamento.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

}
