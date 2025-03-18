package org.qbit.applicationmanager.infrastructure.http;

import org.qbit.applicationmanager.domain.model.Enterprise;
import org.qbit.applicationmanager.domain.model.User;
import org.qbit.applicationmanager.domain.service.EnterpriseService;
import org.qbit.applicationmanager.domain.service.UserService;
import org.qbit.applicationmanager.infrastructure.http.dto.EnterpriseDto;
import org.qbit.applicationmanager.infrastructure.http.dto.mapper.EnterpriseMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.Optional;

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
        User user = userService.getUserByUsername(username);
        Enterprise enterprise = enterpriseService.createEnterprise(enterpriseDto.getName(), user);
        EnterpriseDto outputDto = enterpriseMapper.toDto(enterprise);
        return ResponseEntity.ok(outputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnterpriseDto> getEnterprise(@PathVariable Long id, Authentication authentication) {
        return enterpriseService.getEnterpriseById(id)
                .map(enterprise -> {
                    String username = authentication.getName();
                    if (!belongsToUser(enterprise, username)) {
                        return ResponseEntity.status(403).<EnterpriseDto>build();
                    }
                    return ResponseEntity.ok(enterpriseMapper.toDto(enterprise));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private boolean belongsToUser(Enterprise enterprise, String username) {
        if (enterprise.getUser() == null || enterprise.getUser().getUserName() == null) {
            return false;
        }
        return enterprise.getUser().getUserName().equals(username);
    }
}