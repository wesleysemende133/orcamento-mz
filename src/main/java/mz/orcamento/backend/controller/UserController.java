package mz.orcamento.backend.controller;
import mz.orcamento.backend.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class UserController {
    public List<UserDTO> users = new ArrayList<>();
    @GetMapping("/usuarios")
    public List<UserDTO> getUserDTO(){
        return users;
    }
    @PostMapping("/usuarios")
    UserDTO newUser(@RequestBody UserDTO user){
        user.setDataCadastro( new Date());
        users.add(user);
        return user;
    }

    @PutMapping("/usuarios/{bi}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String bi, @RequestBody UserDTO updatedUser){
        for(UserDTO user : users){
            if(user.getNumeroBI().equals(bi)){
                user.setNome(updatedUser.getNome());
                user.setEmail(updatedUser.getEmail());
                user.setResidencia(updatedUser.getResidencia());
                user.setTelefone(updatedUser.getTelefone());
            }
        }
        return ResponseEntity.status(404).body(null);
    }

    @DeleteMapping("/usuarios/{bi}")
    public void deleteUser(@PathVariable String bi){
        users.removeIf(user -> user.getNumeroBI().equals(bi));
    }
}
