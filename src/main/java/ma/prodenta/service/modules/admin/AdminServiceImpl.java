package ma.prodenta.service.modules.admin;

import ma.prodenta.common.exceptions.ValidationException;
import ma.prodenta.common.util.PasswordUtil;
import ma.prodenta.common.validators.AuthValidator;
import ma.prodenta.entities.En.Admin;
import ma.prodenta.mvc.dto.admin.AdminViewDto;
import ma.prodenta.mvc.dto.admin.LoginRequestDto;
import ma.prodenta.repository.common.AdminRepository;

import java.time.LocalDateTime;

/**
  logique métier d'auth Admin.
 */
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public AdminViewDto authenticate(LoginRequestDto loginRequest) throws ValidationException {
        AuthValidator.validateUsername(loginRequest.getUsername());
        AuthValidator.validatePassword(loginRequest.getPassword());

        Admin admin = adminRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new ValidationException("Nom d'utilisateur ou mot de passe incorrect."));

        String inputHash = PasswordUtil.hashPassword(loginRequest.getPassword());
        if (!inputHash.equals(admin.getPasswordHash())) {
            throw new ValidationException("Nom d'utilisateur ou mot de passe incorrect.");
        }

        adminRepository.updateLastLogin((long) admin.getId());

        return toViewDto(admin);
    }

    private AdminViewDto toViewDto(Admin admin) {
        LocalDateTime lastLoginAt = admin.getLastLoginAt() != null ? admin.getLastLoginAt() : LocalDateTime.now();
        return new AdminViewDto(
                (long) admin.getId(),
                admin.getUsername(),
                admin.getNom(),
                admin.getEmail(),
                admin.getRole(),
                lastLoginAt
        );
    }
}
