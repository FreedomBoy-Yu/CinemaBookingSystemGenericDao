package eddie.project.cinemabookingsystemgenericdao.aspect;

import eddie.project.cinemabookingsystemgenericdao.annotation.RequiresRole;
import eddie.project.cinemabookingsystemgenericdao.dto.user.UserJwtResponseDTO;
import eddie.project.cinemabookingsystemgenericdao.exception.UnauthorizedException;
import eddie.project.cinemabookingsystemgenericdao.util.JwtUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class RoleCheckAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Before("@annotation(eddie.project.cinemabookingsystemgenericdao.annotation.RequiresRole)")
    public void checkRole(JoinPoint joinPoint) {
        // 獲取當前請求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new UnauthorizedException("無法獲取請求信息");
        }
        
        HttpServletRequest request = attributes.getRequest();
        
        // 從請求頭獲取JWT令牌
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new UnauthorizedException("未提供有效的授權令牌");
        }
        
        token = token.substring(7); // 去掉前綴 "Bearer "
        
        // 驗證JWT並獲取用戶角色
        UserJwtResponseDTO userInfo;
        try {
            userInfo = jwtUtil.validateToken(token);
        } catch (Exception e) {
            throw new UnauthorizedException("無效的授權令牌: " + e.getMessage());
        }
        
        // 獲取方法上的RequiresRole注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequiresRole requiresRole = method.getAnnotation(RequiresRole.class);
        
        // 檢查角色
        boolean hasPermission = false;
        
        if (requiresRole.logical() == RequiresRole.Logical.ANY) {
            // 任一角色即可
            for (int role : requiresRole.value()) {
                if (userInfo.getRole() == role) {
                    hasPermission = true;
                    break;
                }
            }
        } else {
            // 所有角色都需要 (很少會用到這種情況，但保留這個可能性)
            hasPermission = true;
            for (int role : requiresRole.value()) {
                if (userInfo.getRole() != role) {
                    hasPermission = false;
                    break;
                }
            }
        }
        
        if (!hasPermission) {
            throw new UnauthorizedException("權限不足，需要角色: " + 
                Arrays.toString(requiresRole.value()) + 
                ", 當前角色: " + userInfo.getRole());
        }
    }
}
