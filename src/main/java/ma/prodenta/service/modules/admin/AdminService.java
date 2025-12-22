package ma.prodenta.service.modules.admin;

import ma.prodenta.common.exceptions.ValidationException;
import ma.prodenta.mvc.dto.admin.AdminViewDto;
import ma.prodenta.mvc.dto.admin.LoginRequestDto;

/**
 logique métier de Admin.
 */
public interface AdminService {
    AdminViewDto authenticate(LoginRequestDto loginRequest) throws ValidationException;
}
