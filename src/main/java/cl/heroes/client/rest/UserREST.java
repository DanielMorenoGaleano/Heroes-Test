package cl.heroes.client.rest;

import cl.heroes.client.document.UpdateUser;
import cl.heroes.client.document.User;
import cl.heroes.client.dto.SignupDTO;
import cl.heroes.client.dto.TokenDTO;
import cl.heroes.client.repository.UserRepository;

import java.util.Optional;

import javax.el.MethodNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserREST {
    @Autowired
    UserRepository userRepository;
    


    @GetMapping("/{id}")
    @PreAuthorize("#user.id == #id")
    public ResponseEntity<?> me(@AuthenticationPrincipal User user, @PathVariable String id) {
        return ResponseEntity.ok(userRepository.findById(id));
    }
    
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("#user.id == #id")
	public ResponseEntity<Object> deleteById(@AuthenticationPrincipal User user,@PathVariable String id){
		userRepository.deleteById(id);
		return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PutMapping("/update/{id}")
	public  void update (@RequestBody SignupDTO signupDTO,@PathVariable String id){
		 Optional<User> userRepo =  userRepository.findById(id);
		if(userRepo.isPresent()) {
			User userDTO = userRepo.get();
			userDTO.setEmail(signupDTO.getEmail());
			userDTO.setPassword(signupDTO.getPassword());
			userRepository.save(userDTO);
		}
		
	
	}
}
