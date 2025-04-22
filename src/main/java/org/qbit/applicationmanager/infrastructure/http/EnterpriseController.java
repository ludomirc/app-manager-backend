package org.qbit.applicationmanager.infrastructure.http;

import java.util.List;
import java.util.Optional;

import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.EnterpriseService;
import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.EnterpriseDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.EnterpriseMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/api/enterprises")
public class EnterpriseController {

    private final EnterpriseService enterpriseService;
    private final UserService userService;
    private final EnterpriseMapper enterpriseMapper;

    public EnterpriseController(EnterpriseService enterpriseService, UserService userService, EnterpriseMapper enterpriseMapper) {
        this.enterpriseService = enterpriseService;
        this.userService = userService;
        this.enterpriseMapper = enterpriseMapper;
    }

    @PostMapping
    public ResponseEntity<EnterpriseDto> createEnterprise(@RequestBody EnterpriseDto enterpriseDto, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> user = userService.getUserByUserName(username);
        Enterprise enterprise = enterpriseService.createEnterprise(enterpriseDto.getName(), user.get());
        EnterpriseDto outputDto = enterpriseMapper.toDto(enterprise);
        return ResponseEntity.ok(outputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnterpriseDto> getEnterprise(@PathVariable Long id, Authentication authentication) {
        return enterpriseService.getEnterpriseById(id)
                .map(enterprise -> getEnterpriseDtoResponseEntity(authentication, enterprise))
                .orElseGet(this::getNotFoundResponseEntry);
    }

    @GetMapping
    public ResponseEntity<List<EnterpriseDto>> getEnterprisesForCurrentUser(Authentication authentication) {
        Optional<User> user = userService.getUserByUserName(authentication.getName());
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<EnterpriseDto> userEnterprises = enterpriseService.getEnterprisesByUser(user.get())
                .stream()
                .map(enterpriseMapper::toDto)
                .toList();

        return ResponseEntity.ok(userEnterprises);
    }

    private ResponseEntity<EnterpriseDto> getNotFoundResponseEntry() {
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<EnterpriseDto> getEnterpriseDtoResponseEntity(Authentication authentication, Enterprise enterprise) {
        String username = authentication.getName();
        if (!belongsToUser(enterprise, username)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .build();
        }
        return ResponseEntity.ok(enterpriseMapper.toDto(enterprise));
    }

    private boolean belongsToUser(Enterprise enterprise, String username) {
        if (enterprise.getUser() == null || enterprise.getUser().getUserName() == null) {
            return false;
        }
        return enterprise.getUser().getUserName().equals(username);
    }

}